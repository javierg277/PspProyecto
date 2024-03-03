package org.example.connection;

import org.example.model.domain.ATM;
import org.example.model.domain.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8080;
    private static final int MAX_CLIENTS = 100;

    static Map<User, ObjectOutputStream> clients = new HashMap<>();
    static Map<User, ATM> clientRooms = new HashMap<>();
    static Map<ATM, Set<User>> rooms = new HashMap<>();
    static ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

    public static void main(String[] args) {
        System.out.println("Chat Server is running...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                pool.execute(new ClientHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
