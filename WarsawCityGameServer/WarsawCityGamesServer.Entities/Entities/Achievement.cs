using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace WarsawCityGamesServer.Entities.Entities
{
    public class Achievement : Entity
    {
        public string Name { get; set; }
        public string Description { get; set; }
    }
}
