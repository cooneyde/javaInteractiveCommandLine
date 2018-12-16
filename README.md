# javaInteractiveCommandLine

[![Coverage Status](https://coveralls.io/repos/github/cooneyde/javaInteractiveCommandLine/badge.svg?branch=master)](https://coveralls.io/github/cooneyde/javaInteractiveCommandLine?branch=master)
[![Build Status](https://travis-ci.org/cooneyde/javaInteractiveCommandLine.svg?branch=master)](https://travis-ci.org/cooneyde/javaInteractiveCommandLine)


##Dependencies for building the application locally

1. Maven https://maven.apache.org/download.cgi
1. Java 8
1. MySQL version 8.0.12 https://www.mysql.com/downloads/


###Building and running with maven

`mvn clean compile`

`mvn exec:java -Dexec.mainClass="com.example.Main"`

### Running the unit tests

`mvn clean test`

### Building and running the java jar

`mvn clean package`

`java -jar javaApp-1.0-jar-with-dependencies.jar`