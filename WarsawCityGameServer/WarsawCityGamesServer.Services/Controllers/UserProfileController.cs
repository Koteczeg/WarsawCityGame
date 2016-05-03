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
        public IHttpActionResult GetProfileData()
        {
            PlayerProfileDto dto = new PlayerProfileDto();
            string username = User.Identity.Name;
            Player player = _context.Players.FirstOrDefault(x => x.User.UserName == username);
            if (player == null)
                return BadRequest();
            
            return Ok(dto);
        }
    }
}
