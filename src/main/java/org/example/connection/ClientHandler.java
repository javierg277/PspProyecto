package org.example.connection;

import org.example.model.domain.User;

import java.io.*;
import java.net.Socket;

import static org.example.connection.Server.clients;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private User user;

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

    public ClientHandler() {

    }
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            boolean isConnected = true;

            do {
                int choice = in.readInt();
                switch (choice) {
                    case 1:
                        String name = (String) in.readObject();
                        if(Server.checkName(name)){
                            out.writeBoolean(true);
                            out.flush();
                            String pass = (String) in.readObject();
                            if(Server.checkPass(name, pass)) {
                                out.writeBoolean(true);
                                out.flush();
                                int opt;
                                do {
                                    user = Server.getUser(name);
                                    out.writeObject((User) user);
                                    out.flush();
                                    opt = in.readInt();
                                    switch (opt) {
                                        case 1:
                                            double money = in.readDouble();
                                            user = Server.addMoney(user, money);
                                            out.writeObject((User) user);
                                            out.flush();
                                            break;
                                        case 2:
                                            double money2 = in.readDouble();
                                            user = Server.withdrawMoney(user, money2);
                                            out.writeObject((User) user);
                                            out.flush();
                                            break;
                                        case 3:
                                            String receptorName = (String) in.readObject();
                                            User receptorUser = Server.getUser(receptorName);
                                            out.writeObject((User) receptorUser);
                                            out.flush();
                                            double money3 = in.readDouble();
                                            user = Server.giveMoney(user, money3, receptorUser);
                                            out.writeObject((User) user);
                                            out.flush();
                                            break;
                                        case 4:
                                            user = Server.getUser(user.getName());
                                            out.writeObject((User)user);
                                            out.flush();
                                            break;
                                        default:
                                            break;
                                    }
                                }while(opt != 0);
                            }else{
                                out.writeBoolean(false);
                                out.flush();
                            }
                        }else{
                            out.writeBoolean(false);
                            out.flush();
                        }
                        break;
                    case 0:
                        isConnected = false;
                        break;
                    default:
                        break;
                }
            }while (isConnected);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

