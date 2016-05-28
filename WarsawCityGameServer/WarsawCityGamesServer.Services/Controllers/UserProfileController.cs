using System;
using System.Data.Entity;
using System.Data.Entity.Validation;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Runtime.InteropServices;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.UserData;
using WarsawCityGamesServer.Services.Essentials;

namespace WarsawCityGamesServer.Services.Controllers
{
    [Authorize]
    [RoutePrefix("UserProfile")]
    public class UserProfileController : ApiController
    {
        private readonly IMapper _mapper;
        private readonly IUserProfileService _service;

        public UserProfileController(IUserProfileService service, IMapper mapper)
        {
            _service = service;
            _mapper = mapper;
        }

        [Authorize]
        [Route("GetProfileData")]
        [HttpGet]
        public async Task<IHttpActionResult> GetProfileData()
        {
            string username = User.Identity.Name;
            Player player = await _service.FindPlayer(username);
            if (player == null)
                return BadRequest();
            var dto = _mapper.Map<PlayerProfileDto>(player);
            dto.Level = await _service.GetPlayerLevelName(username);
            return Ok(dto);
        }
        [Authorize]
        [Route("GetUserImage")]
        [HttpGet]
        public async Task<string> GetUserImage()
        {
            string username = User.Identity.Name;
            Player player = await _service.FindPlayer(username);
            if (player == null)
                return null;
            return Convert.ToBase64String(player.UserImage);
        }

        [Authorize]
        [HttpPost]
        [Route("Upload")]
        public async Task<IHttpActionResult> Upload([FromBody]string file)
        {
            return await _service.TryUpdateProfilePicture(file, User.Identity.Name) ? (IHttpActionResult)Ok() : BadRequest();
        }

        [Authorize]
        [HttpPost]
        [Route("ChangePassword")]
        public async Task<IHttpActionResult> ChangePassword(string username, string currentPassword, string newPassword)
        {
            return await _service.TryChangePassword(username, currentPassword, newPassword) ? (IHttpActionResult)Ok() : BadRequest();
        }

        [Authorize]
        [HttpPost]
        [Route("ChangeUserData")]
        public async Task<IHttpActionResult> ChangeUserData(PlayerProfileDto dto)
        {
            return await _service.TryChangeUserData(dto.Username, dto.Name, dto.Email, dto.Description) ? (IHttpActionResult)Ok() : BadRequest();
        }
    }
}
