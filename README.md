Coding challenge.

On this file will explain the way to build/run/use the microservice application.

There's two ways to build/run/use the app:
	1. Using the dockerfile: For this, we need have docker installed. First, we need change the application properties from
							gateway, auth, user, account and transacion service. Open the file application.properties and change this property: eureka.client.service-url.defaultZone and put this new value eureka.client.service-url.defaultZone=http://eureka-server:8761/eureka

							Then, we need to generate the .jar files for every microservice, for this we open the CMD in each microservice root folder and execute the following command: mvnw clean package.

							When every .jar file have been generated, then we locate the CMD in the docker-compose folder, and execute the following command: docker-compose build. This will create each image of the microservices.	

							When this is done, then we execute docker-compose up. This will create and run each image within a docker container. 						

	2. Doing it manually: This is how is configured by default in the repository. We should compile every microservice and execute it in the following order: eureka - gateway - auth - user - account - transaction.

In the browser, also we can search by http://localhost:8761, and this will show us the discovery service created.

Technologies used: 
	- Spring-eureka-client: This library allows create a client to connect with the discovery service.
	- Spring-eureka-server: This library allows create a discovery server.
	- Spring Cloud Gateway: Makes it easy to build a broker by providing a simple yet effective way to route to APIs
        - H2: Implemented as an embedded database in the projects where it corresponds.
        - JPA: Responsible for data persistence.
        - Spring security: Facilitating user authentication and authorization security configuration.
        - JWT: JSON web token creates tokens that are validated to know the identity and privileges of users.
	- Springdoc: Automating API documentation.
        - Docker: Facilitates by automating the deployment of APIs within containers and in turn within a network in this case.

NOTE 1: A .postman_collection.json file is delivered with the configuration for use of the different services.

	Run order services: 
		- AUTH -> create : Register the user
		- AUTH -> login : Gets the token according to the user information
		- Update the token for each service.
		- Use the services that are in USER.

NOTE 2: The auth, user, account and transaction projects have files that are executed on startup and that create a schema and/or record initial information.

	If you want to cut the path run: 
		- AUTH -> create : Register the user
		- AUTH -> login : Gets the token according to the user information
		- Update the token for each service.
		- USER -> SAVETRANSACTION.
		- See updated information with GET services.