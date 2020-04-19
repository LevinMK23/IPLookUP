import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(89);
        System.out.println("Server started on port = 89");
        int cnt = 1;
        Scanner in = new Scanner(System.in);
        new Thread(() -> {
            try {
                while (true) {
                    String command = in.next();
                    if (command.equals("kick")) {
                        int number = in.nextInt();
                        int c = 0;
                        for (ClientHandler client : ClientList.getClients()) {
                            c++;
                            if (c == number) {
                                ClientList.getClients().remove(client);
                                client.sendMessage("quit");
                                // System.exit(0);
                            }
                        }
                    } else if (command.equals("quit")) {
                        for (ClientHandler client : ClientList.getClients()) {
                            ClientList.getClients().remove(client);
                            client.sendMessage("quit");
                        }
                        for (ClientRunner runner : ClientList.getRunners()) {
                            runner.stop();
                        }
                        System.exit(0);
                    } else if (command.equals("clients")) {
                        int c = 1;
                        for (ClientHandler client : ClientList.getClients()) {
                            System.out.println(c + ") client: " + client);
                            c++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        try {
            while (true) {
                try {
                    Socket socket = server.accept();
                    System.out.println("Client " + socket.getInetAddress() + " accepted!");
                    ClientHandler client = new ClientHandler(socket);
                    ClientList.getClients().add(client);
                    ClientRunner runner = new ClientRunner(client);
                    ClientList.getRunners().add(runner);
                    new Thread(runner).start();
                    cnt++;
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {

        }
    }
}
