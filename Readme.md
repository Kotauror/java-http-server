# Java HTTP Server

This is an HTTP server written in Java within three weeks during my apprenticeship at 8th Light.

The server passes all tests from [Cob Spec](https://github.com/8thlight/cob_spec) - an 8th Light suite of tests used to validate a web server to ensure it adheres to HTTP specifications.

## Requirements

Both this server and Cob Spec require Java 8 and Maven.

## Running the server

1. Clone the Cob Spec repo. It will be needed to run the test suite and serve as a root directory with sample files: `$ git clone https://github.com/8thlight/cob_spec.git`

2. Clone this server's repo: `$ git clone https://github.com/Kotauror/java-http-server`

3. Enter the server's repo: `$ cd java-http-server`

4. Go to the location of JAR file: `$ cd /out/artifacts/java_http_server_jar`

5. Execute the JAR file to run the server on the local machine. In the code below:
- replace `5000` with the port on which you want to run it;
- replace `/Users/justynazygmunt/Desktop/cob_spec/public` with a path to the public directory in the Cob Spec repo cloned in the first step.

`$ java -jar java-http-server.jar -p 5000 -d /Users/justynazygmunt/Desktop/cob_spec/public`

This server should now be running - you should see `I'm listening for connections` in the console.

You can go to `http://localhost:port/` (replace `port` with a port number set in step 5) public directory of files and make HTTP requests.

## Running the Cob Spec tests

1. Enter into Cob Spec directory (cloned in step 1 of the section above): `$ cd cob_spec`

2. Build it using maven: `$ mvn package`

3. Run the Cob Spec server: `java -jar fitnesse.jar -p 9090`

Open your browser and go to http://localhost:9090 - you should see Fitnesse website.

4. Configure the test suite. Go to the HttpTestSuite and click 'Edit' at the top. You need to update the 'User-Defined Variables' that should be visible in the text box.

4a. `SERVER_START_COMMAND` is the command to start the server by executing jar, hence it should have a path to the jar file:

`!define SERVER_START_COMMAND {java -jar (path-to-httpServer-root-on-your-machine)/out/artifacts/java_http_server_jar/java-http-server.jar}`

On my machine, this line looks like this:

`!define SERVER_START_COMMAND {java -jar /Users/justynazygmunt/Desktop/java-http-server/out/artifacts/java_http_server_jar/java-http-server.jar}`

4b. `PUBLIC_DIR` defines the place from which Fitnesse gets sample files to run the tests. Change the path as follows:
`!define PUBLIC_DIR {(path-to_Cob_SPec-root)/public}`

On my machine, this line looks like this:

`!define PUBLIC_DIR {/Users/justynazygmunt/Desktop/cob_spec/public}`

4c. Remember to remove `-` from the beginning of both lines in the 'User-Defined Variables' section. Only then the variables will be visible to the testing framework.

5. Click Save.

6. Run the tests by navigating back to the HttpTestSuite website's initial page and click Suite. All tests should turn green.

![test green](https://image.ibb.co/mEDQgL/Zrzut-ekranu-2018-10-18-o-11-21-17.png)

## Things to improve, spotted irregularities

### Authentication data
TL;DR: disclosed authentication data.
Password and login required to authorize a client making request for the `/logs` path (see `BasicAuthHadler`) are available in `utilities/AuthenticationCredentials`.
This information in publicly visible and was pushed to GitHub because this server is for training purpose and I wanted crafters
from 8th Light to have a complete access to all information in an easy way. In real life though, credentials wouldn't be
publicly visible, but would be hidden as environmental variables.

### Logger
TL;DR: The server has only a basic logger.
When writing the server I was following requirements laid down by the Fitnesse testing framework.
None of the tests needed a logger to pass, so I've decided to not add this functionality and focus on the things that were actually necessary to pass tests.
As I had some time left before submitting the code for review, I've decided to try to add a logger.
I've managed to develop a basic functionality that collects information about:
- connections with clients;
- IOExceptions thrown when connecting with clients;
- IOExceptions thrown within the request-response cycle.
The logger data is then available at the `BasicAuthHadler`, after providing authentication data. The server's logger is injected to `BasicAuthHadler` via `RequestRouter`.

In my opinion, the logger should have some form of logger-handler - an instance that would be injected into each client's thread - and that would
collect more data, for example about requests that were made by the clients. The logger that I've written can't be used for this purpose as it shouldn't disclose its public API to clients.

### Continuous integration
There is no tool ensuring a successful build on my project like Travis CI. No further explanation needed, adding it would be beneficial.

### IntelliJ vs pure Gradle
TL;DR: no gradle.build file.
When working on the project I've used IntelliJ IDE, which is super handy, especially when it comes to refactoring. I was also building the project via IntelliJ.
Because of this, the project has no gradle file that allows for building it outside of IntelliJ environment. It was not really needed for this code review,
as I've provided the review board with a JAR file of an already build project, but should be kept in mind for the future, to make it easier for others to work on the same project.
There is a rumor that not everyone uses IntelliJ :P

### Chrome vs the rest of browsers - what you see when firing the server locally.
When I open the server on Chrome, I don not always see the list of files, but instead this line appears:
`mmmm textwrapon=false; textautoformat=false; wysiwyg=textarea`.
Cleaning cookies didn't help. This issue doesn't happen in other browsers (checked Safari and Mozilla).
If I had more time, I would get to the bottom of this issue.

### SimultaneousTestSuite.TimeToComplete - performance issue
Last but not least, I've spotted a bizzare performance issue that I haven't figured out.
When the SimultaneousTestSuite.TimeToComplete Fitnesse test is run together with other tests (when sunning the whole suite at once),
it takes from 2 to 4 seconds, what seems to be normal time. However, when run separately, it takes more time. What is more, each next time it takes more time, from 5 to 20 seconds.
Each time it's passing though. Again, if I had more time, I would try to understand the reason for it.

