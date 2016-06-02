using System;
using System.Data.Entity;
using System.Diagnostics.CodeAnalysis;
using System.Reflection;
using System.Web;
using System.Web.Http;
using Autofac;
using Autofac.Integration.WebApi;
using AutoMapper;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin;
using Microsoft.Owin.Security;
using Microsoft.Owin.Security.DataProtection;
using Microsoft.Owin.Security.OAuth;
using Owin;
using WarsawCityGamesServer.DataAccess;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Instances;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Players;
using WarsawCityGamesServer.Models.UserData;
using WarsawCityGamesServer.Services;

[assembly: OwinStartup(typeof(Startup))]
namespace WarsawCityGamesServer.Services
{
    public class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            app.UseCors(Microsoft.Owin.Cors.CorsOptions.AllowAll);

            var builder = new ContainerBuilder();
            var config = new HttpConfiguration();

            builder.RegisterApiControllers(Assembly.GetExecutingAssembly());
            builder.RegisterType<CityGamesContext>().AsSelf().As<DbContext>().InstancePerLifetimeScope();
            builder.RegisterType<UnitOfWork>().AsImplementedInterfaces();
            builder.RegisterType<UnitOfWork>().As<UnitOfWork>();
            builder.RegisterWebApiFilterProvider(config);
            RegisterAutoMapperConfiguration(builder);
            RegisterServices(builder);
            RegisterIdentity(app, builder);

            builder.RegisterWebApiFilterProvider(config);
            WebApiConfig.Register(config);

            var container = builder.Build();
            config.DependencyResolver = new AutofacWebApiDependencyResolver(container);
            app.UseAutofacMiddleware(container);
            app.UseAutofacWebApi(config);
            ConfigureOAuth(app);
            app.UseWebApi(config);
        }

        private static void RegisterServices(ContainerBuilder builder)
        {
            builder.RegisterType<PlayerService>().As<IPlayerService>();
            builder.RegisterType<UserProfileService>().As<IUserProfileService>();
            builder.RegisterType<FriendshipsService>().As<IFriendshipsService>();
            builder.RegisterType<RankingService>().AsImplementedInterfaces();
            builder.RegisterType<AchievementsService>().As<IAchievementsService>();
        }

        private static void RegisterAutoMapperConfiguration(ContainerBuilder builder)
        {
            var mapperConfig = new MapperConfiguration(cfg =>
            {
                cfg.CreateMap<Player, PlayerRegisterDto>();
                cfg.CreateMap<PlayerRegisterDto, Player>().
                    ForMember(dest => dest.Exp,
                        opts => opts.MapFrom(src => 0));
                cfg.CreateMap<Player, PlayerProfileDto>()
                    .ForMember(dto => dto.Username, p => p.MapFrom(pl => pl.User.UserName))
                    .ForMember(dto => dto.Email, p => p.MapFrom(pl => pl.User.Email))
                    .ForMember(dto => dto.Level, p => p.MapFrom(s => "Słoik"));
            });
            builder.Register(@void => mapperConfig).AsSelf().SingleInstance();
            builder.Register(context => context.Resolve<MapperConfiguration>()
                .CreateMapper(context.Resolve))
                .As<IMapper>()
                .InstancePerLifetimeScope();
        }

        [SuppressMessage("ReSharper", "RedundantTypeArgumentsOfMethod")]
        private static void RegisterIdentity(IAppBuilder app, ContainerBuilder builder)
        {
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

            builder.RegisterType<SignInManager<User, string>>()
                .AsSelf()
                .InstancePerRequest();

            builder.Register(c => HttpContext.Current.GetOwinContext().Authentication)
                .AsImplementedInterfaces()
                .InstancePerRequest();

            builder.Register<IAuthenticationManager>(c => HttpContext.Current.GetOwinContext().Authentication)
                .InstancePerRequest();
            builder.Register<IDataProtectionProvider>(c => app.GetDataProtectionProvider())
                .InstancePerRequest();
        }

        public static OAuthAuthorizationServerOptions OAuthOptions { get; private set; }

        public static string PublicClientId { get; private set; }

        public void ConfigureOAuth(IAppBuilder app)
        {
            PublicClientId = "self";

            OAuthOptions = new OAuthAuthorizationServerOptions
            {
                TokenEndpointPath = new PathString("/Token"),
                Provider = new ApplicationOAuthProvider(PublicClientId),
                AuthorizeEndpointPath = new PathString("/Account/ExternalLogin"),
                AccessTokenExpireTimeSpan = TimeSpan.FromDays(14),
                AllowInsecureHttp = true
            };

            app.UseOAuthBearerTokens(OAuthOptions);
        }
    }
}