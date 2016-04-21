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
        public IDbSet<User> Users { get; set; }
        public IDbSet<UserAchievements> UserAcheivements { get; set; }
        public IDbSet<Achievement> Achievements { get; set; }
        public IDbSet<Friendships> Friendships { get; set; }
        public IDbSet<Mission> Missions { get; set; }
        public IDbSet<MissionHistory> MissionHistory { get; set; }
        public IDbSet<Place> Places { get; set; }

        static CityGamesContext()
        {
            Database.SetInitializer(new CreateDatabaseIfNotExists<CityGamesContext>());
        }

        public static CityGamesContext Create()
        {
            return new CityGamesContext();
        }
    }
}
