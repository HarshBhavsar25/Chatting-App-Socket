💬 Java Socket Chat Application

A simple real-time desktop chat application built using Java Swing and Socket Programming (TCP).

This project demonstrates client-server communication with a modern chat UI inspired by messaging apps.

🚀 Features

🔌 Real-time communication using TCP Sockets

🖥️ Two separate chat windows (Server & Client)

💬 Modern chat bubble UI

⏰ Message timestamps

📜 Auto scroll to latest message

🎨 Clean & responsive Swing interface

🟢 Online status indicator

❌ Smooth window close animation

🛠️ Tech Stack

Java

Java Swing (GUI)

Socket Programming

TCP Protocol

Multithreading

📂 Project Structure
Salman.java      -> Server Side
Shahrukh.java    -> Client Side
icons/           -> UI icons & images
⚙️ How It Works

Salman.java acts as the Server

Creates ServerSocket on port 3434

Waits for client connection

Shahrukh.java acts as the Client

Connects to server using localhost

Exchanges messages using DataInputStream & DataOutputStream

Messages are sent using:

writeUTF()
readUTF()
▶️ How To Run
Step 1: Compile
javac Salman.java
javac Shahrukh.java
Step 2: Run Server First
java Salman
Step 3: Run Client
java Shahrukh

Now both windows will connect and start chatting 🎉

🧠 Concepts Covered

Client-Server Architecture

TCP Communication

Java Networking

Multithreading

Event Handling

Swing UI Design

📸 Future Improvements

Multiple clients support

User authentication

File sharing

Emoji support

Database message storage

Online user list

👨‍💻 Author

Harsh Bhavsar

If you like this project ⭐ Star the repository!
