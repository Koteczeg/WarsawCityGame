using WarsawCityGamesServer.DataAccess.GenericRepository;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.DataAccess
{
    public interface IUnitOfWork
    {
        GenericRepository<Achievement> AchievementRepository { get; }
        GenericRepository<Friendships> FriendshipsRepository { get; }
        GenericRepository<Level> LevelRepository { get; }
        GenericRepository<MissionHistory> MissionHistoryRepository { get; }
        GenericRepository<Mission> MissionRepository { get; }
        GenericRepository<Place> PlaceRepository { get; }
        GenericRepository<PlayerAchievements> PlayerAchievementsRepository { get; }
        GenericRepository<Player> PlayerRepository { get; }
        GenericRepository<User> UserRepository { get; }
        void Save();
    }
}