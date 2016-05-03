using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Models.UserProfile
{
    public class PlayerProfileDto
    {
        public string Email { get; set; }
        public string Name { get; set; }
        public int Exp { get; set; }
        public Level Level { get; set; }
        public string Description { get; set; }
        public string Username { get; set; }
        public byte[] UserImage { get; set; }
        public string Password { get; set; }
    }
}
