import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Multi-Client Chat Server
 * 
 * Flow:
 * 1. Server listens on port 9999
 * 2. When a client connects, ServerThread is spawned
 * 3. ServerThread reads messages from that client
 * 4. Server broadcasts the message to ALL connected clients
 * 5. If message is "!bot", server responds with automated reply
 */
public class Server {

    // Static list of all active client handlers for broadcasting
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Chat Server Starting on port 9999 ===");
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[+] New client connected: " + socket.getInetAddress());
                ClientHandler handler = new ClientHandler(socket);
                clients.add(handler);
                handler.start(); // Start thread for this client
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // Broadcast message to all connected clients
    public static void broadcast(String message, ClientHandler sender) {
        String formatted = sender.getClientName() + ": " + message;
        System.out.println("[BROADCAST] " + formatted);
        for (ClientHandler client : clients) {
            client.sendMessage(formatted);
        }
    }

    // Remove disconnected client from list
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("[-] Client removed. Active: " + clients.size());
    }
}

/**
 * Handles individual client communication in a separate thread
 */
class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Handler error: " + e.getMessage());
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    @Override
    public void run() {
        try {
            // First message from client is their name
            clientName = in.readLine();
            System.out.println("[+] " + clientName + " joined the chat");
            Server.broadcast(clientName + " has joined the chat!", this);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("!bot")) {
                    // Bot auto-reply feature
                    out.println("[BOT] Connecting you to an advisor...");
                    out.println("[BOT] All agents are currently busy. Your query (#" 
                        + System.currentTimeMillis() % 10000 + ") has been noted.");
                } else if (message.equalsIgnoreCase("!quit")) {
                    break;
                } else {
                    Server.broadcast(message, this);
                }
            }
        } catch (IOException e) {
            System.out.println("[-] " + clientName + " disconnected");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
            Server.removeClient(this);
            Server.broadcast(clientName + " has left the chat.", this);
        }
    }
}
