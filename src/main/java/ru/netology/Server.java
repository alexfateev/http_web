package ru.netology;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ExecutorService exec = Executors.newFixedThreadPool(64);
    private ConcurrentHashMap<String,
            ConcurrentHashMap<String, Handler>> handlers = new ConcurrentHashMap<>();

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
        handlers.get(method).putIfAbsent(path, handler);
    }

    public Handler getHandler(Request request) {
        return handlers.get(request.getMethod()).get(request.getPath());
    }

}
