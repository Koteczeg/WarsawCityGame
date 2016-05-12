﻿using System.Linq;
using System.Web.Http;
using AutoMapper;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Models.Missions;
using System.Data.Entity;

namespace WarsawCityGamesServer.Services.Controllers
{
    [Authorize]
    [RoutePrefix("Mission")]
    public class MissionController : ApiController
    {
        private readonly CityGamesContext context;
        private readonly IMapper mapper;

        public MissionController(CityGamesContext context, IMapper mapper)
        {
            this.context = context;
            this.mapper = mapper;
        }

        [Route("SetCurrentMission"), HttpPost]
        public IHttpActionResult SetCurrentMission(PlayerMissionDto dto)
        {
            if (dto == null)
            {
                return BadRequest(ModelState);
            }

            var currentMission = context.Missions.FirstOrDefault(x => x.Name == dto.MissionName);
            if(currentMission == null)
            {
                return BadRequest();
            }

            var player = context.Players.FirstOrDefault(x => x.User.UserName == dto.UserName);
            if (player == null)
            {
                return BadRequest();
            }

            if (player.CurrentMission == null)
            {
                player.CurrentMission = currentMission;
            }
            else
            {
                return BadRequest("You already have a mission!");
            }
            context.SaveChanges();
            return Ok();
        }

        [Route("GetCurrentMission")]
        [HttpGet]
        public IHttpActionResult GetCurrentMission(string username)
        {
            if (username == null) return BadRequest();
            var player = context.Players.Include("CurrentMission").FirstOrDefault(x => x.User.UserName == username);
            if (player == null)
            {
                return BadRequest();
            }
            var mission = player.CurrentMission;
            if (mission == null) return BadRequest("You don't have a current mission");
            CurrentMissionDto dto = new CurrentMissionDto(mission.Name, mission.Description, mission.ExpReward.ToString());
            return Ok(dto);
        }

        [Route("GetAllMissions"), HttpGet]
        public IHttpActionResult GetAllMissions()
        {
            var ret = context.Missions.ToList();
            return Ok(ret);
        }

        [Route("AbortCurrentMission"), HttpPost]
        public IHttpActionResult AbortCurrentMission(PlayerMissionDto dto)
        {
            if (dto == null)
            {
                return BadRequest(ModelState);
            }

            var player = context.Players.FirstOrDefault(x => x.User.UserName == dto.UserName);
            if (player == null)
            {
                return BadRequest();
            }

            if (player.CurrentMission != null)
            {
                player.CurrentMission = null;
            }
            else
            {
                return BadRequest("You don't have a mission to abort !");
            }
            context.SaveChanges();
            return Ok();
        }

        [Route("AccomplishCurrentMission"), HttpPost]
        public IHttpActionResult AccomplishCurrentMission(PlayerMissionDto dto)
        {
            if (dto == null)
            {
                return BadRequest();
            }

            var player = context.Players.Include(x => x.CurrentMission).FirstOrDefault(x => x.User.UserName == dto.UserName);
            if (player == null)
            {
                return BadRequest();
            }

            if (player.CurrentMission == null)
            {
                return BadRequest("You don't have a mission to accomplish !");
             
            }
            else
            {
                //player.Exp += player.CurrentMission.ExpReward;  <-- generate error, why ?
                player.CurrentMission = null;
            }
            context.SaveChanges();
            return Ok();
        }

    }
}
