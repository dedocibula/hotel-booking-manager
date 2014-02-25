Hotel Booking Manager
=====================

Project Hotel Booking Manager simulates an ordinary online hotel booking service.
Provides managing, hotels, rooms in hotels, clients and their reservations. 
Project includes persistence layer, service layer, web presentation layer with REST API
and desktop client that communicates with that REST API.

Project contains 4 modules:
 - backend API
 - backend implementation
 - web application
 - desktop REST client

Web application
---------------
Web application is deployed on Red Hat's public cloud application development and 
hosting platform - OpenShift Online - with the following url:

http://hotelbookingmanager-javarockstars.rhcloud.com

It uses Tomcat 7 (JBoss EWS 2.0) and MySQL 5.5 cartridges.

Desktop client:
---------------
Desktop client is configured with exec maven plugin and can be run from command
line.

1. Locate the desktop client folder in your filesystem.
2. Type mvn exec:java.
