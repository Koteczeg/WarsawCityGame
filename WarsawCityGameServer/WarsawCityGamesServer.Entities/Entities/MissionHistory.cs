using System;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class MissionHistory : Entity
    {
        public virtual Player Player { get; set; }
        public virtual Mission Mission { get; set; }
        public DateTime FinishDate { get; set; }
    }
}
