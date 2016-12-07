# Deeta

[![Build Status](https://travis-ci.org/rabitarochan/deeta.svg?branch=master)](https://travis-ci.org/rabitarochan/deeta)
[![Coverage Status](https://img.shields.io/codecov/c/github/rabitarochan/deeta.svg)](https://codecov.io/gh/rabitarochan/deeta)

Fully customizable data generator tool.

# Description

`Deeta` provides macros for generating data.

```java
Deeta deeta = new Deeta();

deeta.resolve("const value") // => "const value"

deeta.resolve("###") // => random number between "000" and "999"

deeta.resolve("#####-####") // => random number like zip code.

deeta.resolve("${uuid}") // => get random uuid string
```

Data file can be defined in YAML.

```yaml
deeta:
  address:
    zipCode: "#####-####"
    # In the list, one is chosen randomly.
    state: ["Alabama", "Alaska", "Arizona", "Arkansas", ... ]
    japaneseMobilePhone:
      - "090-####-####"
      - "080-####-####"
      - "070-####-####"
```

# Getting started

Gradle:

```groovy
dependencies {
    compile 'com.github.rabitarochan:deeta-api:0.1.0-SNAPSHOT'
}
```

Maven:

```xml
<dependency>
    <groupId>com.github.rabitarochan</groupId>
    <artifactId>deeta-api</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

# Syntax

`Deeta` provides the following syntax by default.

## Numeric conversion

Randomly convert `#` to numbers from `0` to `9`.

```text
"###" => "000" to "999"

"###-####" -> "000-0000" to "999-9999"

"No.#" -> "No.0" to "No.9"
```

When escaping with `\`, `#` is output as it is.

```text
"\###" => "#00" to "#99"
```

## Expand variables

Expand the variable according to the rule `${variable.name}`.
Variable names are connected by dots.

### Rule 1 : `${alias}` (Variable without dot)

The variable name without dot is `alias`.
If an alias is set for the generator, it will be used to generate the data.

### Rule 2 : `${dataSourceName.generatorName}` (Variable including one dot)

Variables containing one dot are expanded to the generator.
If the generator name ends with `Generator`, you can omit it.

Example: `deeta.uuidgenerator` can also be written as` deeta.uuid`.

### Rule 3 : `${dataSourceName.groupName.dataName}` (Variable including two dots)

Variables containing two dots get data from a data file.

`Deeta` now supports YAML format data files.

# TODO

- Character conversion
- Customize documents
- CLI application
- How to define data file

# Inspired

`Deeta` was inspired by a data generation library named `Faker` that is provided for each programming languages.

# License

The MIT License

Copyright (c) 2016 Kengo Asamizu (rabitarochan)
