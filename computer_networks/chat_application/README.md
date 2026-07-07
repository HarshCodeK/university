# Multi-Client Chat Application

A Java Socket Programming project demonstrating client-server communication.

## How It Works

1. **Server** listens on port 9999 for incoming client connections
2. Each client connects and gets a dedicated thread on the server
3. Messages are broadcast from any client to all connected clients
4. Type `!bot` for an automated bot response
5. Type `!quit` to exit

## How to Run

### Step 1: Start the Server
```bash
javac Server.java
java Server
```
Keep this terminal window open.

### Step 2: Start Client(s)
Open new terminal windows and run:
```bash
javac Client.java
java Client
```
Run `Client.java` multiple times for multiple users to test broadcasting.

## Features

- Multi-threaded server handles multiple clients
- Real-time message broadcasting
- GUI with sending animation
- Auto bot response (!bot)
- Clean Swing interface

## Project Structure

```
chat_application/
├── Server.java    # Server with multi-threaded client handling
├── Client.java    # Swing GUI client
└── README.md
```
