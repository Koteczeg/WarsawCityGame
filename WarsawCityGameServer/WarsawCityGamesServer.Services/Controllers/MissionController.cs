using System.Linq;
using System.Threading.Tasks;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.Common.Enums;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Missions;

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
        public IHttpActionResult SetCurrentMission(SetCurrentMissionDto mission)
        {
            if (mission == null || !ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var currentMission = context.Missions.FirstOrDefault(x => x.Name == mission.MissionName);
            if(currentMission == null)
            {
                return BadRequest();
            }

            var player = context.Players.FirstOrDefault(x => x.Name == mission.UserName);
            if (player == null)
            {
                return BadRequest();
            }

            player.CurrentMission = currentMission;
            context.SaveChanges();
            return Ok();
        }
 
    }
}
