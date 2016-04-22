using System.Linq;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.Common.Enums;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Players;

namespace WarsawCityGamesServer.Services.Controllers
{
    [Authorize]
    [RoutePrefix("Players")]
    public class PlayersController : ApiController
    {
        private readonly CityGamesContext context;
        private readonly UserManager<User, string> userManager;
        private readonly IMapper mapper;

        public PlayersController(CityGamesContext context, UserManager<User, string> userManager, IMapper mapper)
        {
            this.context = context;
            this.userManager = userManager;
            this.mapper = mapper;
        }

        [AllowAnonymous]
        [Route("Register"), HttpPost]
        public IHttpActionResult Register(PlayerRegisterDto newPlayer)
        {
            if (newPlayer == null || !ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var player = mapper.Map<Player>(newPlayer);
            player.Level = context.Levels.FirstOrDefault(x => x.Name==nameof(Role.Player));
            player.Exp = 0;
            var user = new User { UserName = newPlayer.UserName };
            if (context.Users.Any(u => user.UserName == u.UserName))
            {
                return BadRequest("Ta nazwa użytkownika jest już zajęta");
            }
            context.Players.Add(player);
            player.User = user;
            var result = userManager.Create(user, newPlayer.Password);
            if (!result.Succeeded)
            {
                return BadRequest(result.Errors.ToString());
            }
            userManager.AddToRole(user.Id, Role.Player.ToString());
            context.SaveChanges();
            return Ok();
        }
    }
}
