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
