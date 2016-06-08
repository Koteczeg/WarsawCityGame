﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.Models.Missions;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces
{
    public interface IMissionHistoryService
    {
        Task<List<MissionHistoryDto>> GetHistoryAsync(string username, int? itemsCount = null);
    }
}
