
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static volatile boolean flag = true;

    public static void main(String[] args) throws IOException {
        // localhost 127.0.0.1 = 192.168.100.3:8080
        Socket socket = new Socket("192.168.100.3", 89);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (true) {
                String serverMessage = null;
                try {
                    serverMessage = in.readUTF();
                    System.out.println(serverMessage);
                    if (serverMessage.equals("quit")) {
                        out.writeUTF("quit");
                        out.flush();
                        in.close();
                        out.close();
                        socket.close();
                        System.out.println("Disconnected");
                        flag = false;
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
        String s = null;
        while (flag) {
            s = scanner.nextLine();
            if (!socket.isClosed()) {
                out.writeUTF(s);
                out.flush();
            }
            if (s.equals("quit")) {
                break;
            }
        }
    }
}
