﻿using System;
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
            var friendships = _unitOfWork.FriendshipsRepository.GetMany(x => x.Player.User.UserName == username);
            return (List<FriendshipDto>) friendships.Select(x => new FriendshipDto
            {
                Id = x.Friend.Id,
                Name = x.Friend.Name,
                Image = Convert.ToBase64String(x.Friend.UserImage)
            });

        }

        public FriendshipDto FindFriend(string username)
        {
            var player = _unitOfWork.PlayerRepository.FirstOrDefault(x => x.User.UserName == username);
            if (player == null) return null;
            return new FriendshipDto
            {
                Id= player.Id,
                Image=Convert.ToBase64String(player.UserImage),
                Name=player.Name
            };
        }
    }
}