package ru.netology;

import java.io.BufferedReader;

public interface Handler {
    public void handler(Request request, BufferedReader responseStream);
}
