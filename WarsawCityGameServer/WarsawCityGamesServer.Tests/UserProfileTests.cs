using System.Net.Http;
using System.Security.Principal;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.Results;
using AutoMapper;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Moq;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Instances;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
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
        private readonly UserProfileController controller;
        private Player player;
        public UserProfileTests()
        {
            var levels = new[]
            {
                new Level() {Id = 1, ExpRequired = 0, Name = "Default"}
            };
            var users = new[]
            {
                new User()
                {
                    Email = "bakalam@test.pl",
                    UserName = "bakalam"
                }
            };
            var players = new[]
            {
                new Player()
                {
                    CurrentMission = null, Description = "Halo!", Exp = 110, Name = "Mateusz Bąkała", Id = 1,
                    UserImage = new byte[] {3}, Level = levels[0], User = users[0]
                }
            };
            player = players[0];
            var mockContext = new Mock<CityGamesContext>() { CallBase = true };
            mockContext.Setup(c => c.Players).ReturnsDbSet(players);
            mockContext.Setup(c => c.Levels).ReturnsDbSet(levels);
            var mockUnitOfWork = MockHelper.MockUnitOfWork(mockContext.Object);
            var userStore = new Mock<UserStore<User>>(mockContext.Object);
            var userManager = new Mock<UserManager<User>>(userStore.Object);
            IUserProfileService service = new UserProfileService(mockUnitOfWork.Object, userManager.Object);
            var mapper = CreateMapper();
            controller = new UserProfileController(service, mapper);

            var mockC = new Mock<HttpControllerContext>();
            controller.ControllerContext = mockC.Object;
            controller.ControllerContext.RequestContext.Principal = new GenericPrincipal(new GenericIdentity("bakalam", "Type"), new string[] { });
            controller.Configuration = new HttpConfiguration();
            controller.Request = new HttpRequestMessage();
        }

        [Fact]
        public async void GetProfileDataTest_ForSignedPlayer_ShouldReturnCorrectData()
        {          
            var res = await controller.GetProfileData();
            var ret = res as OkNegotiatedContentResult<PlayerProfileDto>;
            var dto = ret?.Content;

            Assert.NotNull(res);
            Assert.NotNull(ret);
            Assert.NotNull(dto);
            Assert.Equal(player.Description, dto?.Description);
            Assert.Equal(player.Exp, dto?.Exp);
            Assert.Equal(player.Name, dto?.Name);
            Assert.Equal(player.Level?.Name, dto?.Level);
        }

        [Fact]
        public async void GetUserImageTest_ForNotNullImage_ShouldReturnNotNullResult()
        {
            var res = await controller.GetUserImage();
            Assert.NotNull(res);
            Assert.Equal(res, "Aw==");
        }

        [Fact]
        public async void UploadImageTest_ForSignedPlayer_ShouldUploadImage()
        {
            const string newImage = "yolo";
            await controller.UploadProfileImage(newImage);
            var image = await controller.GetUserImage();
            Assert.Equal(image, newImage);
        }

        [Fact]
        public async void ChangeUserDataTest_ForSignedPlayer_ShouldChangeUserData()
        {
            var dto = new PlayerProfileDto()
            {
                Name="programuje w dotnecie",
                Description = "juz trzecie stulecie",
                Email = "yolo@gmail.com",
            };
            await controller.ChangeUserData(dto);
            Assert.Equal(player.Name, dto.Name);
            Assert.Equal(player.Description, dto.Description);
            Assert.Equal(player.User.Email, dto.Email);

        }

        private static IMapper CreateMapper()
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
