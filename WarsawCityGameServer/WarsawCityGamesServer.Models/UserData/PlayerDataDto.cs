using System.Runtime.Serialization;
using WarsawCityGamesServer.Entities.Entities;

namespace WarsawCityGamesServer.Models.UserData
{
    [DataContract]
    public class PlayerProfileDto
    {
        [DataMember(Name = "Email")]
        public string Email { get; set; }
        [DataMember(Name = "Name")]
        public string Name { get; set; }
        [DataMember]
        public int Exp { get; set; }
        [DataMember(Name = "Level")]
        public string Level { get; set; }
        [DataMember(Name = "Description")]
        public string Description { get; set; }
        [DataMember(Name = "Username")]
        public string Username { get; set; }
        [DataMember(Name = "UserImage")]
        public byte[] UserImage { get; set; }
    }
}
