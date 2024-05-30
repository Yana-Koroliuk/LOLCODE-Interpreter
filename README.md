# LOLCODE-Interpreter

[![Java CI with Maven](https://github.com/Yana-Koroliuk/LOLCODE-Interpreter/actions/workflows/maven.yml/badge.svg)](https://github.com/Yana-Koroliuk/LOLCODE-Interpreter/actions/workflows/maven.yml)

This project provides a lexer, parser, and interpreter for a simple programming language LOLCODE 1.2. The LOLCODE Interpreter reads and executes LOLCODE scripts by first converting the raw source code into a series of tokens, which represent the basic elements of the language. These tokens are then arranged into an Abstract Syntax Tree (AST) by the parser to represent the grammatical structure of the code. The interpreter traverses the AST to evaluate expressions, execute statements, and manage the program state. During this process, the code is validated for syntax correctness, and any errors are reported to the user. For more detailed information about the application, please refer to the [design document](https://docs.google.com/document/d/1iH2IbCv_gKnikHg7J2Ctt2L9580PfzwSZN1FgVrfxYs/edit?usp=sharing).

## Prerequisites
> **NOTE:** You need to have Docker, Maven, and Java installed on your system to build, run, and test this application. Visit the following links to download and install:
> - [Docker](https://www.docker.com/get-started)
> - [Maven](https://maven.apache.org/install.html)
> - [Java](https://www.java.com/en/download/)

## Installation

1. Clone the repository from GitHub:
    ```bash
    git clone https://github.com/Yana-Koroliuk/LOLCODE-Interpreter.git
    cd LOLCODE-Interpreter
    ```

## Usage

### Building the Docker Image

1. Build the Docker image for the LOLCODE Interpreter:
    ```bash
    docker build -t lolcode-interpreter .
    ```

### Running the LOLCODE Interpreter

2. Run the LOLCODE Interpreter with the Docker image, specifying the path to your LOLCODE file:
    ```bash
    docker run --rm -i -v /path/to/your/test.lol:/app/test.lol lolcode-interpreter /app/test.lol
    ```

## Example LOLCODE Files

In the project, the `src/main/resources` directory contains example LOLCODE files that you can copy and run. The `test.lol` file includes all the basic syntactic constructs implemented in this interpreter. Additionally, there are two example files: `hello_world.lol` and `input_output.lol`. The `hello_world.lol` file demonstrates a simple "Hello, world!" program, and the `input_output.lol` file shows how to handle input and output in LOLCODE. Below are the contents of these two files:

### hello_world.lol
```lolcode
HAI 1.2

VISIBLE "Hello, world!"

KTHXBYE
```

### input_output.lol
```lolcode
HAI 1.2

I HAS A VAR
VISIBLE "Please enter a value: "
GIMMEH VAR
VISIBLE "You entered: " AN VAR

KTHXBYE

```
## Running Example Files

You can run the provided example files like this:

1. To run `hello_world.lol`:
    ```bash
    docker run --rm -i -v $(pwd)/src/main/resources/hello_world.lol:/app/hello_world.lol lolcode-interpreter /app/hello_world.lol
    ```

2. To run `input_output.lol`:
    ```bash
    docker run --rm -i -v $(pwd)/src/main/resources/input_output.lol:/app/input_output.lol lolcode-interpreter /app/input_output.lol
    ```

## Run tests
To execute the tests when you are in the root directory of the repository, enter the following command:
```bash
mvn test
```

## Help

If you have any questions, create an issue and start a discussion
[github](https://github.com/Yana-Koroliuk/LOLCODE-Interpreter/issues).
## License
This project is [GNU General Public](https://www.gnu.org/licenses/gpl-3.0) licensed.
## Contributors
- [Balakhon Mykhailo](https://github.com/mibal-ua)
- [Koroliuk Yana](https://github.com/Yana-Koroliuk)
- [Kovalenko Mykhailo](https://github.com/merrymike-noname)
