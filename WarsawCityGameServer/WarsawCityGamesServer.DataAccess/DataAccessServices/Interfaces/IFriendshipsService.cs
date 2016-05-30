using System.Collections.Generic;
using WarsawCityGamesServer.Models.Friendships;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IFriendshipsService
    {
        List<FriendshipDto> GetFriendsForUser(string username);
        FriendshipDto FindFriend(string username);
    }
}