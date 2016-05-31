using System;
using System.Collections.Generic;
using System.Linq;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Models.Friendships;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class FriendshipsService : IFriendshipsService
    {
        private readonly IUnitOfWork _unitOfWork;

        public FriendshipsService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public List<FriendshipDto> GetFriendsForUser(string username)
        {
            var friendships = _unitOfWork.FriendshipsRepository.DbSet.Where(x => x.Player.User.UserName == username);
            var friends = friendships.ToList().Select(x => new FriendshipDto
            {
                Id = x.Friend.Id,
                Name = x.Friend.Name,
                Image = Convert.ToBase64String(x.Friend.UserImage),
                Username = x.Friend.User.UserName,
                ActionType = "remove"
            }).ToList();
            return friends;
        }

        public FriendshipDto FindFriend(string player_username, string username)
        {
            var player = _unitOfWork.PlayerRepository.DbSet.FirstOrDefault(x => x.User.UserName == username);
            if (player == null) return null;
            var dto = new FriendshipDto
            {
                Id= player.Id,
                Image=Convert.ToBase64String(player.UserImage),
                Name=player.Name,
                Username = player.User.UserName,
            };
            if (!_unitOfWork.FriendshipsRepository.DbSet.Any(x=> x.Player.User.UserName==player_username && x.Friend.User.UserName==username) && player_username!=username)
            {
                dto.ActionType = "add";
            }
            else if (_unitOfWork.FriendshipsRepository.DbSet.Any(x => x.Player.User.UserName == player_username && x.Friend.User.UserName == username) && player_username != username)
            {
                dto.ActionType = "remove";
            }
            else
            {
                dto.ActionType = "other";
            }
            return dto;
        }
    }
}