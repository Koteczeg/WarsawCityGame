using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WarsawCityGamesServer.Models.Players
{
    public class PlayerRankingDto
    {
        public string PlayerLogin { get; set; }
        public string PlayerName { get; set; }
        public int PlayerExp { get; set; }
        public byte[] PlayerImage { get; set; }
        public int LevelNumber { get; set; }
        public string LevelName { get; set; }
    }
}
