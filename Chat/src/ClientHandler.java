import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private String nick;
    private Channel channel;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;

        try {
            channel = ChannelBase.of(socket);
            new Thread(() -> {
                Thread authorization = new Thread(()->auth());
                authorization.start();
                try {
                    authorization.join(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (nick == null){
                    sendMessage("authorization time out");
                    channel.sendMessage(new Message(MessageType.EXIT_COMMAND,""));
                    System.out.println("authorization time out");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                System.out.println(nick + " handler waiting for new massages");
                while (socket.isConnected()) {
                    Message msg = channel.getMessage();
                    if (msg == null) continue;
                    switch (msg.getType()) {
                        case EXIT_COMMAND:
                            server.unsubscribe(this);
                            break;
                        case PRIVATE_MESSAGE:
                            sendPrivateMessage(msg.getBody());
                            break;
                        case BROADCAST_CHAT:
                            server.sendBroadcastMessage(nick + " : " + msg.getBody());
                        case CHANGE_NICK:
                            changeNick(msg);
                        default:
                            System.out.println("invalid message type");
                    }
                }

            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeNick(Message msg) {
        String[] strings = msg.getBody().split(" ");
        String newNick = strings[2];
        server.getAuthService().changeNick(newNick, nick);
        server.sendBroadcastMessage(nick + " renamed to: " + newNick);
        nick = newNick;
    }

    private void sendPrivateMessage(String messageWithNickTo) {
        int firstSpaceIndex = messageWithNickTo.indexOf(" ");
        final String nickTo = messageWithNickTo.substring(0, firstSpaceIndex);
        final String message = messageWithNickTo.substring(firstSpaceIndex).trim();
        if (server.isNickTaken(nickTo)) {
            server.sendPrivateMessage(nick, nickTo, nick + " -> " + nickTo + " : " + message);
        } else {
            sendMessage(nickTo + " not taken!");
        }
    }

    /**
     * Wait for command: "/auth login1 pass1"
     */
    private void auth() {
        while (true) {
            if (!channel.hasNextLine()) continue;
            Message message = channel.getMessage();
            if (MessageType.AUTH_MESSAGE.equals(message.getType())) {
                String[] commands = message.getBody().split(" ");// /auth login1 pass1
                if (commands.length >= 3) {
                    String login = commands[1];
                    String password = commands[2];
                    System.out.println("Try to login with " + login + " and " + password);
                    String nick = server.getAuthService()
                            .authByLoginAndPassword(login, password);
                    if (nick == null) {
                        String msg = "Invalid login or password";
                        System.out.println(msg);
                        sendMessage(msg);
                    } else if (server.isNickTaken(nick)) {
                        String msg = "Nick " + nick + " already taken!";
                        System.out.println(msg);
                        sendMessage(msg);
                    } else {
                        this.nick = nick;
                        String msg = "Auth ok!";
                        System.out.println(msg);
                        sendMessage(msg);
                        server.subscribe(this);
                    }
                }
            } else {
                sendMessage("Invalid command!");
            }
        }
    }

    public void sendMessage(String msg) {
        channel.sendMessage(msg);
    }

    public String getNick() {
        return nick;
    }
}