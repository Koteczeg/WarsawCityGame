using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Entities.Migrations
{
    using System.Data.Entity.Migrations;

    internal sealed class Configuration : DbMigrationsConfiguration<Context.CityGamesContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(Context.CityGamesContext context)
        {
            context.Levels.Add(new Level {ExpRequired = 1, Id = 1, Name = "test"});
            context.SaveChanges();
        }
    }
}
