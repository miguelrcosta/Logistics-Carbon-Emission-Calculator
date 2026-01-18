# Logistics Carbon Emission Calculator

Java application developed in an academic context for the **Software Engineering II** course.

This project focuses on the **calculation and analysis of carbon emissions in logistics transport chains**, supporting sustainability assessment and decision-making in freight transportation.

The application applies established **software engineering practices**, including modular design, automated testing, code quality analysis and agile development methodologies.

---

## Project Overview

The main objective of this project is to model and evaluate the **environmental impact of logistics transport chains**, from origin to destination.

The system allows the definition of transport chains composed of multiple elements, such as transport operations and hub operations, and calculates emissions using internationally recognized metrics.

Key goals of the project include:
- Accurate carbon emission calculation
- Code quality and maintainability
- Testability and reliability
- Application of software engineering best practices

---

## Core Features

- Modeling of logistics transport chains
- Support for transport and hub operations
- Carbon emission calculation:
  - Tank-To-Wheel (TTW)
  - Well-To-Tank (WTT)
  - Well-To-Wheel (WTW)
- Emission intensity analysis
- Import of data from CSV files
- Support for primary and secondary data sources
- Filtering and analysis of emission results

---

## Project Structure

The project follows a modular architecture, separating responsibilities across different layers:

- **Model**
  - Transport chain entities
  - Transport and hub operations
  - Emission factors

- **Service / Business Logic**
  - Emission calculation logic
  - Data processing rules

- **Utils**
  - CSV parsing
  - Auxiliary calculations

- **Tests**
  - Unit and integration tests

- **Build & Quality**
  - Automated builds
  - Code coverage and static analysis tools

---

## Software Engineering Practices

This project was developed applying the following practices:

- Agile development using **SCRUM**
- Test-Driven Development (TDD)
- Continuous Integration (CI)
- Static code analysis
- Code coverage measurement
- Version control and configuration management

---

## How to Run the Project

### Requirements

- Java JDK
- Gradle

### Build the project

```bash
gradle build
```

### Run Tests

```bash
gradle test
```

---

## Technologies Used

- Java
- Gradle
- JUnit 5
- JaCoCo (code coverage)
- PMD (static code analysis)
- CSV (data processing)
- Git

---

## Notes

This project was developed strictly for academic purposes.
The main focus is on software engineering principles, code quality and testing rather than production deployment.
