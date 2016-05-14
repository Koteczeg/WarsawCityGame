using System;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
        public void Test1()
        {

        }

        [Fact]
        public async Task Test2()
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
            

            var mockContext = new Mock<CityGamesContext>();
            mockContext.Setup(m => m.Players).ReturnsDbSet(players);
            mockContext.Setup(m => m.Levels).ReturnsDbSet(levels);
            mockContext.Setup(m => m.Set<Player>()).Returns(MockDbSetHelper.CreateMockSet(players.AsQueryable()).Object);
            mockContext.Setup(m => m.Set<Level>()).Returns(MockDbSetHelper.CreateMockSet(levels.AsQueryable()).Object);
            var mockStore = new Mock<UserStore<User>>(mockContext.Object);
            var mockUserManager = new Mock<UserManager<User,string>>(mockStore.Object);
            var mockUnitOfWork = new Mock<UnitOfWork>(mockContext.Object);
            var mockPlayersRep = new Mock<GenericRepository<Player>>(mockContext.Object);
            var mockLevelsRep = new Mock<GenericRepository<Level>>(mockContext.Object);
            mockUnitOfWork.Setup(m => m.LevelRepository).Returns(mockLevelsRep.Object);
            mockUnitOfWork.Setup(m => m.PlayerRepository).Returns(mockPlayersRep.Object);
            IUserProfileService service = new UserProfileService(mockUnitOfWork.Object, mockUserManager.Object);
            IMapper mapper = CreateMapper();
            var controller = new UserProfileController(service, mapper);

            var res = await controller.GetProfileData("bakalam");
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
            });
            return mapperConfig.CreateMapper();
        }
    }
}
