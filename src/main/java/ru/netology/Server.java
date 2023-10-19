package ru.netology;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final ExecutorService exec = Executors.newFixedThreadPool(64);

    public void listen(int port) {

        try (final var serverSocket = new ServerSocket(port)) {
            while (true) {
                exec.submit(new ThreadHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
