﻿using System;
using System.Linq;
using System.Net.Http;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.Results;
using AutoMapper;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Moq;
using WarsawCityGamesServer.DataAccess;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Instances;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.DataAccess.GenericRepository;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Players;
using WarsawCityGamesServer.Models.UserData;
using WarsawCityGamesServer.Services.Controllers;
using Xunit;

namespace WarsawCityGamesServer.Tests
{
    public class UserProfileTests
    {
        [Fact]
        public async void GetUserDataTest()
        {
            var levels = new Level[]
            {
                new Level() {Id = 1, ExpRequired = 0, Name = "Default"}
            };

            var users = new User[]
            {
                new User()
                {
                    Email = "bakalam@test.pl",
                    UserName = "bakalam"
                }
            };

            var players = new Player[]
            {
                new Player()
                {
                    CurrentMission = null, Description = "Halo!", Exp = 110, Name = "Mateusz Bąkała", Id = 1,
                    UserImage = null, Level = levels[0], User = users[0]
                }
            };
            var mockContext = new Mock<CityGamesContext>() { CallBase = true };
            mockContext.Setup(c => c.Players).ReturnsDbSet(players);
            mockContext.Setup(c => c.Levels).ReturnsDbSet(levels);

            Assert.Equal(mockContext.Object.Players.Count(), 1);
            //mockContext.Setup(c => c.Users).ReturnsDbSet(users);

            var mockUnitOfWork = MockHelper.MockUnitOfWork(mockContext.Object);
            IUserProfileService service = new UserProfileService(mockUnitOfWork.Object, null);
            IMapper mapper = CreateMapper();
            var controller = new UserProfileController(service, mapper);

            var mockC = new Mock<HttpControllerContext>();
            controller.ControllerContext = mockC.Object;
            controller.ControllerContext.RequestContext.Principal = new GenericPrincipal(new GenericIdentity("bakalam", "Type"), new string[] { });
            controller.Configuration = new HttpConfiguration();
            controller.Request = new HttpRequestMessage();
            var res = await controller.GetProfileData();
            var ret = res as OkNegotiatedContentResult<PlayerProfileDto>;
            var dto = ret?.Content;

            Assert.NotNull(res);
            Assert.NotNull(ret);
            Assert.NotNull(dto);

            Assert.Equal(players[0].Description, dto?.Description);
            Assert.Equal(players[0].Exp, dto?.Exp);
            Assert.Equal(players[0].Name, dto?.Name);
            Assert.Equal(players[0].Level?.Name, dto?.Level);
        }

        private IMapper CreateMapper()
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
            return mapperConfig.CreateMapper();
        }
    }
}
