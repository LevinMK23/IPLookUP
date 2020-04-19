import java.util.concurrent.ConcurrentLinkedDeque;

public class ClientList {

    public static ConcurrentLinkedDeque<ClientHandler> getClients() {
        return clients;
    }

    private static ConcurrentLinkedDeque<ClientHandler> clients = new ConcurrentLinkedDeque<>();

    public static ConcurrentLinkedDeque<ClientRunner> getRunners() {
        return runners;
    }

    private static ConcurrentLinkedDeque<ClientRunner> runners = new ConcurrentLinkedDeque<>();

}
