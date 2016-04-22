using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Security.Claims;
using System.Threading.Tasks;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class User : IdentityUser
    {
        /// <summary>
        /// Generates user identity
        /// </summary>
        /// <param name="manager">Identity manager instance</param>
        /// <param name="authenticationType">Authentication type</param>
        /// <returns></returns>
        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<User> manager, string authenticationType)
        {
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);
            return userIdentity;
        }
    }
}