# GOMOKU

Gomoku game for two players.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

You will need JDK 8 or higher. For that install SDKMAN ( if not installed ) by visiting the site:

```
http://sdkman.io/install.html
```

After the installation you will need to need to download and set the JDK

```
sdk install java 8u161-oracle
```

Clone the project

```
git clone https://github.com/devjbence/Progtech.git

```
Go to it's directory
```
cd Progtech/Gomoku
```

### Installing

Install the ojdbc6:

```
mvn install:install-file -Dfile=src/main/resources/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar
```

Install the package to the local repo:

```
mvn install
```

## Running the tests

You can now run the game.

### Test the game

```
mvn exec:java
```

## Deployment

Deploying is the same as testing:

```
mvn exec:java
```

## Built With
* [ Java JPA ] (http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)
* [ JAVA FX ] (https://docs.oracle.com/javafx/2/overview/jfxpub-overview.htm)
* [ Maven ](https://maven.apache.org/) - Dependency Management
* [ Hibernate ] (http://http://hibernate.org)
* [ GSON ] (https://github.com/google/gson)
* [ Oracle Database Express Edition 11g ] (http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html)


## Authors

 ** Bence Jurás ** 

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.txt](LICENSE.txt) file for details

## Acknowledgments

* Tibor Balla
* Péter Jeszenszky
* Udemy.com

