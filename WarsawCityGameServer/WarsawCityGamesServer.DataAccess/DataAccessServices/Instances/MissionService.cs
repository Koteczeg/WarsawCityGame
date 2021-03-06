﻿using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.Missions;

namespace WarsawCityGamesServer.DataAccess.DataAccessServices.Instances
{
    public class MissionService : IMissionService
    {
        private readonly IUnitOfWork _unitOfWork;

        public MissionService(IUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;
        }

        public async Task<List<MissionDto>> GetAllMissionsAsync(string username)
        {
            return await Task.Run(() => GetAllMissions(username));
        }

        public async Task<MissionDto> GetCurrentMissionAsync(string username)
        {
            return await Task.Run(() => GetCurrentMission(username));
        }

        public async Task<bool> AbortCurrentMissionAsync(string username)
        {
            return await Task.Run(() => AbortCurrentMission(username));
        }

        public async Task<bool> SetCurrentMissionAsync(PlayerMissionDto dto)
        {
            return await Task.Run(() => SetCurrentMission(dto));
        }

        private List<MissionDto> GetAllMissions(string username)
        {
            var list = new List<MissionDto>();
            var player = _unitOfWork.PlayerRepository.DbSet.Include(p=>p.User).Include(p=>p.Level).
                FirstOrDefault(p => p.User.UserName.Equals(username));
            if (player == null) return list;
            var his = _unitOfWork.MissionHistoryRepository.DbSet.Where(x => x.Player.Id == player.Id);
            var missions =
                _unitOfWork.MissionRepository.DbSet.Where(x => (x.MinimumLevel.ExpRequired <= player.Exp))
                .Include(m=>m.Place);
            missions = missions.Where(x => !his.Any(h => h.Mission.Id == x.Id));
            foreach (var mission in missions.ToList())
            {
                list.Add(new MissionDto
                {
                    MissionId = mission.Id,
                    UserName = player.User.UserName,
                    ExpReward = mission.ExpReward,
                    Image = mission.Place.Image!=null ? Convert.ToBase64String(mission.Place.Image) : null,
                    MinimalLevelName = player.Level.Name,
                    MinimalLevelNumber = player.Level.Id,
                    MissionDescription = mission.Description,
                    MissionName = mission.Name,
                    PlaceName = mission.Place.Name,
                    PlaceX = mission.Place.XCoordinate,
                    PlaceY = mission.Place.YCoordinate
                });
            }
            return list;
        }

        private MissionDto GetCurrentMission(string username)
        {
            var player = _unitOfWork.PlayerRepository.DbSet.FirstOrDefault(p => p.User.UserName.Equals(username));
            if (player?.CurrentMission == null) return null;
            Mission mission = _unitOfWork.MissionRepository.FirstOrDefault(m => m.Id == player.CurrentMission.Id);
            MissionDto dto = new MissionDto();
            dto.MissionId = mission.Id;
            dto.UserName = player.User.UserName;
            dto.ExpReward = mission.ExpReward;
            dto.Image = Convert.ToBase64String(mission.Place.Image);
            dto.MinimalLevelName = player.Level.Name;
            dto.MinimalLevelNumber = player.Level.Id;
            dto.MissionDescription = mission.Description;
            dto.MissionName = mission.Name;
            dto.PlaceName = mission.Place.Name;
            dto.PlaceX = mission.Place.XCoordinate;
            dto.PlaceY = mission.Place.YCoordinate;
            return dto;
        }

        private bool AbortCurrentMission(string username)
        {
            var player = _unitOfWork.PlayerRepository.DbSet.FirstOrDefault(p => p.User.UserName.Equals(username));
            if (player?.CurrentMission == null) return false;
            player.CurrentMission = null;
            _unitOfWork.Save();
            return true;
        }

        private bool SetCurrentMission(PlayerMissionDto dto)
        {

            var mission = _unitOfWork.MissionRepository.DbSet.FirstOrDefault(x => x.Name == dto.MissionName);
            if (mission == null) return false;

            var player = _unitOfWork.PlayerRepository.DbSet.FirstOrDefault(x => x.User.UserName == dto.UserName);
            if (player == null) return false;

            if (player.CurrentMission != null) return false;

            player.CurrentMission = mission;
            _unitOfWork.Save();
            return true;
        }


    }
}
