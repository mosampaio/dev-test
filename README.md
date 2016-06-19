Java Developer Test
===================

[![Build Status](https://travis-ci.org/TwilioDevEd/sms2fa-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/sms2fa-servlets)

## Requirements

[Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)

## How to run the tests

```bash
./gradlew test
```

## How to generate the fat jar

```bash
./gradlew shadowJar
```

It generates the it under `{project_path}/build/libs/`.

## How to run the fat jar

```bash
java -jar GoEuroTest.jar "CITY_NAME"
```

It should print the generated file path.

### FAQ

#### How to change the host?
You can to define the GO_EURO_HOST environment variable:
```bash
export GO_EURO_HOST=http://api.goeuro.com
```
If this variable is not defined, the default value is `http://api.goeuro.com`.

#### How to change the output directory?
You can to define the OUTPUT_DIRECTORY environment variable:
```bash
export OUTPUT_DIRECTORY=/tmp/
```
If this variable is not defined, the default value is the OS temporary directory.
