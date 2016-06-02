using System.Collections.Generic;
using WarsawCityGamesServer.Models.Achievements;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IAchievementsService
    {
        List<AchievementDto> GetUserAchievements(string username);
    }
}