# <p style='color:blue'>**Challenge Solution**</p>

The solution consists of an API capable of transferring funds between two accounts, taking into account certain business rules.

## <p style='color:blue'>**Implementación**</p>

The API is developed with a microservices architecture with the following technologies.

- **Spring-eureka-client** - This library allows you to create a client to connect to the discovery service.
- **Spring-eureka-server** - This library allows you to create a discovery server.
- **Spring Cloud Gateway** - It makes it easy to create a gateway that provides a simple yet effective way to route to microservices.
- **H2** - Implemented as an embedded database in the projects where it corresponds.
- **JPA** - Responsible for data persistence.
- **Spring security** - Facilitate user authentication and authorization security settings.
- **JWT** - JSON Web Token creates tokens that are validated for user identity and privileges.
- **Swagger** - API documentation automation.
- **Docker** - It makes it easier to automate the deployment of the API within containers and in turn within a network in this case.

In this case there are 6 microservices which are detailed below.

- **eureka-service** - Discovery server responsible for registering and locating the different microservices.
- **gateway-service** - Provides an entry point for interacting with microservices.
- **auth-service** - Responsible for providing security to consumption.
- **user-service** - Abstraction of a user that has accounts and can perform transactions.
- **account-service** - Responsible for managing the functionalities of the accounts.
- **transaction-service** - Responsible for managing the functionalities of the transactions.

In each microservice you can find different packages, which have the *com.test* convention. Listed below are those that can be found.

- **(microservicename)service** : The name corresponds to the microservice to which it belongs followed by the word *service*. Example: *eurekaservice*, *gatewayservice*, etc. This package is the main package and is where the startup class can be found.
- **config** : Contains the corresponding configuration classes for each microservice.
- **constant** : Contains the classes with the constant values for each microservice.
- **controller** : Contains the controller classes, where the requests arrive and from where they are delegated.
- **dto** : Contains the classes used to secure the transfer of information.
- **entity** : Contains the entity classes that represent a table in a database and that are made up of persistence fields corresponding to the columns of the corresponding table.
- **exception** : Contains the class that handles the exceptions that occur during execution time and have to do with business rules and classes with the different types of error according to the business rules.
- **repository** : Contains the repository classes in charge of managing the persistence operations with a database table.
- **service** : Contains the classes that provide security to the API.
- **service** : Contains the interfaces that specify the services with which the user can interact through the controller.
- **service.impl** : Contains the classes that implement the services with which the user can interact through the controller.
- **util** : Contains the classes that provided usefulness or facilities..


## <p style='color:blue'>**Business Modeling**</p>

Below you can see sequence diagrams of the three most important transactions developed (Authentication, Save Account, Sava Transaction). In these cases, it is due to the fact that each request complies with the business rules.

### Authentication

![Image Text](https://raw.githubusercontent.com/JJGA123/IMAGES/main/AUTHENTICATION.png)

### Create Account

![Image Text](https://github.com/JJGA123/IMAGES/blob/main/CREATEACCOUNT.png)

### Send Transaction

![Image Text](https://github.com/JJGA123/IMAGES/blob/main/SENDTRANSACTION.png)

## <p style='color:blue'>**Build/Run/Use**</p>

This part will explain how to compile/run/use the API.

There are two ways to build/run/use the app:

- ####Using the docker file: 

For this, you need to have docker installed. Change the properties of the application
gateway, auth, user, account and transaction. Open the application.properties file and change the value of the property: *eureka.client.service-url.defaultZone* and put this new value *http://eureka-service:8761/eureka*.

Generate the .jar files for each microservice by opening CMD in the root folder of each microservice and executing the *mvnw clean package* command.

When each .jar file has been generated, you need to locate the CMD in the docker-compose folder and run the *docker-compose build* command. This will create each microservices image.

Finally run *docker-compose up*. This will build and run each image inside a docker container.

- ####Manually:

Each microservice must be compiled and run in the following order: eureka - gateway - auth - user - account - transaction.

In the browser we can also search for *http://localhost:8761*, this will show the discovery service created with the clients that have registered.

NOTE 1: For the use of the different services, a .postman_collection.json file is delivered.

Run as follows:
>1. AUTH -> create: Register the user.
>2. AUTH -> login: get the token according to the user information.
>3. Update the token for each service.
>4. Use the services found in USER.


NOTE 2: The auth, user, account and transaction projects have files that are executed at startup and that create a schema and/or record initial information.

If you want to cut the path, execute as follows:
>- AUTH -> create: Register the user
>- AUTH -> login: get the token according to the user information
>- Update the token for each service.
>- USER -> saveTransaction: Execute the transaction.
>- USER -> View updated information with GET services.

## <p style='color:blue'>**Test**</p>

The unit tests are located in the *\src\test\java\com\test\microservice* folder corresponding to the project, in this case the tests were executed for the service component.