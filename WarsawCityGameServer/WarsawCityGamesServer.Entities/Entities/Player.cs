namespace WarsawCityGamesServer.Entities.Entities
{
    public class Player : Entity
    {
        public byte[] UserImage { get; set; }
        public virtual Level Level { get; set; }
        public int Exp { get; set; }
        public virtual Mission CurrentMission { get; set; }
        public virtual User User { get; set; }
        public string Name { get; set; } = "Guest";
        public string Description { get; set; }
    }
}
