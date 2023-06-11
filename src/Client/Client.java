package Client;

import Connection.*;


public class Client {

    private Connection connection;
    private static ModelGuiClient model;
    private static ViewGuiClient gui;
    private volatile boolean isConnect = false; // флаг, отображающий состояние подключения клиента к серверу
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

        while (true) {
            if (client.isConnect()) {

                client.setConnect(false);
            }
        }
    }
}





