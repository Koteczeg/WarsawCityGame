using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace WarsawCityGamesServer.Models.Missions
{
    [DataContract]
    public class MissionHistoryDto
    {
        [DataMember(Name = "missionId")]
        public int MissionId { get; set; }
        [DataMember(Name = "missionName")]
        public string MissionName { get; set; }
        [DataMember(Name = "missionDescription")]
        public string MissionDescription { get; set; }
        [DataMember(Name = "expReward")]
        public int ExpReward { get; set; }
        [DataMember(Name = "placeName")]
        public string PlaceName { get; set; }
        [DataMember(Name = "image")]
        public string Image { get; set; }
        [DataMember(Name = "userName")]
        public string UserName { get; set; }
    }
}
