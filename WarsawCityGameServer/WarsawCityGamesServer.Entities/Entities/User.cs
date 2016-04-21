using System.ComponentModel.DataAnnotations;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class User : Entity
    {
        public string Username { get; set; }
        [EmailAddress]
        public string Email { get; set; }
        public string HashedPassword { get; set; }
        public string Salt { get; set; }
        public byte[] UserImage { get; set; }
        public virtual Level Level { get; set; }
        public int Exp { get; set; }
        public virtual Mission CurrentMission { get; set; }
    }
}
