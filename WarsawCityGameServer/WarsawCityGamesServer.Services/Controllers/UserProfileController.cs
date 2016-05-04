using System;
using System.Collections.Generic;
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
            Player player = _context.Players.FirstOrDefault(x => x.User.UserName == username);
            if (player == null)
                return BadRequest();
            dto.Level = player.Level.Name;
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
            _userManager.ChangePassword(player.User.Id, currentPassword, newPassword);
            return Ok();
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
            player.Description = player.Description;
            player.User.Email = player.User.Email;
            player.Name = player.Name;
            player.UserImage = player.UserImage;
            _context.SaveChanges();
            return Ok(dto);
        }
    }
}
