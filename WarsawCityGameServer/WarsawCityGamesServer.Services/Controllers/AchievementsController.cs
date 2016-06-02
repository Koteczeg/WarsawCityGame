using System.Web.Http;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;

namespace WarsawCityGamesServer.Services.Controllers
{
    [RoutePrefix("Achievements")]
    public class AchievementsController : ApiController
    {
        private readonly IAchievementsService _service;

        public AchievementsController(IAchievementsService service)
        {
            _service = service;
        }

        [Authorize]
        [HttpPost]
        [Route("GetUserAchievements")]
        public IHttpActionResult GetUserAchievements()
        {
            var username = User.Identity.Name;
            var achievements = _service.GetUserAchievements(username);
            return Ok(achievements);
        }

        [Authorize]
        [HttpPost]
        [Route("AssignAchievement")]
        public IHttpActionResult AssignAchievement(string achievementName)
        {
            var username = User.Identity.Name;
            _service.AssignAchievementToUser(username, achievementName);
            return Ok();
        }
    }
}
