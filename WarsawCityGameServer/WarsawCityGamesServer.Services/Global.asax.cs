using System.Data.Entity;
using System.Reflection;
using System.Web.Http;
using Autofac;
using Autofac.Integration.WebApi;
using AutoMapper;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Players;

namespace WarsawCityGamesServer.Services
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            GlobalConfiguration.Configure(WebApiConfig.Register);
            var builder = new ContainerBuilder();
            builder.RegisterApiControllers(Assembly.GetExecutingAssembly());
            builder.RegisterType<CityGamesContext>().AsSelf().As<DbContext>().InstancePerLifetimeScope();
            var config = new HttpConfiguration();
            builder.RegisterWebApiFilterProvider(config);
            var mapperConfig = new MapperConfiguration(cfg => {
                cfg.CreateMap<Player, PlayerRegisterDto>().ReverseMap();
            });
            builder.Register(@void => mapperConfig).AsSelf().SingleInstance();
            builder.Register(context => context.Resolve<MapperConfiguration>()
            .CreateMapper(context.Resolve))
            .As<IMapper>()
            .InstancePerLifetimeScope();
            builder.RegisterType<UserStore<User>>()
                .As<IUserStore<User, string>>()
                .As<IUserStore<User>>()
                .InstancePerLifetimeScope();
            builder.RegisterType<UserManager<User, string>>()
                .AsSelf()
                .InstancePerRequest();
            builder.RegisterType<UserManager<User>>()
                .AsSelf()
                .InstancePerRequest();

            
            WebApiConfig.Register(config);
            var container = builder.Build();
            config.DependencyResolver = new AutofacWebApiDependencyResolver(container);
            GlobalConfiguration.Configuration.DependencyResolver = new AutofacWebApiDependencyResolver(container);
        }
    }
}
