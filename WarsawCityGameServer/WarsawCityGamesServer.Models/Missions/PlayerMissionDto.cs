using System.ComponentModel.DataAnnotations;
using System.Runtime.Serialization;

namespace WarsawCityGamesServer.Models.Missions
{
    public class PlayerMissionDto
    {
        [Required]
        [DataMember(Name = "userName")]
        public string UserName { get; set; }

        [Required]
        [DataMember(Name = "missionName")]
        public string MissionName { get; set; }
    }
}
