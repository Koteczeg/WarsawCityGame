using System;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class Friendships : Entity
    {
        public virtual User User { get; set; }
        public virtual User Friend { get; set; }
        public DateTime? StartRelationDate { get; set; }
    }
}
