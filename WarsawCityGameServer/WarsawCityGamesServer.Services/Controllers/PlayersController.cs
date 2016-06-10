using System.Threading.Tasks;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Players;

namespace WarsawCityGamesServer.Services.Controllers
{
    [Authorize]
    [RoutePrefix("Players")]
    public class PlayersController : ApiController
    {
        private readonly IPlayerService service;
        private readonly IMapper mapper;
        private readonly IAchievementsService _achievementsService;

        public PlayersController(IMapper mapper, IPlayerService service, IAchievementsService achievementsService)
        {
            this.mapper = mapper;
            this.service = service;
            _achievementsService = achievementsService;
        }

        [AllowAnonymous]
        [Route("Register"), HttpPost]
        public async Task<IHttpActionResult> Register(PlayerRegisterDto newPlayer)
        {
            if (newPlayer == null || !ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if (!service.CheckUsernameAvailability(newPlayer.UserName))
            {
                return BadRequest("This username has been already taken. Try another one.");
            }
            var player = mapper.Map<Player>(newPlayer);

            var result = await service.AddPlayer(player, newPlayer.UserName, newPlayer.Password, newPlayer.Email);
            _achievementsService.AssignAchievementToUser(newPlayer.UserName, "AccountCreated");
            return !result.Succeeded ? GetErrorResult(result) : Ok();
        }

        private IHttpActionResult GetErrorResult(IdentityResult result)
        {
            if (result == null)
            {
                return InternalServerError();
            }
            if (result.Succeeded) return null;
            if (result.Errors != null)
            {
                ModelState.AddModelError(string.Empty, "Your password is incorrect. It must contain at least 6 characters (letters and numbers).");
            }
            if (ModelState.IsValid)
            {
                return BadRequest();
            }
            return BadRequest(ModelState);
        }
    }
}
