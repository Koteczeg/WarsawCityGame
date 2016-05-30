using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using AutoMapper;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;

namespace WarsawCityGamesServer.Services.Controllers
{
    public class FriendshipsController : ApiController
    {
        private readonly IMapper _mapper;
        private readonly IFriendshipsService _service;

        public FriendshipsController(IFriendshipsService service, IMapper mapper)
        {
            _service = service;
            _mapper = mapper;
        }
    }
}
