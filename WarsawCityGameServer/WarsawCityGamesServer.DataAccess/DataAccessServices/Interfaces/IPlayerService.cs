using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IPlayerService
    {
        Task<IdentityResult> AddPlayer(Player user, string username, string password);
        bool CheckUsernameAvailability(string username);
    }
}