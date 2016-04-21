namespace WarsawCityGamesServer.Entities.Entities
{
    public class UserAchievements : Entity
    {
        public virtual User User { get; set; }
        public virtual Achievement Achievement { get; set; }
    }
}
