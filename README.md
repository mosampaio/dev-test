Java Developer Test
===================

# How to generate the fat jar

```bash
./gradlew shadowJar
```

# How to run it

```bash
java -jar GoEuroTest.jar "CITY_NAME"
```

After it runs, it should print the generated file path.

# Custom

### How to change the host
You have to define the GO_EURO_HOST environment variable:
```bash
export GO_EURO_HOST=http://api.goeuro.com
```
If this variable is not defined, the default value is `http://api.goeuro.com`.

### How to change the output directory
You have to define the OUTPUT_DIRECTORY environment variable:
```bash
export OUTPUT_DIRECTORY=/tmp/
```
If this variable is not defined, the default value is the OS temporary directory.
