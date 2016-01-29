# lolcat4j

Java port of the [lolcat Ruby Gem](https://github.com/busyloop/lolcat).

# Building with Maven

Clone this repository and run:

```bash
mvn clean package
```

# Running

```bash
java -jar lolcat4j-0.1.0.jar [OPTION...] [FILE]...
```

# Use in your application

Add the following Maven dependency to your pom.xml:

```xml
<dependency>
  <groupId>com.github.jamesnetherton</groupId>
  <artifactId>lolcat4j</artifactId>
  <version>0.1.0</version>
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

# Options

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
