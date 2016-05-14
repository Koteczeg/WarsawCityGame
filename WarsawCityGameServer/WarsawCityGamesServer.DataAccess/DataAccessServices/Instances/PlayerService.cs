using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.Common.Enums;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Entities;
namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class PlayerService : IPlayerService
    {
        private readonly UnitOfWork unitOfWork;
        private readonly UserManager<User, string> userManager;

        public PlayerService(UnitOfWork unitOfWork, UserManager<User, string> userManager)
        {
            this.userManager = userManager;
            this.unitOfWork=unitOfWork;
        }

        public async Task<IdentityResult> AddPlayer(Player player, string username, string password)
        {
            player.Level = unitOfWork.LevelRepository.GetFirst(x => x.Id == 1);
            var user = new User { UserName = username };
            unitOfWork.PlayerRepository.Insert(player);
            player.User = user;
            var result = await userManager.CreateAsync(user, password);
            if (!result.Succeeded)
            {
                return result;
            }
            userManager.AddToRole(user.Id, Role.Player.ToString());
            unitOfWork.Save();
            return result;
        }

        public bool CheckUsernameAvailability(string username)
        {
            return !unitOfWork.UserRepository.Any(u => username == u.UserName);
        }
    }
}