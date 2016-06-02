using System;
using System.Data.Entity.Validation;
using System.Linq;
using System.Threading.Tasks;
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
            return await Task.Run(() => _unitOfWork.PlayerRepository.DbSet.First(x => x.User.UserName.Equals(username)));
        }

        public async Task<bool> TryChangePassword(string username, string oldPassword, string newPassword)
        {
            var user = await Task.Run(() => _unitOfWork.UserRepository.GetFirst(x => x.UserName.Equals(username)));
            if (user == null) return false;
            var b = await _userManager.CheckPasswordAsync(user, newPassword);
            if (!b) return false;
            var result = await _userManager.ChangePasswordAsync(user.Id, oldPassword, newPassword);
            return result != null;
        }

        public async Task<bool> TryChangeUserData(string username, string name, string email, string description)
        {
            var player = await FindPlayer(username);
            if (player == null) return false;
            player.Name = name;
            player.User.Email = email;
            player.Description = description;
            await Task.Run(() => _unitOfWork.Save());
            return true;
        }

        public async Task<string> GetPlayerLevelName(string username)
        {
            var player = await Task.Run(() => _unitOfWork.PlayerRepository.GetWithInclude(x => x.User.UserName.Equals(username), "Level").First());
            return player?.Level?.Name;
        }

        public async Task<bool> TryUpdateProfilePicture(string file, string username)
        {
            try
            {
                    var player = await FindPlayer(username);
                    player.UserImage = Convert.FromBase64String(file);
                    _unitOfWork.Save();
                    return true;
            }
            catch (DbEntityValidationException)
            {
                return false;
            }
        }

        public async Task RemoveUserImage(string username)
        {
            var player = await FindPlayer(username);
            player.UserImage = null;
            _unitOfWork.Save();
        }
    }
}
