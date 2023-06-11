package Client;



import java.io.IOException;
import java.net.Socket;

public class Client {

    private static ModelGuiClient model;
    private static ViewGuiClient gui;
    private volatile boolean isConnect = false; //флаг отобаржающий состояние подключения клиента  серверу

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    //точка входа в клиентское приложение
    public static void main(String[] args) {
        Client client = new Client();
        model = new ModelGuiClient();
        gui = new ViewGuiClient(client);
        gui.initFrameClient();
        while (true) {
            if (client.isConnect()) {

                client.setConnect(false);
            }
        }
    }
}





