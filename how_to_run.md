# Running the app in vscode

## Prerequisites:
- Java installed and configured: check `java -version`, at time of writing "2025-09-16 LTS".
- Maven installed `mvn -version`, current is 3.9.11
- Vscode setup with required plugins such as "Spring Boot Extension Pack" and "Language Support for Java(TM) by Red Hat"

## Compiling maven
1) Run `mvn clean` to remove the old compiled files and temporary build from previous runs
This includes:
   - Compiled .class files
   - Packaged .jar or .war files
   - Temporary files in the target/ directory

Now you can run `mvn install` which will compiles java files into class files and runs tests (important so we can see if the build has any breaking changes)
Finally it bundles source code into a .jar or .war file.

Note: You can just combine both commands e.g. run `mvn clean install`, I usually do this as it ensures we have that fresh slate ready for testing (not using outdated or leftover files).
You can also skip tests with `mvn install -DskipTests` if required.

2) Now you can clean the java workspace in vscode by using CTRL+SHIFT+P and running the java plugin.

## Linting your code
3) Code can be linted with `mvn checkstyle:checkstyle` to get a report of lints.
Then to test those are fixed you can use `mvn checkstyle:check` to fail build if violations are found.

## Running the code
You can run the code in standard mode or debug, the prerequisite is that you have succesfully compiled the code.

4)  First ensure you are still in the root of the project.
    Once the code is built as per steps 1&2, you can then also serve directly from the vscode "run" on the AppMain.main() method.
    - Additionally you could use `mvn spring-boot:run` to use the built in spring mvn command which will do also do compilation pre-checks.

## Testing
5) Tests can be ran with either mvn or vscode
   - For mvn theres a host of options including:
      - Run all unit tests: `mvn test`
      - Run specific unit test matching ClassName: `mvn -Dtest=ClassName test`
   These options are great for developing with CI/CD and either need to test a specific use case or run the whole suite to check coverage.

   - With vscode, which is usually my goto I would use the run test play button:
      - Run all tests in that class: by clicking the play button at the very top of the test suite
      - Or individual tests: clicking the button on the test I am looking at running/debugging.

5b) Checking code coverage can be done with `mvn clean test` which will generate coverage report in: `target/site/jacoco/index.html`
This can be enforced by running `mvn clean verify` which runs tests and checks code coverage percentages match expectations.

## Building the artifact
6) Run `mvn package`
   This will build a .jar/.war to your specifications as defined in the pom.xml (e.g. version and name etc)

## Building as a Dockerfile
7) Change directory to location of the Dockerfile and run: `docker build -t url-shortener:{{insert_version}} .`
   This will build the image locally using the layers specified in your dockerfile. (e.g. layers for file changes, layers for artifact placement in folder structure)

## Running the Dockerfile
8) `docker run -p 8080:8080 --name=url-shortener-v{{insert_version}} url-shortener:{{insert_version}}`
   This will run the container on port 8080 named url-shortener