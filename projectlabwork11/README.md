# My Maven Project

This is a simple Maven project that demonstrates the basic structure and functionality of a Java application.

## Project Structure

```
my-maven-project
├── src
│   ├── main
│   │   ├── java
│   │   │   └── App.java         # Main application class
│   │   └── resources            # Resources for the application
│   └── test
│       ├── java
│       │   └── AppTest.java     # Test class for App
│       └── resources            # Resources for tests
├── pom.xml                      # Maven configuration file
└── README.md                    # Project documentation
```

## Getting Started

To build and run the project, ensure you have Maven installed on your machine. You can then use the following commands:

1. **Build the project:**
   ```
   mvn clean install
   ```

2. **Run the application:**
   ```
   mvn exec:java -Dexec.mainClass="App"
   ```

## Running Tests

To run the tests, use the following command:

```
mvn test
```

## License

This project is licensed under the MIT License.