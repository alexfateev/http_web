package ru.netology;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadHandler implements Runnable {

    private final Socket socket;
    private final Server server;

    public ThreadHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                final var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final var out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            Request request = new Request(in.readLine());
            Handler handler = server.getHandler(request);
            if (handler != null) {
                handler.handle(request, new BufferedOutputStream(socket.getOutputStream()));
            } else {
                out.write((
                        "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Length: 0\r\n" +
                                "Connection: close\r\n" +
                                "\r\n"
                ).getBytes());
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
