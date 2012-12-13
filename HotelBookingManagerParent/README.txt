
***********************************
HOTEL BOOKING MANAGER v1.0-SNAPSHOT
***********************************
Authors: Marián Rusnák, Thanh Dang Hoang Minh, Filip Bogyai, Andrej Galád

Project Hotel Booking Manager simulates an ordinary online hotel booking service.
Provides managing, hotels, rooms in hotels, clients and their reservations. 
Project includes persistence layer, service layer, web presentation layer with REST API
and desktop client that communicates with that REST API.

Project contains 4 modules:
 - backend API
 - backend implementation
 - web application
 - desktop REST client


HOW TO RUN
----------

Web application:
It is configured with tomcat maven plugin.
1) Connect to derby database, on localhost, port 1527, with username and password pa165.
2) In command line locate the web folder.
3) Type mvn tomcat:run or mvn tomcat7:run (two versions are configured).
4) In browser go to address: http://localhost:8080/pa165/.

Desktop client
It is configured with exec maven plugin.
1) Run web application following steps above (if not, desktop client will close 
   because of no server connection).
2) In command line locate the desktop client folder.
3) Type mvn exec:java.