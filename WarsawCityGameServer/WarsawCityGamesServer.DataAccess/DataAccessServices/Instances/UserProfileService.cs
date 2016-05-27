using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Validation;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Http.Results;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class UserProfileService : IUserProfileService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly UserManager<User, string> _userManager;

        public UserProfileService(IUnitOfWork unitOfWork, UserManager<User, string> userManager)
        {
            _unitOfWork = unitOfWork;
            _userManager = userManager;
        }


        public async Task<Player> FindPlayer(string username)
        {
            return await Task.Run(() => _unitOfWork.PlayerRepository.Context.Players.First(x => x.User.UserName.Equals(username)));
        }

        public async Task<bool> TryChangePassword(string username, string oldPassword, string newPassword)
        {
            User user = await Task.Run(() => _unitOfWork.UserRepository.GetFirst(x => x.UserName.Equals(username)));
            if (user == null) return false;
            bool b = await _userManager.CheckPasswordAsync(user, newPassword);
            if (!b) return false;
            var result = await _userManager.ChangePasswordAsync(user.Id, oldPassword, newPassword);
            return result != null;
        }

        public async Task<bool> TryChangeUserData(string username, string name, string email, string description, byte[] userImage)
        {
            Player player = await FindPlayer(username);
            if (player == null) return false;
            player.Name = name;
            player.User.Email = email;
            player.Description = description;
            player.UserImage = userImage;
            await Task.Run(() => _unitOfWork.Save());
            return true;
        }

        public async Task<string> GetPlayerLevelName(string username)
        {
            Player player = await Task.Run(() => _unitOfWork.PlayerRepository.GetWithInclude(x => x.User.UserName.Equals(username), "Level").First());
            return player?.Level?.Name;
        }

        public async Task<bool> TryUpdateProfilePicture(byte[] file, string username)
        {
            try
            {
                using (MemoryStream ms = new MemoryStream())
                {
                    var player = await FindPlayer(username);
                    player.UserImage = file;
                    _unitOfWork.Save();
                    return true;
                }
            }
            catch (DbEntityValidationException e)
            {
                return false;
            }
        }
    }
}
