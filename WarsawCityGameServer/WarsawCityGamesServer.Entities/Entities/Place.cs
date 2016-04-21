namespace WarsawCityGamesServer.Entities.Entities
{
    public class Place : Entity
    {
        public string Name { get; set; }
        public double XCoordinate { get; set; }
        public double YCoordinate { get; set; }
        public byte[] Image { get; set; }
    }
}
