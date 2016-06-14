using System.Collections.Generic;
using System.Net.Http;
using System.Security.Principal;
using System.Web.Http;
using System.Web.Http.Controllers;
using System.Web.Http.Results;
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
    public class RankingTests
    {
        private readonly RankingController controller;
        private readonly Player player;
        public RankingTests()
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
            IRankingService service = new RankingService(mockUnitOfWork.Object);
            controller = new RankingController(service);

            var mockC = new Mock<HttpControllerContext>();
            controller.ControllerContext = mockC.Object;
            controller.ControllerContext.RequestContext.Principal = new GenericPrincipal(new GenericIdentity("bakalam", "Type"), new string[] { });
            controller.Configuration = new HttpConfiguration();
            controller.Request = new HttpRequestMessage();
        }

        [Fact]
        public async void GetProfileDataTest_ForSignedPlayer_ShouldReturnCorrectData()
        {
            var res = await controller.GetRankingForPlayer();
            var ret = res as OkNegotiatedContentResult<List<PlayerRankingDto>>;
            var dto = ret?.Content;

            Assert.NotNull(res);
            Assert.NotNull(ret);
            Assert.NotNull(dto);
        }
    }
}