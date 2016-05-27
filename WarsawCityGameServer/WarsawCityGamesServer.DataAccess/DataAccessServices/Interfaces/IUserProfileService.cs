using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IUserProfileService
    {
        Task<Player> FindPlayer(string username);
        Task<bool> TryChangePassword(string username, string oldPassword, string newPassword);
        Task<bool> TryChangeUserData(string username, string name, string email, string description, byte[] userImage);
        Task<string> GetPlayerLevelName(string username);
        Task<bool> TryUpdateProfilePicture(byte[] file, string username);
    }
}
