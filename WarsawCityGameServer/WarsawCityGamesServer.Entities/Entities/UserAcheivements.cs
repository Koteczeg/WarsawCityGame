namespace WarsawCityGamesServer.Entities.Entities
{
    public class UserAcheivements
    {
        public virtual User User { get; set; }
        public virtual Achievement Achievement { get; set; }
    }
}
