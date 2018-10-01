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




