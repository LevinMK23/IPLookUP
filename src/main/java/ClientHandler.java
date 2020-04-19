import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;
    private static int counter = 0;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        nickName = "user" + counter;
        counter++;
    }



    public String readMessage() throws IOException {
        return in.readUTF();
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
    }

    @Override
    public String toString() {
        return nickName + " IP: " + socket.getInetAddress().toString();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
