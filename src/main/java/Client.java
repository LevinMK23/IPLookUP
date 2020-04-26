
import javafx.scene.control.TableRow;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static volatile boolean flag = true;
    public static volatile boolean loading = false;

    public static void main(String[] args) throws IOException {
        // localhost 127.0.0.1 = 192.168.100.3:8080
        Socket socket = new Socket("192.168.100.3", 89);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        new Thread(()-> {
            try {
                while (true) {
                    while (loading) {
                        System.out.print("*");
                        Thread.sleep(500);
                    }
                    // System.out.println();
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            while (true) {
                String serverMessage = null;
                try {
                    serverMessage = in.readUTF();
                    if (serverMessage.equals("File was upload with status OK")) {
                        System.out.println("\n" + serverMessage);
                    } else {
                        System.out.println(serverMessage);
                    }
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
                if (s.equals("send file")) {
                    out.writeUTF(s);
                    out.flush();
                    System.out.println("Input fileName like data.txt or other");
                    String fileName = scanner.nextLine();
                    out.writeUTF(fileName);
                    out.flush();
                    File file = new File(fileName);
                    FileInputStream fis = new FileInputStream(file);
                    out.writeUTF(String.valueOf(file.length()));
                    out.flush();
                    loading = true;
                    byte [] bytes = new byte[8192];
                    while (fis.read(bytes) != -1) {
                        out.write(bytes);
                    }
                    loading = false;
                    out.flush();
                } else {
                    out.writeUTF(s);
                    out.flush();
                }
                if (s.equals("mkdir")) {
                    System.out.println("Input directory name");
                    String dir = scanner.nextLine();
                    out.writeUTF(dir);
                    out.flush();
                }
            }
            if (s.equals("quit")) {
                break;
            }
        }
    }
}
