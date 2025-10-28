# Assumptions made for this coding challenge.

- If a user tries to send an "alias" to the api on route "/api/v1/url-shortener/{alias}" which is not there I will do nothing but return back Alias not found.
=> Its assumed a user will always add the alias before wanting to get that redirect.

- Its assumed that every record of alias, full-url and short-url will be complete.
=> Therefore if we get a record matching a given alias a full-url and short-url will be gettable for that record.

- Its assumed that the shortUrl generated will always be "http://localhost:8080/{{alias}}, the fullUrl can be any url to redirect to.
=> Therefore its always within the scope of the backend on port 8080.

- Assuming that the fact "full-url" has https means that this can be any outside url including those implementing https.

- Assuming delete will only ever return a 204 if successfull, and therefore only catching this with conditional if in Angular is suitable.

- Assuming that a user will always have to provide a custom-alias and fullUrl, as this is what my frontend component enforces.

- Code coverage would be nice to get to the maven expected value of 70% however this project is time constrained so just testing the crucial components for now.

I'm going to treat the feature expectations and requirements as their own iterations by feature branch with the following identifiers for ease of use and clarification.
Usually I'd expect my feature requirements and expectations for a given sprint to come from a project management tool such as JIRA, and this document is intended to mimic this issue ticket style.

## Issue Tracker of Feature branches:
The numbering is slightly arbitrary it just depends on what issues had come before, I've selected 001 onwards for documentation and additional (docker requirements),100 for backend and 200 for frontend.

### FT-100-java-spring-initial-setup - merged 🚀
As a java developer I want to have a basic program written in Java Spring and tested with JUnit for MVC usage, compiled and ready for coming iterations.
Acceptance criteria: Java program compiles without errors and tests pass for basic skeleton scenarios such as hello world.

### FT-101-openapi-boilerplate-code - merged 🚀
As a java developer I want to create boilerplated code to mimic the sorts of logic which will be required.
This will enable future iterations to use this logic and provides a good template for another developer to work on.
Acceptance criteria: Boilerplated code with logic for Controller methods matching their openapi specifications, parameter testing is required for basic openapi related specifications but not the inner service logic (e.g. The method returns 200 and "OK").

### BUG-100-api-context-path-fix 
As a java tester I want to be able to avoid needing to provide the full URL path, this should be inherited by the test classes from application.properties/application-test.properties file.
Acceptance criteria: The context should be passed correctly with "server.servlet.context-path" for controllers and their tests.
e.g. `mockMvc.perform(get("/health")` should work and not require additional "/api/v1/url-shortener/" path to be added in to the mockmvc test.

### FT-200-angular-initial-setup - merged 🚀
As an angular developer I want to have a frontend UI which will complement the backend API.
This will include Karma as the testing suite and will add simple components to prove the angular frontend is working.
Acceptance criteria: Angular serves html content to the localhost domain and basic karma tests are passing.

### FT-201-angular-services-and-api-integration
As a developer of the url-shortener site I want to be able to see the outputs coming from the backend UI.
Acceptance criteria: Designed and implemented a service to prove that backend interactions are being sent back to UI as expected.

### BUG-101-spring-ldap-security-breaking-tests
As a tester I want to be able to run my controller tests (`@webmvctest`) without needing to deal with issues related to CORS.
Currently these controller tests are having to exclude "SecurityAutoConfiguration.class" from building its custom Beans which are conflicting with tests and expecting authorised logins etc.
Acceptance criteria: Tests pass without the need to exclude SecurityAutoConfiguration.class, it may be the case this is already suitable so further investigation is required.

### FT-001-document-build-instructions - merged 🚀
As a developer I want to be able to build and run the code (frontend and backend java spring api) locally.
Acceptance criteria: readme files are created detailing approach for building and running the code.

### Release-basic-angular-and-api-integration - released as 0.0.1 🚀
Note: I also developed a simple Github Actions pipeline when looking into why eslint was not working.

As a developer I want to have a branch which will contain release staged code.
Acceptance criteria: Github action of CI/CD passing for this Release branch when merged into dev.
This release should be a basic implementation of frontend and backend and is to prove that the Release functionality is working as intended 
e.g. lint, build, test and then build of artifacts end to end.
Note: This will be release 0.0.1

### FT-103-checkstyle-fix-lints-checkstyle-9.3-ruleset
As a developer I want to have a clean run of CI/CD for lints.
Currently using `mvn checkstyle:check`, which does not break build if Checkstyle is not satisfied, this will need updating eventually.
Acceptance criteria: Checkstyle passes with very minimal errors for Checkstyle 9.3
Priority: lower priority over the functionality of the task.

### Release-persisted-url-shortener-records - released as 0.0.2 🚀
As a developer I want to have a persisted store to save records into, this will also encompass changes required to bring in:
Repositories, Services and DataSource to set up the db and provide required methods to interact using SQL.
Acceptance criteria: Release provides functionality to persist data across restarts, this is visualised on the UI as a list of those persisted data and updates on changes.
Note: This will be release 0.0.2

### FT-104-alias-with-url-mapping-repository - merged 🚀
As a java developer I want to have a JPA respository to save my objects into, ready for integration into a persistant store.
Acceptance criteria: working repository with basic tests with JPA with basic accessor methods added.

### FT-105-backend-changes-implementing-api - merged 🚀
As a java developer I want to have full implementations completed for all endpoints in the openapi spec ready for frontend visualisations.
Acceptance criteria: All endpoints within openapi.yml are successfully implemented.

### FT-202-frontend-ui-visualisations-for-api - merged 🚀
As an angular developer I want to provide a simple UI for visualising outputs from backend endpoints within the openapi spec.
Acceptance criteria: Services set up for endpoints which have been implemented with simple UI view to complement the returns of data.

### FT-104-backend-api-changes-to-match-frontend-expectations - merged 🚀
As a java developer I want to provide my controller endpoints in a way which suits the frontend UI implementation.
Acceptance criteria: FT-202-frontend-ui-visualisations-for-api works alongside backend endpoints with returns being given as expected.

### FT-002-containerised-app-artifacts
As a developer of the app I want to containerise my application code, so that it can used as a container within Docker for frontend and backend.
Acceptance criteria: Dockerfiles created to build code for frontend and backend and if possible integrate this with CICD to build docker images to registry.

### FT-003-code-coverage-testing-improvements
As a tester of the app I want to have code coverage up to reasonable levels for both frontend and backend.
Acceptance criteria: Sensible code coverage percentage achieved with functionality tested where suitable.

## Release-more-advanced-api-features-added 0.0.3
As a developer I want to have more production ready features including endpoint exposing for debugging containers and other more advanced features.
Acceptance criteria: Additions for the backend API to add endpoints exposed by spring and more advanced testing features for containerisation purpose.

## FT-108-random-alias-if-none-provided
As a java developer I want to provide a more custom implementation of /shorten method, this will save a random alias if none is provided to the API
Acceptance criteria: User can provide no value for alias in their request and backend will take this to create a record for its own "generated alias".