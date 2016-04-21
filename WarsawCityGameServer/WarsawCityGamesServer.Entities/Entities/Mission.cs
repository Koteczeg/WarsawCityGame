namespace WarsawCityGamesServer.Entities.Entities
{
    public class Mission : Entity
    {
        public virtual Place Place { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int ExpReward { get; set; }
        public virtual Level MinimumLevel { get; set; } 
    }
}
