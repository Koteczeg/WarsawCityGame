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
        public TestController()
        {
            
        }
        [Route("Sample"), HttpGet]
        public IHttpActionResult GetAddress()
        {
            //var level = new CityGamesContext().Levels.First();//.Levels.Find(1);
            return Ok("PASSED"); //level.Name
        }
    }
}
