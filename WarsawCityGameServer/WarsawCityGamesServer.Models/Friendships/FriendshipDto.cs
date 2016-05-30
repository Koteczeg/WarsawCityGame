using System.Runtime.Serialization;

namespace WarsawCityGamesServer.Models.Friendships
{
    [DataContract]
    public class FriendshipDto
    {
        [DataMember(Name = "id")]
        public int Id { get; set; }
        [DataMember(Name = "image")]
        public string Image { get; set; }
        [DataMember(Name = "name")]
        public string Name { get; set; } 
    }
}