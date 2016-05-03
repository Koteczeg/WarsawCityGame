namespace WarsawCityGamesServer.Entities.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class PlayerExtensions : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Players", "Name", c => c.String());
            AddColumn("dbo.Players", "Description", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.Players", "Description");
            DropColumn("dbo.Players", "Name");
        }
    }
}
