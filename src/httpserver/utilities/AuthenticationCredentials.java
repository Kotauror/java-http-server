package httpserver.utilities;

public enum AuthenticationCredentials {

    LOGIN("admin"),
    PASSWORD("hunter2");

    private final String value;

    AuthenticationCredentials(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
