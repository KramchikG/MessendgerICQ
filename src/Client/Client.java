package Client;



import java.io.IOException;
import java.net.Socket;

public class Client {

    private static ModelGuiClient model;
    private static ViewGuiClient gui;
    private volatile boolean isConnect = false; //флаг отобаржающий состояние подключения клиента  серверу
    /**
     * Проверяет, установлено ли подключение клиента к серверу.
     *
     * @return true, если подключение установлено, в противном случае - false
     */
    public boolean isConnect() {
        return isConnect;
    }
    /**
     * Устанавливает состояние подключения клиента к серверу.
     *
     * @param connect true, если подключение установлено, false - если нет
     */
    public void setConnect(boolean connect) {
        isConnect = connect;
    }
    /**
     * Точка входа в клиентское приложение.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Client client = new Client();
        model = new ModelGuiClient();
        gui = new ViewGuiClient(client);
        gui.initFrameClient();
        // Бесконечный цикл для обработки состояния подключения
        while (true) {
            if (client.isConnect()) {
                // Действия, выполняемые при подключении клиента

                client.setConnect(false); // Сброс флага подключения
            }
        }
    }
}





