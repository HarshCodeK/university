import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Multi-Client Chat Client (Swing GUI)
 * 
 * Flow:
 * 1. Connect to server at localhost:9999
 * 2. Send messages via text field
 * 3. Receive messages via server broadcast
 * 4. Shows "sending..." animation effect when sending
 */
public class Client extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private String username;

    public Client() {
        setTitle("Chat Client");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Get username
        username = JOptionPane.showInputDialog(this, "Enter your name:", "Login", JOptionPane.PLAIN_MESSAGE);
        if (username == null || username.trim().isEmpty()) {
            username = "User" + (int)(Math.random() * 1000);
        }

        initUI();
        connectToServer();
        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        chatArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 13));
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action listeners
        ActionListener sendAction = e -> sendMessage();
        sendButton.addActionListener(sendAction);
        messageField.addActionListener(sendAction);

        // Close handler
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (out != null) {
                    out.println("!quit");
                }
                try { if (socket != null) socket.close(); } catch (IOException ex) {}
                System.exit(0);
            }
        });
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send username as first message
            out.println(username);

            // Start listener thread for incoming messages
            new Thread(this::listenForMessages).start();

            chatArea.append("[CONNECTED] Welcome, " + username + "!\n");
            chatArea.append("[INFO] Type !bot for automated response\n");
            chatArea.append("[INFO] Type !quit to exit\n\n");
        } catch (IOException e) {
            chatArea.append("[ERROR] Cannot connect to server. Make sure Server.java is running.\n");
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                final String msg = message;
                SwingUtilities.invokeLater(() -> chatArea.append(msg + "\n"));
            }
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> chatArea.append("[DISCONNECTED] Connection lost.\n"));
        }
    }

    private void sendMessage() {
        String msg = messageField.getText().trim();
        if (msg.isEmpty()) return;

        // Show "sending animation" effect
        chatArea.append("[Sending" + ".".repeat((int)(Math.random() * 3) + 1) + "]\n");

        out.println(msg);
        messageField.setText("");

        if (msg.equals("!quit")) {
            try { socket.close(); } catch (IOException e) {}
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
