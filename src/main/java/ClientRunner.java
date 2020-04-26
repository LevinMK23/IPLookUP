import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ClientRunner implements Runnable {

    private ClientHandler client;
    private boolean isRunning;

    public ClientRunner(ClientHandler client) {
        this.client = client;
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                String message = client.readMessage();
                if (message.equals("send file")) {
                    System.out.println("Load file from " + client.getUId());
                    String fileName = client.readMessage();
                    System.out.println("FileName: " + fileName);
                    File dir = new File(client.getUId());
                    if (dir.exists() || dir.mkdir()) {
                        File userFile = new File(client.getUId() + "/" + fileName);
                        if (userFile.exists() || userFile.createNewFile()) {
                            FileOutputStream fos = new FileOutputStream(userFile);
                            int fileLength = Integer.parseInt(client.readMessage());
                            System.out.println("Need load " + fileLength + " bytes");
                            // 0 1 2 3 4 5 6 finish
                            InputStream userStream = client.getStream();
                            byte [] bytes = new byte[8192];
                            for (int i = 0; i < fileLength / 8192 + 1; i++) {
                                int readed = userStream.read(bytes);
                                fos.write(bytes);
                                Arrays.fill(bytes, (byte) 0);
                            }
                            fos.flush();
                            client.sendMessage("File was upload with status OK");
                        }
                    }
                }
                if (message.equals("mkdir")) {
                    String dir = client.readMessage();
                    File file = new File(client.getUId() + "/" + dir);
                    if (file.mkdir()) {
                        System.out.println("Directory was created for " + client.getUId());
                        client.sendMessage("Directory " + dir + " was created on the server");
                    } else {
                        client.sendMessage("Directory " + dir + " exists on the server");
                    }
                }
                if (message.equals("quit")) {
                    client.sendMessage("quit");
                    ClientList.getClients().remove(client);
                    break;
                }
                for (ClientHandler clientHandler : ClientList.getClients()) {
                    if (!clientHandler.equals(client)) {
                        clientHandler.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
