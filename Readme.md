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

## Things to improve

### Authorisation data
Password and login required to authorize a client (see `BasicAuthHadler`) are available in `utilities/AuthenticationCredentials`.
This information in publicly visible and was pushed to GitHub because this server is for training purpose and I wanted crafters
from 8th Light to have a complete access to all information in an easy way. In real life though, credentials wouldn't be
publicly visible, but would be hidden as environmental variables.

### Logger
The server has no logger. When writing the server I was following requirements laid down by the Fitnesse testing framework.
None of the tests needed a logger, so I've decided to not add this functionality and focus on the things that were actually necessary to pass tests.
Although not explicitly required, I am aware that a form of logging is very useful and should be implemented.
If I had more time, I would write a logger that:
- collects and logs information about requests made;
- collects and logs information about errors.

### Continuous integration
There is no tool ensuring a successful build on my project like Travis CI. No further explanation needed, adding it would be beneficial.
