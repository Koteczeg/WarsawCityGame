using System.Runtime.Serialization;

namespace WarsawCityGamesServer.Models.Achievements
{
    [DataContract]
    public class AchievementDto
    {
        [DataMember(Name = "name")]
        public string Name { get; set; }
        [DataMember(Name = "description")]
        public string Description { get; set; }
    }
}