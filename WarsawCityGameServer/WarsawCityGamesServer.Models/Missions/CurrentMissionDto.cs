using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace WarsawCityGamesServer.Models.Missions
{
    public class CurrentMissionDto
    {
        [DataMember(Name = "name")]
        public string Name { get; set; }

        [DataMember(Name = "description")]
        public string Description { get; set; }

        [DataMember(Name = "expReward")]
        public string ExpReward { get; set; }

        public CurrentMissionDto(string Name, string Description, string ExpReward)
        {
            this.Description = Description;
            this.ExpReward = ExpReward;
            this.Name = Name;
        }
    }

}
