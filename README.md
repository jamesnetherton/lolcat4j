# lolcat4j

[![CircleCI](https://img.shields.io/circleci/project/jamesnetherton/lolcat4j/master.svg)](https://circleci.com/gh/jamesnetherton/lolcat4j/tree/master)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.jamesnetherton/lolcat4j.svg?maxAge=600)](http://search.maven.org/#search%7Cga%7C1%7Clolcat4j)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=600)](https://opensource.org/licenses/MIT)

Java port of the [lolcat Ruby Gem](https://github.com/busyloop/lolcat).

Requires Java 7 or later.

## Building with Maven

Clone this repository and run:

```bash
mvn clean package
```

## Running

```bash
java -jar lolcat4j-0.1.0.jar [OPTION...] [FILE]...
```

## Use in your application

Add the following Maven dependency to your pom.xml:

```xml
<dependency>
  <groupId>com.github.jamesnetherton</groupId>
  <artifactId>lolcat4j</artifactId>
  <version>0.2.0</version>
</dependency>
```

Then use the builder class:

```java
Lol lol = Lol.builder()
    .seed(1)
    .frequency(3.0)
    .spread(3.0)
    .text("Hello World!")
    .file(new File("my-file.txt"))
    .build();
lol.cat();
```

For animation, add a call to `animate()`:

```java
Lol lol = Lol.builder()
    ...
    .animate();
lol.cat();
```

To use all of the default values:

```java
Lol lol = Lol.builder();
lol.cat();
```

## Options

| Option  | Description  | Type  | Default Value  |
|---|---|---|---|
| -a, --animate  | Enable psychedelics | Boolean | false |
| -d, --duration  | Animation duration  | Integer | 12 |
| -F, --freq  | Rainbow frequency | Double | 0.1 |
| -h, --help  | Show usage message |   |   |
| -S, --seed  | Rainbow seed, 0 = random | Integer | 0 |
| -s, --speed  | Animation speed | Double | 20.0 |
| -p, --spread  | Rainbow spread | Double | 3.0 |
| -v, --version  | Print version and exit |   |   |
