package ru.netology;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private final String method;
    private final String path;
    private final String requestLine;
    private final List<NameValuePair> nameValuePair;
    private Map<String, String> queryParam = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Request(String requestLine) {
        final var parts = requestLine.split(" ");
        this.requestLine = requestLine;
        this.method = parts[0];
        this.path = parts[1];
        this.nameValuePair = URLEncodedUtils.parse(requestLine, StandardCharsets.UTF_8);
        for (NameValuePair pair : nameValuePair) {
            System.out.println(pair.getName() + ": " + pair.getValue());
        }

    }

    public List<NameValuePair> getQueryParams() {
        return nameValuePair;
    }

    public List<String> getQueryParam(String name) {
        List<String> result = new ArrayList<>();
        for( NameValuePair pair : nameValuePair){
            if (pair.getName().equals(name)) {
                result.add(pair.getValue());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
