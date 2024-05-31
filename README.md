# FotoAnalyzer-Payara-Micro

* der "Starter" auf der Payara-Seite erzeugt für Gradle ein falsches und unvollständiges `build.gradle`.
* Payara verwendet zum Testen Arquillian, was scheinbar immer noch nicht mit JUnit 5 umgehen kann (es existiert keine `ArquillianExtension`).
* ein möglicher Ausweg ist die Nutzung von `Testcontainers` (siehe https://blog.payara.fish/jakarta-ee-integration-testing-with-testcontainers). Das ist als Nächstes zu untersuchen.

# Payara Starter Project

This is a sample application generated by the Payara Starter.

## Getting Started

### Prerequisites

- [Java SE 21+](https://adoptium.net/?variant=openjdk21)
- [Gradle](https://gradle.org/install/)

## Running the Application

To run the application locally, follow these steps:

1. Open a terminal and navigate to the project's root directory.

2. Make sure you have the appropriate Java version installed. We have tested with Java SE 8, Java SE 11, Java SE 17, and Java SE 21.

3. Execute the following command:

```
./gradle build microStart
```

4. Once the runtime starts, you can access the project at http://localhost:8080/

## Building a Docker Image
To build a Docker image for this application follow these steps:

Open a terminal and navigate to the project's root directory. Make sure you have Docker installed and running on your system.
Execute the following Gradle command to build the Docker image:

```
gradle buildDockerImage
```

This command will build a Docker image for your application.

Once the image is built, you can run a Docker container from the image using the following command:

```
gradle startDockerContainer
```

That's it! You have successfully built and run the application in a Docker container.



