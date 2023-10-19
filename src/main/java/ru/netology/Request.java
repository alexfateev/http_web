package ru.netology;

public class Request {

    private final String method;
    private final String path;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Request(String requestLine) {
        final var parts = requestLine.split(" ");
        if (parts.length != 3) {
            this.method = "";
            this.path = "";
        } else {
            this.method = parts[0];
            this.path = parts[1];
        }
    }
}
