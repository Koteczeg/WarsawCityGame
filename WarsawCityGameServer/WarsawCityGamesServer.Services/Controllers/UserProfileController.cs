using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.UserData;

namespace WarsawCityGamesServer.Services.Controllers
{
    [Authorize]
    [RoutePrefix("UserProfile")]
    public class UserProfileController : ApiController
    {
        private readonly CityGamesContext _context;
        private readonly UserManager<User, string> _userManager;
        private readonly IMapper _mapper;

        public UserProfileController(CityGamesContext context, UserManager<User, string> userManager, IMapper mapper)
        {
            _context = context;
            _userManager = userManager;
            _mapper = mapper;
        }

        [Authorize]
        [Route("GetProfileData")]
        [HttpGet]
        public IHttpActionResult GetProfileData(string username)
        {
            PlayerProfileDto dto = new PlayerProfileDto();
            Player player = _context.Players.Include(x=>x.Level).FirstOrDefault(x => x.User.UserName == username);
            if (player == null)
                return BadRequest();
            Level level = player.Level;
            dto.Level = _context.Levels.FirstOrDefault(x => x.Id == level.Id)?.Name;
            dto.Description = player.Description;
            dto.Email = player.User.Email;
            dto.Exp = player.Exp;
            dto.Name = player.Name;
            dto.UserImage = player.UserImage;
            dto.Username = player.User.UserName;
            return Ok(dto);
        }

        [Authorize]
        [HttpPost]
        [Route("ChangePassword")]
        public IHttpActionResult ChangePassword(string username, string currentPassword, string newPassword)
        {
            Player player = _context.Players.FirstOrDefault(x => x.User.UserName == username);
            if (player == null)
                return BadRequest();
            var identity = _userManager.ChangePassword(player.User.Id, currentPassword, newPassword);
            return identity != null || _userManager.CheckPassword(player.User, newPassword) ? (IHttpActionResult)Ok() : BadRequest();
        }

        [Authorize]
        [HttpPost]
        [Route("ChangeUserData")]
        public IHttpActionResult ChangeUserData(PlayerProfileDto dto)
        {
            string username = dto.Username;
            Player player = _context.Players.FirstOrDefault(x => x.User.UserName == username);
            if (player == null)
                return BadRequest();
            try
            {
                player.Description = dto.Description;
                player.User.Email = dto.Email;
                player.Name = dto.Name;
                player.UserImage = dto.UserImage;
                _context.SaveChanges();
                return Ok();
            }
            catch (Exception)
            {
                return BadRequest();
            }
        }
    }
}
