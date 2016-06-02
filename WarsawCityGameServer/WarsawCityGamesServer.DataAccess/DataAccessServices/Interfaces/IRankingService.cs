using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.Models.Players;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IRankingService
    {
        Task<List<PlayerRankingDto>> GetRankingPlayersForUserAsync(string username);
    }
}
