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
        [Route("Sample"), HttpGet]
        public IHttpActionResult GetAddress()
        {
            return Ok("ikarłapałraki");
        }
    }
}
