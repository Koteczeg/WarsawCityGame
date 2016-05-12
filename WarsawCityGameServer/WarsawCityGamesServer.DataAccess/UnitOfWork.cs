using System;
using System.Collections.Generic;
using System.Data.Entity.Validation;
using System.Diagnostics;
using System.Linq;
using WarsawCityGamesServer.DataAccess.GenericRepository;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.DataAccess
{
    public class UnitOfWork : IDisposable
    {
        private readonly CityGamesContext context;

        private GenericRepository<User> userRepository;
        private GenericRepository<Level> levelRepository;
        private GenericRepository<Player> playerRepository;
        private GenericRepository<PlayerAchievements> playerAchievementsRepository;
        private GenericRepository<Achievement> achievementRepository;
        private GenericRepository<Friendships> friendshipsRepository;
        private GenericRepository<Mission> missionRepository;
        private GenericRepository<MissionHistory> missionHistoryRepository;
        private GenericRepository<Place> placeRepository;

        private bool disposed;

        public UnitOfWork(CityGamesContext context)
        {
            this.context = context;
        }

        public GenericRepository<User> UserRepository => userRepository ?? (userRepository = new GenericRepository<User>(context));
        public GenericRepository<Level> LevelRepository => levelRepository ?? (levelRepository = new GenericRepository<Level>(context));
        public GenericRepository<Player> PlayerRepository => playerRepository ?? (playerRepository = new GenericRepository<Player>(context));
        public GenericRepository<PlayerAchievements> PlayerAchievementsRepository => playerAchievementsRepository ?? (playerAchievementsRepository = new GenericRepository<PlayerAchievements>(context));
        public GenericRepository<Achievement> AchievementRepository => achievementRepository ?? (achievementRepository = new GenericRepository<Achievement>(context));
        public GenericRepository<Friendships> FriendshipsRepository => friendshipsRepository ?? (friendshipsRepository = new GenericRepository<Friendships>(context));
        public GenericRepository<Mission> MissionRepository => missionRepository ?? (missionRepository = new GenericRepository<Mission>(context));
        public GenericRepository<MissionHistory> MissionHistoryRepository => missionHistoryRepository ?? (missionHistoryRepository = new GenericRepository<MissionHistory>(context));
        public GenericRepository<Place> PlaceRepository => placeRepository ?? (placeRepository = new GenericRepository<Place>(context));


        public void Save()
        {
            try
            {
                context.SaveChanges();
            }
            catch (DbEntityValidationException e)
            {

                var outputLines = new List<string>();
                foreach (var eve in e.EntityValidationErrors)
                {
                    outputLines.Add($"{DateTime.Now}: Entity of type \"{eve.Entry.Entity.GetType().Name}\" in state \"{eve.Entry.State}\" has the following validation errors:");
                    outputLines.AddRange(eve.ValidationErrors.Select(ve => $"- Property: \"{ve.PropertyName}\", Error: \"{ve.ErrorMessage}\""));
                }
                System.IO.File.AppendAllLines(@"C:\errors.txt", outputLines);
                throw;
            }
        }

        protected virtual void Dispose(bool disposing)
        {
            if (!disposed)
            {
                if (disposing)
                {
                    Debug.WriteLine("UnitOfWork is being disposed");
                    context.Dispose();
                }
            }
            disposed = true;
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }
    }
}