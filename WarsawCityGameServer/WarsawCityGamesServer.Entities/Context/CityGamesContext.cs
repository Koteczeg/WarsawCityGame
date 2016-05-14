using System.Data.Entity;
using Microsoft.AspNet.Identity.EntityFramework;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Entities.Context
{
    public class CityGamesContext : IdentityDbContext<User>
    {
        public virtual IDbSet<Level> Levels { get; set; } 
        public virtual IDbSet<Player> Players { get; set; }
        public virtual IDbSet<PlayerAchievements> UserAcheivements { get; set; }
        public virtual IDbSet<Achievement> Achievements { get; set; }
        public virtual IDbSet<Friendships> Friendships { get; set; }
        public virtual IDbSet<Mission> Missions { get; set; }
        public virtual IDbSet<MissionHistory> MissionHistory { get; set; }
        public virtual IDbSet<Place> Places { get; set; }

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
