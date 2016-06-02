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
    [RoutePrefix("Ranking")]
    public class RankingController : ApiController
    {
        private readonly IRankingService _service;

        public RankingController(IRankingService service)
        {
            _service = service;
        }

        [Authorize]
        [HttpPost]
        [Route("GetRanking")]
        public async Task<IHttpActionResult> GetRankingForPlayer()
        {
            var list = await _service.GetRankingPlayersForUserAsync(User.Identity.Name);
            return Ok(list);
        }
    }
}
