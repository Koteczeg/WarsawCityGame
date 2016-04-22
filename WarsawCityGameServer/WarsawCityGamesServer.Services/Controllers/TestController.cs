using System.Linq;
using System.Web.Http;
using WarsawCityGamesServer.Entities.Context;
namespace WarsawCityGamesServer.Services.Controllers
{
    [RoutePrefix("Test")]
    public class TestController : ApiController
    {
        private readonly CityGamesContext context;

        public TestController(CityGamesContext context)
        {
            this.context = context;
        }
        [Authorize]
        [Route("AuthorizationSample"), HttpGet]
        public IHttpActionResult Sample()
        {
            var level = context.Levels.First();
            return Ok(level.Name);
        }

        [Route("NoAuthorizationSample"), HttpGet]
        public IHttpActionResult Sample2()
        {
            var level = context.Levels.First();
            return Ok(level.Name);
        }
    }
}
