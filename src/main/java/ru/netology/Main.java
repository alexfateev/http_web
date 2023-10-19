package ru.netology;

import java.io.BufferedOutputStream;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        // добавление хендлеров (обработчиков)
        server.addHandler("GET", "/index.html", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                // TODO: handlers code
            }
        });
        server.addHandler("POST", "/messages", new Handler() {
            public void handle(Request request, BufferedOutputStream responseStream) {
                // TODO: handlers code
            }
        });

        server.listen(9999);
    }
}


