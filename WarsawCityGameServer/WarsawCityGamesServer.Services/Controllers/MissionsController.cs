using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;

namespace WarsawCityGamesServer.Services.Controllers
{
    [Authorize]
    [RoutePrefix("Missions")]
    public class MissionsController : ApiController
    {
        private readonly IMissionService _service;

        public MissionsController(IMissionService service)
        {
            _service = service;
        }

        [Authorize]
        [HttpGet]
        [Route("GetCurrentMission")]
        public async Task<IHttpActionResult> GetCurrentMission()
        {
            if (User?.Identity?.Name == null)
                return Unauthorized();
            var result = await _service.GetCurrentMissionAsync(User.Identity.Name);
            return Ok(result);
        }

        [Authorize]
        [HttpPost]
        [Route("AbortCurrentMission")]
        public async Task<IHttpActionResult> AbortCurrentMission()
        {
            if (User?.Identity?.Name == null)
                return Unauthorized();
            var result = await _service.AbortCurrentMissionAsync(User.Identity.Name);
            if (!result)
                return BadRequest();
            return Ok();
        }
    }
}
