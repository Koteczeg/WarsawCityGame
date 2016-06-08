//using System.Linq;
//using System.Web.Http;
//using AutoMapper;
//using WarsawCityGamesServer.Entities.Context;
//using WarsawCityGamesServer.Models.Missions;
//using System.Data.Entity;
//using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;

//namespace WarsawCityGamesServer.Services.Controllers
//{
//    [Authorize]
//    [RoutePrefix("Mission")]
//    public class MissionController : ApiController
//    {
//        private readonly IMissionService _service;

//        public MissionController(IMissionService service)
//        {
//            _service = service;
//        }

//        [Route("SetCurrentMission"), HttpPost]
//        public IHttpActionResult SetCurrentMission(PlayerMissionDto dto)
//        {
//            var ret = _service.SetCurrentMissionAsync(dto);
//            if (ret.Result) return Ok();
//            return BadRequest();
//        }

//        [Route("GetCurrentMission")]
//        [HttpGet]
//        public IHttpActionResult GetCurrentMission(string username)
//        {
//            var ret = _service.GetCurrentMissionAsync(username);
//            if (ret.Result != null) return Ok(ret.Result);
//            return BadRequest();
//        }

//        [Route("GetAllMissions"), HttpGet]
//        public IHttpActionResult GetAllMissions()
//        {
//            //TODO
//            return Ok();
//        }

//        [Route("AbortCurrentMission"), HttpPost]
//        public IHttpActionResult AbortCurrentMission(string username)
//        {
//            var ret = _service.AbortCurrentMissionAsync(username);
//            if (ret.Result) return Ok();
//            return BadRequest();
//        }

//        [Route("AccomplishCurrentMission"), HttpPost]
//        public IHttpActionResult AccomplishCurrentMission(PlayerMissionDto dto)
//        {
//            //TODO
//            return Ok();
//        }

//    }
//}
