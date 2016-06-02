using System.Collections.Generic;
using System.Linq;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Achievements;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class AchievementsService : IAchievementsService
    {
        private readonly IUnitOfWork _unitOfWork;

        public AchievementsService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public List<AchievementDto> GetUserAchievements(string username)
        {
            var achievements = _unitOfWork.PlayerAchievementsRepository.DbSet.Where(x => x.Player.User.UserName == username);
            var dto = achievements.Select(x => new AchievementDto
            {
                Name = x.Achievement.Name,
                Description = x.Achievement.Description
            }).ToList();
            return dto;
        }

        public void AssignAchievementToUser(string username, string achievementName)
        {
            var player = _unitOfWork.PlayerRepository.DbSet.FirstOrDefault(x => x.User.UserName.Equals(username));
            var achievement = _unitOfWork.AchievementRepository.DbSet.FirstOrDefault(x => x.Name == achievementName);
            if (player == null || achievement == null) return;
            if (_unitOfWork.PlayerAchievementsRepository.Any(x => x.Player.Id == player.Id && x.Achievement.Id == achievement.Id))
                return;
            var achievementPlayer= new PlayerAchievements
            {
                Player = player,
                Achievement = achievement
            };
            _unitOfWork.PlayerAchievementsRepository.Insert(achievementPlayer);
            _unitOfWork.Save();
        }
    }
}