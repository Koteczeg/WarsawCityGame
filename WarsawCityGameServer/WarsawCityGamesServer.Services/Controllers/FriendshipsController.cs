using System.Web.Http;
using WarsawCityGamesServer.DataAccess.DataAccessServices.Interfaces;

namespace WarsawCityGamesServer.Services.Controllers
{
    [RoutePrefix("Friendships")]
    public class FriendshipsController : ApiController
    {
        private readonly IFriendshipsService _service;

        public FriendshipsController(IFriendshipsService service)
        {
            _service = service;
        }

        [Authorize]
        [HttpPost]
        [Route("GetFriends")]
        public IHttpActionResult GetFriendsForUser()
        {
            var username = User.Identity.Name;
            var friends = _service.GetFriendsForUser(username);
            return Ok(friends);
        }

        [Authorize]
        [HttpPost]
        [Route("FindFriend")]
        public IHttpActionResult FindFriend(string username)
        {
            var player = _service.FindFriend(User.Identity.Name,username);
            return Ok(player);
        }

        [Authorize]
        [HttpPost]
        [Route("AssignFriend")]
        public IHttpActionResult AssignFriend(int playerId)
        {
            _service.AssignFriend(User.Identity.Name, playerId);
            return Ok();
        }

        [Authorize]
        [HttpPost]
        [Route("RemoveFriend")]
        public IHttpActionResult RemoveFriend(int playerId)
        {
            _service.RemoveFriend(User.Identity.Name, playerId);
            return Ok();
        }
    }
}
