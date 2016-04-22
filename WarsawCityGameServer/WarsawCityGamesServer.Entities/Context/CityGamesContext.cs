using System.Data.Entity;
using Microsoft.AspNet.Identity.EntityFramework;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Entities.Context
{
    public class CityGamesContext : IdentityDbContext<User>
    {
        public IDbSet<Level> Levels { get; set; } 
        public IDbSet<Player> Players { get; set; }
        public IDbSet<PlayerAchievements> UserAcheivements { get; set; }
        public IDbSet<Achievement> Achievements { get; set; }
        public IDbSet<Friendships> Friendships { get; set; }
        public IDbSet<Mission> Missions { get; set; }
        public IDbSet<MissionHistory> MissionHistory { get; set; }
        public IDbSet<Place> Places { get; set; }

        public CityGamesContext() : base("CityGamesContext") { }//DefaultConnection

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
