namespace WarsawCityGamesServer.Entities.Entities
{
    public class PlayerAchievements : Entity
    {
        public virtual Player Player { get; set; }
        public virtual Achievement Achievement { get; set; }
    }
}
