using System;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class Friendships : Entity
    {
        public virtual Player Player { get; set; }
        public virtual Player Friend { get; set; }
        public DateTime? StartRelationDate { get; set; }
    }
}
