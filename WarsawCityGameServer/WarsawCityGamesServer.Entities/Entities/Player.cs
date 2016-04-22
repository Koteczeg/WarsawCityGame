using System.ComponentModel.DataAnnotations;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class Player : Entity
    {
        public byte[] UserImage { get; set; }
        public virtual Level Level { get; set; }
        public int Exp { get; set; }
        public virtual Mission CurrentMission { get; set; }
        [Required]
        public virtual User User { get; set; }
    }
}
