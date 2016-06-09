using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Missions;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class MissionHistoryService : IMissionHistoryService
    {
        private readonly IUnitOfWork _unitOfWork;

        public MissionHistoryService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<List<MissionHistoryDto>> GetHistoryAsync(string username, int? itemsCount = null)
        {
            return await Task.Run(() => GetHistory(username, itemsCount));
        }

        public async Task<bool> AcceptCurrentMissionAsync(string username)
        {
            return await Task.Run(() => AcceptCurrentMission(username));
        }

        private List<MissionHistoryDto> GetHistory(string username, int? itemsCount = null)
        {
            Player player = _unitOfWork.PlayerRepository.DbSet.FirstOrDefault(x => x.User.UserName.Equals(username));
            if (player == null) return null;
            IQueryable<MissionHistory> l = _unitOfWork.MissionHistoryRepository.DbSet.Where(x => x.Player.Id == player.Id).
                OrderByDescending(s => s.FinishDate).Include(h => h.Mission).Include(h => h.Mission.Place);
            if (itemsCount.HasValue) l = l.Take(itemsCount.Value);
            return l.ToList().Select(x => new MissionHistoryDto()
            {
                UserName = username,
                ExpReward = x.Mission.ExpReward,
                Image = Convert.ToBase64String(x.Mission.Place.Image),
                MissionName = x.Mission.Name,
                PlaceName = x.Mission.Place.Name,
                MissionId = x.Mission.Id,
                MissionDescription = x.Mission.Description,
            }).ToList();
        }

        private bool AcceptCurrentMission(string username)
        {
            Player player = _unitOfWork.PlayerRepository.DbSet.Include(x=>x.CurrentMission).FirstOrDefault(x => x.User.UserName.Equals(username));
            if (player?.CurrentMission == null) return false;
            Mission mission = player.CurrentMission;
            player.Exp += mission.ExpReward;
            MissionHistory missionHistory = new MissionHistory()
            {
                Mission = mission,
                Player = player,
                FinishDate = DateTime.Now
            };
            player.CurrentMission = null;
            _unitOfWork.MissionHistoryRepository.DbSet.Add(missionHistory);
            _unitOfWork.Save();
            return true;
        }
    }
}
