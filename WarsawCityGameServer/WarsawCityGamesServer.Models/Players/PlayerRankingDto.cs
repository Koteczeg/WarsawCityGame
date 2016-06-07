using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace WarsawCityGamesServer.Models.Players
{
    [DataContract]
    public class PlayerRankingDto
    {
        [DataMember(Name = "PlayerLogin")]
        public string PlayerLogin { get; set; }
        [DataMember(Name = "PlayerName")]
        public string PlayerName { get; set; }
        [DataMember(Name = "PlayerExp")]
        public int PlayerExp { get; set; }
        [DataMember(Name = "PlayerImage")]
        public string PlayerImage { get; set; }
        [DataMember(Name = "LevelNumber")]
        public int LevelNumber { get; set; }
        [DataMember(Name = "LevelName")]
        public string LevelName { get; set; }
        [DataMember(Name = "isCurrentPlayer")]
        public bool IsCurrentPlayer { get; set; }
    }
}
