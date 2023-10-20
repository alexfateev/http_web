package ru.netology;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ExecutorService exec = Executors.newFixedThreadPool(64);
    private ConcurrentHashMap<String,
            ConcurrentHashMap<String, Handler>> handlers = new ConcurrentHashMap<>();

    public Server() {
        fillHandlers();
    }

    private void fillHandlers() {
        final var validPaths = List.of("/index.html", "/spring.svg", "/spring.png",
                "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html",
                 "/classic.html", "/events.html", "/events.js");
        validPaths.forEach(c -> addHandler("GET", c, new Handler() {
            @Override
            public void handle(Request request, BufferedOutputStream responseStream) {
                try {
                    final var filePath = Path.of(".", "public", request.getPath());
                    final var mimeType = Files.probeContentType(filePath);
                    final var length = Files.size(filePath);
                    responseStream.write((
                            "HTTP/1.1 200 OK\r\n" +
                                    "Content-Type: " + mimeType + "\r\n" +
                                    "Content-Length: " + length + "\r\n" +
                                    "Connection: close\r\n" +
                                    "\r\n"
                    ).getBytes());
                    Files.copy(filePath, responseStream);
                    responseStream.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, Handler>> getHandlers() {
        return handlers;
    }

    public void listen(int port) {

        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                exec.submit(new ThreadHandler(serverSocket.accept(), this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        handlers.putIfAbsent(method, new ConcurrentHashMap<>());
        handlers.get(method).put(path, handler);
    }

    public Handler getHandler(Request request) {
        return handlers.get(request.getMethod()).get(request.getPath());
    }

}
