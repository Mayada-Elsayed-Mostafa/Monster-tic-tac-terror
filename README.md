## Overview

The client application allows users to interact with Monster tic-tac-terror. Users can play in three modes: Online (over network) after registering or logging in, vs. computer, and vs. guest (local), record their games and replay them.



## Features

- *Three Game Modes:*
  - Online Mode: Challenge other users over the network.
  - Vs. Computer Mode: Play against an AI with adjustable difficulty (easy, intermediate and hard).
  - Vs. Guest Mode: Play locally with another player on the same machine.
- *Game Recording:*
  - Record matches for replay later.
- *Elegant User Interface:*
  - Intuitive and visually appealing design.



## Requirements

- *Java Version:* Java 8
- *Server:* Must be running the Tic-Tac-Toe server.
- *Libraries:*
  - JavaFX for the graphical user interface.
  - JSON simple 1.1.1 library for server and client interaction and saving and replaying games.



## Setup Instructions

### Step 1: Clone the Repository

```bash
git clone https://github.com/Mayada-Elsayed-Mostafa/TicTacToeGameITIClient.git
cd client
```

### Step 2: Configure the Client

1. Ensure the server is running and accessible.
2. Update the server connection details in the client configuration file (e.g., IP address and port).

### Step 3: Build and Run the Client

1. Compile the client application:
   ```bash
   javac -d bin src/*.java
   ```
2. Add two folders for saving records inside the project folder:
   - offlineRecords
   - onlineRecords
3. Run the client:
   ```bash
   java -cp bin Monster tic-tac-terror
   ```



## Usage

1. Launch the client application.
2. Choose a game mode:
   - *Online:*
     - Enter the server IP to connect to it.
     - Register or log in with your credentials.
     - Select an available player and send a game request.
     - See your online games records.
   - *Vs. Computer:* Adjust difficulty and start playing.
   - *Vs. Guest:* Play locally with another user.
3. Enjoy the game and use the replay feature to review recorded matches.

## Screenshots

Here are some screenshots to showcase the application's features:

## 1. Welcome Page
![Splash Page](images/Client_1.png)
![Welcome Page](images/Client_2.png)

## 2. Single Mode
![Single Mode](images/Client_3.png)
![Single Mode](images/Client_4.png)

## 3. Two Players Mode
![Two Players Mode](images/Client_5.png)
![Two Players Mode](images/Client_6.png)
![Two Players Mode](images/Client_7.png)


## 4. Login Page
![Login Page](images/Client_8.png)
![Login Page](images/Client_9.png)

## 5. Signup Page
![Signup Page](images/Client_10.png)

## 6. Online Mode
![Online Mode](images/Client_11.png)

## 7. In Game Pages
![In Game Pages](images/Client_12.png)
![In Game Pages](images/Client_13.png)
![In Game Pages](images/Client_14.png)
![In Game Pages](images/Client_15.png)
![In Game Pages](images/Client_16.png)
![In Game Pages](images/Client_17.png)
![In Game Pages](images/Client_18.png)

## 8. Records
![Records](images/Client_19.png)
![Records](images/Client_20.png)


## Contributors

1. [Mayada Elsayed](https://github.com/Mayada-Elsayed-Mostafa)
2. [Hazem Mahmoud](https://github.com/Hazem-web)
3. [Shereen Mohamed](https://github.com/shereenmohamed923)
4. [Aya Elsayed](https://github.com/aya-emam-0)
5. [Khairy Hatem](https://github.com/KhairySuleiman4)



## License

This project does not currently have a license. Please contact the contributors for permissions.

