using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.Common.Enums;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Missions;
using System.Collections;
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

        [Route("GetCurrentMission"), HttpPost]
        public IHttpActionResult GetCurrentMission(PlayerMissionDto dto)
        {
            if (dto == null) return BadRequest();
            var player = context.Players.Include("CurrentMission").FirstOrDefault(x => x.User.UserName == dto.UserName);
            if (player == null)
            {
                return BadRequest();
            }
            var mission = player.CurrentMission;
            if (mission == null) return BadRequest("You don't have a current mission");
            return Ok(mission);
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

    }
}
