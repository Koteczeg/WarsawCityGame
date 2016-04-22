using System.Linq;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using WarsawCityGamesServer.Common.Enums;
using WarsawCityGamesServer.Entities.Context;
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

        protected override void Seed(CityGamesContext context)
        {
            context.Levels.Add(new Level {ExpRequired = 1, Id = 1, Name = "test"});
            GenerateRoles(context);
            context.SaveChanges();
        }

        private static void GenerateRoles(CityGamesContext context)
        {
            var roleStore = new RoleStore<IdentityRole>(context);
            var roleManager = new RoleManager<IdentityRole>(roleStore);
            foreach (var role in from roleName in typeof(Role).GetEnumNames() where !context.Roles.Any(r => r.Name == roleName)
                                 select new IdentityRole { Name = roleName })
            {
                roleManager.Create(role);
            }
        }
    }
}
