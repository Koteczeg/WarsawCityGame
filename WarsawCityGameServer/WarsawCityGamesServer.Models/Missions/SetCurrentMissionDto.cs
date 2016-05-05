using System.ComponentModel.DataAnnotations;

namespace WarsawCityGamesServer.Models.Missions
{
    public class SetCurrentMissionDto
    {
        [Required]
        public string UserName { get; set; }

        [Required]
        public string MissionName { get; set; }
    }
}
