using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WarsawCityGamesServer.Entities.Context;

namespace WarsawCityGamesServer.Services.Controllers
{
    [RoutePrefix("Test")]
    public class TestController : ApiController
    {
        [AllowAnonymous]
        [Route("Test"), HttpGet]
        public string Register()
        {
            var ctx = new CityGamesContext();
            return ctx.Players.Count().ToString();
            return "elo";
        }
    }
}
