using System;
using System.Threading.Tasks;
using System.Web.Http;
using AutoMapper;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;
using WarsawCityGamesServer.Models.UserData;

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
            var username = User.Identity.Name;
            var player = await _service.FindPlayer(username);
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
            var username = User.Identity.Name;
            var player = await _service.FindPlayer(username);
            return player == null ? null : Convert.ToBase64String(player.UserImage);
        }

        [Authorize]
        [HttpPost]
        [Route("Upload")]
        public async Task<IHttpActionResult> UploadProfileImage([FromBody]string file)
        {
            return await _service.TryUpdateProfilePicture(file, User.Identity.Name) ? (IHttpActionResult)Ok() : BadRequest();
        }

        [Authorize]
        [HttpPost]
        [Route("ChangePassword")]
        public async Task<IHttpActionResult> ChangePassword(string currentPassword, string newPassword)
        {
            return await _service.TryChangePassword(User.Identity.Name,currentPassword, newPassword) ? (IHttpActionResult)Ok() : BadRequest();
        }

        [Authorize]
        [HttpPost]
        [Route("ChangeUserData")]
        public async Task<IHttpActionResult> ChangeUserData(PlayerProfileDto dto)
        {
            return await _service.TryChangeUserData(User.Identity.Name,dto.Name, dto.Email, dto.Description) ? (IHttpActionResult)Ok() : BadRequest();
        }

        [Authorize]
        [HttpPost]
        [Route("RemoveImage")]
        public async Task<IHttpActionResult> RemoveImage()
        {
            try
            {
                await _service.RemoveUserImage(User.Identity.Name);
                return Ok();
            }
            catch (Exception)
            {
                return BadRequest();
            }
        }
    }
}
