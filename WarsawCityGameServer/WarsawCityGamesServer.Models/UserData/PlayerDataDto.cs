using System.Runtime.Serialization;

namespace WarsawCityGamesServer.Models.UserData
{
    [DataContract]
    public class PlayerProfileDto
    {
        [DataMember(Name = "email")]
        public string Email { get; set; }
        [DataMember(Name = "name")]
        public string Name { get; set; }
        [DataMember(Name = "exp")]
        public int Exp { get; set; }
        [DataMember(Name = "level")]
        public string Level { get; set; }
        [DataMember(Name = "description")]
        public string Description { get; set; }
        [DataMember(Name = "username")]
        public string Username { get; set; }
    }
}
