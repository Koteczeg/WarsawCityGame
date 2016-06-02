using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Models.Players;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class RankingService : IRankingService
    {
        private readonly IUnitOfWork _unitOfWork;

        public RankingService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        } 

        public async Task<List<PlayerRankingDto>> GetRankingPlayersForUserAsync(string username)
        {
            return await Task.Run(()=> GetRankingPlayersForUser(username));
        }

        private List<PlayerRankingDto> GetRankingPlayersForUser(string username)
        {
            var friendships = _unitOfWork.FriendshipsRepository.DbSet.Where(x => x.Player.User.UserName.Equals(username));
            var friends = friendships.Select(f => f.Friend).Include(p => p.Level).Include(p => p.User);
            return friends.Select(x => new PlayerRankingDto()
            {
                PlayerName = x.Name,
                PlayerLogin = x.User.UserName,
                PlayerExp = x.Exp,
                PlayerImage = Convert.ToBase64String(x.UserImage),
                LevelName = x.Level.Name,
                LevelNumber = x.Level.Id
            }).OrderByDescending(x=>x.PlayerExp).ToList();
        }
    }
}
