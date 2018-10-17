## For the start

### Cob Spec

Clone the Cob Spec repo to save the test suite and root file directory:

```git clone https://github.com/8thlight/cob_spec.git```

Read about the requirements for Cob spec here: https://github.com/8thlight/cob_spec.

Clone this project repo:

```git clone https://github.com/Kotauror/java-http-server.git```

### Run the http server

- go to ```/out/artifacts/java_http_server_jar``` in the cloned directory
- run:
```java -jar java-http-server.jar -p 8080 -d DIRECTORY_TO_SERVE```

This server should now be running even though there is no confirmation message.
Replace DIRECTORY_TO_SERVE with the absolute path to the cloned Cob spec folder, for me it is:
```java -jar java-http-server.jar -p 8085 -d ~/Desktop/cob_spec/public/```

### Run the Fitnesse end to end tests

Go to the cob_spec cloned directory and run:
```java -jar fitnesse.jar -p 9090```


### Things to improve

#### Authorisation data
Password and login required to authorize a client (see `BasicAuthHadler`) are available in `utilities/AuthenticationCredentials`.
This information in publicly visible and was pushed to GitHub because this server is for training purpose and I wanted crafters
from 8th Light to have a complete access to all information in an easy way. In real life though, credentials wouldn't be
publicly visible, but would be hidden as environmental variables.

#### Logger
The server has no logger. When writing the server I was following requirements laid down by the Fitnesse testing framework.
None of the tests needed a logger, so I've decided to not add this functionality and focus on the things that were actually necessary to pass tests.
Although not explicitly required, I am aware that a form of logging is very useful and should be implemented.
If I had more time, I would write a logger that:
- collects and logs information about requests made;
- collects and logs information about errors.
