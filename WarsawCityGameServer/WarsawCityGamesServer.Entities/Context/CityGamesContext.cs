using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Entities.Context
{
    public class CityGamesContext : DbContext
    {
        public IDbSet<Level> Levels { get; set; } 
    }
}
