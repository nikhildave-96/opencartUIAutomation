
# Introduction

This project focuses on testing modern web applications using hybrid framework concept. The framework is built to be generic in terms of application configuration, finding web elements and performing common actions. Nothing needs to be written from scratch as this structure is capable of testing any web application to validate the functionality requirements.

## Pre-requisites
Before starting to work with this project, ensure you have:
 
 - Java Development Kit 
 - Integrated Development Environment like VS Code, IntelliJ, Eclipse (preferable)
 - Git scm version control
 - Maven plugin 'Maven Integration for Eclipse' (often referred to as m2e) installation if required from Eclipse marketplace

## Framework components

 - Java: used as the base programming language
 - Selenium: a suite of tools to automate web application
 - TestNG: establishes the test framework
 - Maven: project management tool 
 - Extent: useful in test reporting
 - Log4j: provides a logging solution
 - Apache POI: helps read test data from MS excel file

## Configuration details

 - pom.xml: helps in configuration, build and dependencies management and contains basic project information 
 - testng.xml - used for organizing and executing test cases
 - log4j2.xml - config file for printing logs either in console or in a external file or both
 - application credentials (oc_username and oc_password) should be configured as environment variables under run configuration's Environment section of the IDE or as system environment variable (restarting IDE needed) to be able to run tests locally. See this for more details https://docs.saucelabs.com/basics/environment-variables/ 

## Maven
In Eclipse IDE, run configurations under Maven Build section can be created for below commands with project root folder as the base directory. (without specifying mvn keyword)

 - mvn clean -> Cleans the project and removes all files generated by the previous build.
 - mvn clean install -> delete the target folder, compile the project, run tests, package it, and add it to the local repository
 - mvn compile -> Compiles source code of the project.
 - mvn package -> Creates JAR or WAR file for the project to convert it into a distributable format.
 - mvn test -> Runs tests for the project.
 - mvn test-compile -> Compiles the test source code.
 - mvn clean test -> Removes all previously generated files and run the tests.
 - mvn test -Dtest=TestClassName#TestCaseName -> Runs only the specified test.
 - mvn test -Dtest=TestClassName -> Runs testcases only from the specified test class.

Ensure build success to be displayed in the console/logs upon running any of the mvn commands or the configured maven jobs.

## Running tests locally

 - 
