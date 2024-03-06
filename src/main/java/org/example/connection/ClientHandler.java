package org.example.connection;

import org.example.model.domain.ATM;
import org.example.model.domain.User;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static org.example.connection.Server.clients;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;
    private ATM atm;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ATM getATM() {
        return atm;
    }

    public void setATM(ATM atm) {
        this.atm = atm;
    }

    public ClientHandler() {

    }
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            //Log in
            boolean isLogged;
            do{
                do{
                    isLogged =false;
                    user = (User) in.readObject();
                    synchronized (clients) {
                        while (clients.containsKey(user)) {
                            out.writeObject(false);
                            out.flush();
                            user = (User) in.readObject();
                        }
                        out.writeObject(true);
                        out.flush();
                        clients.put(user, out);

                        if ((boolean) in.readObject()) {


                        } else {
                            isLogged = true;
                        }
                    }
                } while(!isLogged);
            } while (!isLogged);



            //User Log in and Room creation
            /*boolean isLogged;
            do {
                do {
                    isLogged = false;
                    user = (User) in.readObject();

                    synchronized (clients) {
                        while (clients.containsKey(user)) {
                            out.writeObject(false);
                            out.flush();
                            user = (User) in.readObject();
                        }
                        out.writeObject(true);
                        out.flush();
                        clients.put(user, out);

                        if ((boolean) in.readObject()) {

                            room = (Room) in.readObject();
                            synchronized (rooms) {
                                if (rooms.containsKey(room)) {
                                    out.writeObject(false);
                                    out.flush();
                                    clients.remove(user);
                                } else {
                                    out.writeObject(true);
                                    out.flush();
                                    rooms.put(room, new HashSet<>());
                                    rooms.get(room).add(user);
                                    isLogged = true;
                                }
                            }
                        } else {
                            isLogged = true;
                        }
                    }
                } while (!isLogged);

                //At this point the user is logged
                //Room created if needed

                boolean isLeaving;
                do{
                    isLeaving = false;
                    //Send the rooms list to the client

                    Set<Room> roomSet = new HashSet<>();
                    if (!rooms.isEmpty()) {
                        for (Room room : rooms.keySet()) {
                            roomSet.add(room);
                        }
                        out.writeObject(roomSet);
                        out.flush();
                    } else {
                        out.writeObject(roomSet);
                        out.flush();
                    }

                    //Checks if exiting or not

                    boolean isExiting = (boolean) in.readObject();
                    if (isExiting) {
                        clients.remove(user);
                        isLogged = false;
                        isLeaving = true;
                    } else {

                        //Join a room

                        Room enteringRoom = (Room) in.readObject();
                        User enteringUser = (User) in.readObject();
                        clientRooms.put(enteringUser, enteringRoom);
                        rooms.get(enteringRoom).add(enteringUser);

                        //Start reading and sending messages
                        Message message;
                        while ((message = (Message) in.readObject()) != null) {
                            if (message.getRoom() != null) {
                                broadcastToRoom(message.getRoom(), message);
                            }
                        }

                        //If null Message, removes the user from the room.

                        clientRooms.remove(enteringUser);
                        rooms.get(enteringRoom).remove(enteringUser);

                        isLeaving = false;

                        out.writeObject(null);
                        out.flush();

                    }
                }while (!isLeaving);
            }while(!isLogged);*/
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (user != null) {
                clients.remove(user);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

