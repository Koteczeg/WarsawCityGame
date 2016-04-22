using System.ComponentModel.DataAnnotations;
namespace WarsawCityGamesServer.Models.Players
{
    public class PlayerRegisterDto
    {
        [Required]
        public string UserName { get; set; }
        [Required]
        public string Email { get; set; }
        [Required]
        public string Password { get; set; }
    }
}