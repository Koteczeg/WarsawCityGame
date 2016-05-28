namespace WarsawCityGamesServer.Entities.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class DetachingRequired : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Players", "User_Id", "dbo.AspNetUsers");
            DropIndex("dbo.Players", new[] { "User_Id" });
            AlterColumn("dbo.Players", "User_Id", c => c.String(maxLength: 128));
            CreateIndex("dbo.Players", "User_Id");
            AddForeignKey("dbo.Players", "User_Id", "dbo.AspNetUsers", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Players", "User_Id", "dbo.AspNetUsers");
            DropIndex("dbo.Players", new[] { "User_Id" });
            AlterColumn("dbo.Players", "User_Id", c => c.String(nullable: false, maxLength: 128));
            CreateIndex("dbo.Players", "User_Id");
            AddForeignKey("dbo.Players", "User_Id", "dbo.AspNetUsers", "Id", cascadeDelete: true);
        }
    }
}
