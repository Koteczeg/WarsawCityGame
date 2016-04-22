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
        
        [Route("Sample"), HttpGet]
        public IHttpActionResult GetAddress()
        {
            var level = new CityGamesContext().Levels.First();
            return Ok(level.Name);
        }
    }
}
