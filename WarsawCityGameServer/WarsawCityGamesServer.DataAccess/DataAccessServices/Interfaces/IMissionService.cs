using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.Models.Missions;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IMissionService
    {
        Task<MissionDto> GetCurrentMissionAsync(string username);
        Task<bool> AbortCurrentMissionAsync(string username);
        Task<bool> SetCurrentMissionAsync(PlayerMissionDto username);
        Task<List<MissionDto>> GetAllMissionsAsync(string username);
    }
}
