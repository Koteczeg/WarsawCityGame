# Warsaw City Game
Warsaw City Game is a simple traveler application for devices with Android operating system. Developed for a Mobile applications: Android course at our university.

The system consists of two modules:
* Client application (Java SE & android libraries)
* RESTful API service providing common interface for Client application (ASP.NET Web API)

## Motivation
The main purpose was to get familiar with programming for mobile devices and also develop an application which would stand out from the crowd of other traveler applications. It is not an ordinary traveler; the product is designed as an action game; users compete ranking by completing some quests which are connected with visiting some places in Warsaw.

## Installation
To run Warsaw City Game you will need to set up the API service and database. The fastest way is to deploy both of it via web services, for ex. Microsoft Azure or Amazon provide it.
In that case you should update the connection string in `Web.config` for WarsawCityGame.Services project and `App.config`  for WarsawCityGame.Entities. You will need to update API address in client application too (in WarsawCityGame.App module). To initialize the database and seed it with the test data run the following command in the Package Manager Console:
```
Update-Database -ProjectName WarsawCityGame.Entities
```
## License
MIT License
