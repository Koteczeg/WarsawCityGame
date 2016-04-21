namespace WarsawCityGamesServer.Entities.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Addcontext : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Achievements",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Description = c.String(),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Friendships",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        StartRelationDate = c.DateTime(),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                        Friend_Id = c.Int(),
                        User_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Users", t => t.Friend_Id)
                .ForeignKey("dbo.Users", t => t.User_Id)
                .Index(t => t.Friend_Id)
                .Index(t => t.User_Id);
            
            CreateTable(
                "dbo.Users",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Username = c.String(),
                        Email = c.String(),
                        HashedPassword = c.String(),
                        Salt = c.String(),
                        UserImage = c.Binary(),
                        Exp = c.Int(nullable: false),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                        CurrentMission_Id = c.Int(),
                        Level_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Missions", t => t.CurrentMission_Id)
                .ForeignKey("dbo.Levels", t => t.Level_Id)
                .Index(t => t.CurrentMission_Id)
                .Index(t => t.Level_Id);
            
            CreateTable(
                "dbo.Missions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        Description = c.String(),
                        ExpReward = c.Int(nullable: false),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                        MinimumLevel_Id = c.Int(),
                        Place_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Levels", t => t.MinimumLevel_Id)
                .ForeignKey("dbo.Places", t => t.Place_Id)
                .Index(t => t.MinimumLevel_Id)
                .Index(t => t.Place_Id);
            
            CreateTable(
                "dbo.Levels",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        ExpRequired = c.Int(nullable: false),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Places",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(),
                        XCoordinate = c.Double(nullable: false),
                        YCoordinate = c.Double(nullable: false),
                        Image = c.Binary(),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.MissionHistories",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        FinishDate = c.DateTime(nullable: false),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                        Mission_Id = c.Int(),
                        User_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Missions", t => t.Mission_Id)
                .ForeignKey("dbo.Users", t => t.User_Id)
                .Index(t => t.Mission_Id)
                .Index(t => t.User_Id);
            
            CreateTable(
                "dbo.UserAchievements",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Version = c.Binary(nullable: false, fixedLength: true, timestamp: true, storeType: "rowversion"),
                        Achievement_Id = c.Int(),
                        User_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Achievements", t => t.Achievement_Id)
                .ForeignKey("dbo.Users", t => t.User_Id)
                .Index(t => t.Achievement_Id)
                .Index(t => t.User_Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.UserAchievements", "User_Id", "dbo.Users");
            DropForeignKey("dbo.UserAchievements", "Achievement_Id", "dbo.Achievements");
            DropForeignKey("dbo.MissionHistories", "User_Id", "dbo.Users");
            DropForeignKey("dbo.MissionHistories", "Mission_Id", "dbo.Missions");
            DropForeignKey("dbo.Friendships", "User_Id", "dbo.Users");
            DropForeignKey("dbo.Friendships", "Friend_Id", "dbo.Users");
            DropForeignKey("dbo.Users", "Level_Id", "dbo.Levels");
            DropForeignKey("dbo.Users", "CurrentMission_Id", "dbo.Missions");
            DropForeignKey("dbo.Missions", "Place_Id", "dbo.Places");
            DropForeignKey("dbo.Missions", "MinimumLevel_Id", "dbo.Levels");
            DropIndex("dbo.UserAchievements", new[] { "User_Id" });
            DropIndex("dbo.UserAchievements", new[] { "Achievement_Id" });
            DropIndex("dbo.MissionHistories", new[] { "User_Id" });
            DropIndex("dbo.MissionHistories", new[] { "Mission_Id" });
            DropIndex("dbo.Missions", new[] { "Place_Id" });
            DropIndex("dbo.Missions", new[] { "MinimumLevel_Id" });
            DropIndex("dbo.Users", new[] { "Level_Id" });
            DropIndex("dbo.Users", new[] { "CurrentMission_Id" });
            DropIndex("dbo.Friendships", new[] { "User_Id" });
            DropIndex("dbo.Friendships", new[] { "Friend_Id" });
            DropTable("dbo.UserAchievements");
            DropTable("dbo.MissionHistories");
            DropTable("dbo.Places");
            DropTable("dbo.Levels");
            DropTable("dbo.Missions");
            DropTable("dbo.Users");
            DropTable("dbo.Friendships");
            DropTable("dbo.Achievements");
        }
    }
}
