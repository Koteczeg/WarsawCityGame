using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Moq;
using WarsawCityGamesServer.DataAccess;
using WarsawCityGamesServer.DataAccess.GenericRepository;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Tests
{
    public static class MockHelper
    {
        public static Mock<CityGamesContext> MockDatabase(IEnumerable<Player> players = null, IEnumerable<Level> levels = null, IEnumerable<Achievement> achievements = null,
            IEnumerable<PlayerAchievements> playerAchievements = null, IEnumerable<Friendships> friendships = null, IEnumerable<Mission> missions = null, IEnumerable<MissionHistory> missionHistory = null,
            IEnumerable<Place> places = null)
        {
            var mock = new Mock<CityGamesContext>("test");
            SetMocks(mock, players, levels, achievements, playerAchievements, friendships, missions, missionHistory, places);
            return mock;
        }

        public static void SetMocks(Mock<CityGamesContext> context, IEnumerable<Player> players = null,
            IEnumerable<Level> levels = null, IEnumerable<Achievement> achievements = null,
            IEnumerable<PlayerAchievements> playerAchievements = null, IEnumerable<Friendships> friendships = null,
            IEnumerable<Mission> missions = null, IEnumerable<MissionHistory> missionHistory = null,
            IEnumerable<Place> places = null)
        {
            SetMock(context, m => m.Players, players);
            SetMock(context, m => m.Levels, levels);
            SetMock(context, m => m.Achievements, achievements);
            SetMock(context, m => m.UserAcheivements, playerAchievements);
            SetMock(context, m => m.Friendships, friendships);
            SetMock(context, m => m.Missions, missions);
            SetMock(context, m => m.MissionHistory, missionHistory);
            SetMock(context, m => m.Places, places);
        }

        public static void SetMock<T>(Mock<CityGamesContext> context, Expression<Func<CityGamesContext, IDbSet<T>>> expression,
            IEnumerable<T> values) where T : Entity
        {
            var v = values ?? new List<T>();
            var dbMock = MockDbSetHelper.CreateMockSet(v.AsQueryable());
            context.Setup(expression).Returns(dbMock.Object);
        }

        public static Mock<UnitOfWork> MockUnitOfWork(CityGamesContext context)
        {
            Mock<UnitOfWork> mock = new Mock<UnitOfWork>(context);
            SetRepositiriesMocks(mock, context);
            return mock;
        }

        public static void SetRepositiriesMocks(Mock<UnitOfWork> mock, CityGamesContext context)
        {
            MockGenericRepository(mock, m => m.PlayerRepository, context);
            MockGenericRepository(mock, m => m.LevelRepository, context);
            MockGenericRepository(mock, m => m.AchievementRepository, context);
            MockGenericRepository(mock, m => m.FriendshipsRepository, context);
            MockGenericRepository(mock, m => m.MissionRepository, context);
            MockGenericRepository(mock, m => m.MissionHistoryRepository, context);
            MockGenericRepository(mock, m => m.PlayerAchievementsRepository, context);
            MockGenericRepository(mock, m => m.PlaceRepository, context);
        }

        public static void MockGenericRepository<T>(Mock<UnitOfWork> mock, Expression<Func<UnitOfWork, GenericRepository<T>>> expression, CityGamesContext context)
            where T : class
        {
            var repository = new Mock<GenericRepository<T>>(context);
            mock.Setup(expression).Returns(repository.Object);
        }

        public static Mock<UserManager<User, string>> MockUserManager(CityGamesContext context)
        {
            Mock<IUserStore<User>> store = new Mock<IUserStore<User>>(context);
            return new Mock<UserManager<User, string>>(store.Object);
        }
    }
}
