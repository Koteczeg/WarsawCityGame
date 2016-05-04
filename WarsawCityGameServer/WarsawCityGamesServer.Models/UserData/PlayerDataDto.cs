using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Models.UserData
{
    public class PlayerProfileDto
    {
        public string Email { get; set; }
        public string Name { get; set; }
        public int Exp { get; set; }
        public string Level { get; set; }
        public string Description { get; set; }
        public string Username { get; set; }
        public byte[] UserImage { get; set; }
    }
}
