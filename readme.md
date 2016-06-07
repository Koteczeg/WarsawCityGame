# Warsaw City Game
Warsaw City Game is a simple traveler application for devices with Android operating system. Developed for a Mobile applications: Android course at our university.

The system consists of two modules:
* Client application
* RESTful API service providing common interface for Client application

## Motivation
The main purpose was to get familiar with programming for mobile devices and also develop an application which would stand out from the crowd of other traveler applications.

## Installation
To run Warsaw Sleep Time you will need to set up the API service and database. The fastest way is to deploy both of it via web services, for ex. Microsoft Azure or Amazon provide it.
In that case you should update the connection string in `Web.config` for WarsawCityGame.Services project and `App.config`  for WarsawCityGame.Entities. You will need to update the API address in client application too (WarsawCityGame.App package, Application util) To initialize the database and seed it with the test data run the following command in the Package Manager Console:
```
Update-Database -ProjectName WarsawCityGame.Entities
```
## License
MIT License
