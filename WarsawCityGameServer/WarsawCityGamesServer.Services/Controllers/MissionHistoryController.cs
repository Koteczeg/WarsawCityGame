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
    //[Authorize]
    [RoutePrefix("History")]
    public class MissionHistoryController : ApiController
    {
        private readonly IMissionHistoryService _historyService;

        public MissionHistoryController(IMissionHistoryService historyService)
        {
            _historyService = historyService;
        }

        [HttpGet]
        [Route("GetHistory")]
        public async Task<IHttpActionResult> GetAllUserMissions()
        {
            var l= await _historyService.GetHistoryAsync(User.Identity.Name);
            return l != null ? (IHttpActionResult) Ok(l) : BadRequest();
        }

        //Test local
        [HttpGet]
        [Route("GetHistory")]
        public async Task<IHttpActionResult> GetAllUserMissionsLocal(string username)
        {
            var l = await _historyService.GetHistoryAsync(username);
            return l != null ? (IHttpActionResult)Ok(l) : BadRequest();
        }
    }
}
