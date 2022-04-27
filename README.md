#  Getir Case Study

## About
This project is prepared for Getir. During the project, a structure where users buy books was modeled.

## Requirements and Tech Stack

Docker, Java 11, Spring, Spring Boot, Hibernate, JUnit for development and run.

## API Details
Project has 4 controller and there are Customer, Book, Order and Statistics Controller.

## Postman Collection
You can use this link to copy collections of API's -> https://www.getpostman.com/collections/611fc1963d6fb31a8d04

## Testing Application

This application has a swagger implementation for testing.
Just go to [Swagger Link](http://localhost:8080/swagger-ui/index.html#/) after run the app. 


## Download and Running Application

You can download this project with two way:
1) Click code and copy link then paste terminal  git clone https://github.com/aslanFatih34/ReadingIsGood.git

2) Click code and download as JAR.
Note: Practical way to start this app is right click the main class and run as java application. If you want to make executable jar please follow the steps below.

After get project cd to project folder then open terminal here and run the 
```mvn clean package``` 
command. If you are using STS/Eclipse IDE, then Right Click on your project » Run As » Maven build… » Goals: 
```clean package``` 
» Run. This will build our project and running tests after that create an executable JAR file of application and put it within the "targe"t folder.

For run application with jar, run this command in terminal;

```java -jar target/ReadingIsGood-0.0.1-SNAPSHOT.jar```




