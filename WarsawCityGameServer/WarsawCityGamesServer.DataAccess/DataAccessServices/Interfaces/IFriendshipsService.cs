using System.Collections.Generic;
using WarsawCityGamesServer.Models.Friendships;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IFriendshipsService
    {
        List<FriendshipDto> GetFriendsForUser(string username);
        FriendshipDto FindFriend(string player_username, string username);
        void AssignFriend(string username, int playerId);
        void RemoveFriend(string username, int playerId);
    }
}