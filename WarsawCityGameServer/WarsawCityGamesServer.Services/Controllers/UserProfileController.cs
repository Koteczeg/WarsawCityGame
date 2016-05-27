using System;
using System.Data.Entity;
using System.Linq;
using System.Runtime.InteropServices;
using System.Threading.Tasks;
using System.Web.Http;
using AutoMapper;
using Microsoft.AspNet.Identity;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Entities.Context;
using WarsawCityGamesServer.Entities.Entities;
using WarsawCityGamesServer.Models.UserData;

namespace WarsawCityGamesServer.Services.Controllers
{
    //[Authorize]
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

        //[Authorize]
        [Route("GetProfileData")]
        [HttpGet]
        public async Task<IHttpActionResult> GetProfileData(string username)
        {
            Player player = await _service.FindPlayer(username);
            if (player == null)
                return BadRequest();
            var dto = _mapper.Map<PlayerProfileDto>(player);
            dto.Level = await _service.GetPlayerLevelName(username);
            return Ok(dto);
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
            return await _service.TryChangeUserData(dto.Username, dto.Name, dto.Email, dto.Description, dto.UserImage) ? (IHttpActionResult)Ok() : BadRequest();
        }
    }
}
