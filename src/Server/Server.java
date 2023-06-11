package Server;

import Connection.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Server {
    private ServerSocket serverSocket;
    private static ViewGuiServer gui; // объект класса представления
    private static ModelGuiServer model; // объект класса модели
    private static volatile boolean isServerStart = false; // флаг отражающий состояние сервера запущен/остановлен

    // Метод startServer запускает сервер на указанном порту.
    // Он создает новый объект ServerSocket и устанавливает флаг isServerStart в true.
    // Если сервер не удалось запустить, выводится сообщение об ошибке в представление.

    protected void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isServerStart = true;
            gui.refreshDialogWindowServer("Сервер запущен.\n");
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Не удалось запустить сервер.\n");
        }
    }

    // Метод stopServer останавливает сервер.
    // Если серверный сокет открыт, то он закрывается, а также закрываются соединения с клиентами.
    // Затем все пользователи удаляются из модели, и флаг isServerStart устанавливается в false.
    // Если сервер не запущен, выводится сообщение об ошибке в представление.

    protected void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                for (Map.Entry<String, Connection> user : model.getAllUsersMultiChat().entrySet()) {
                    user.getValue().close();
                }
                serverSocket.close();
                model.getAllUsersMultiChat().clear();
                gui.refreshDialogWindowServer("Сервер остановлен.\n");
            } else {
                gui.refreshDialogWindowServer("Сервер не запущен - останавливать нечего!\n");
            }
        } catch (Exception e) {
            gui.refreshDialogWindowServer("Остановить сервер не удалось.\n");
        }
    }

    // Метод acceptServer запускает бесконечный цикл, в котором сервер принимает новые сокетные подключения от клиентов.
    // При каждом принятом подключении создается новый объект ServerThread (поток сервера) и запускается.

    protected void acceptServer() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new ServerThread(socket).start();
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Связь с сервером потеряна.\n");
                break;
            }
        }
    }

    // Метод sendMessageAllUsers рассылает заданное сообщение всем клиентам из модели.
    // Он перебирает всех подключенных клиентов и отправляет сообщение каждому.
    // Если при отправке сообщения возникла ошибка, выводится сообщение об ошибке в представление.

    protected void sendMessageAllUsers(Message message) {
        for (Map.Entry<String, Connection> user : model.getAllUsersMultiChat().entrySet()) {
            try {
                user.getValue().send(message);
            } catch (Exception e) {
                gui.refreshDialogWindowServer("Ошибка отправки сообщения всем пользователям!\n");
            }
        }
    }

    // Точка входа для приложения сервера.
    // Создается объект сервера, представление и модель.
    // Затем запускается бесконечный цикл ожидания флага isServerStart, который устанавливается в true при запуске сервера.
    // При получении сигнала о запуске сервера вызывается метод acceptServer для принятия новых подключений.
    // Цикл продолжается до тех пор, пока сервер не остановится или не возникнет исключение.

    public static void main(String[] args) {
        Server server = new Server();
        gui = new ViewGuiServer(server);
        model = new ModelGuiServer();
        gui.initFrameServer();
        while (true) {
            if (isServerStart) {
                server.acceptServer();
                isServerStart = false;
            }
        }
    }

    // Класс-поток ServerThread, который запускается при принятии сервером нового сокетного соединения с клиентом.
    // В конструктор передается объект Socket, представляющий сокетное соединение с клиентом.

    private class ServerThread extends Thread {
        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        // Метод requestAndAddingUser реализует запрос имени у клиента и добавление его в модель.
        // Он отправляет клиенту запрос на имя и ожидает ответ.
        // Если имя не занято другим клиентом, оно добавляется в модель и отправляется клиенту подтверждение.
        // Затем отправляется сообщение всем клиентам о новом пользователе.
        // Метод возвращает имя пользователя.

        private String requestAndAddingUser(Connection connection) {
            while (true) {
                try {
                    connection.send(new Message(MessageType.REQUEST_NAME_USER));
                    Message responseMessage = connection.receive();
                    String userName = responseMessage.getTextMessage();
                    if (responseMessage.getTypeMessage() == MessageType.USER_NAME && userName != null && !userName.isEmpty() && !model.getAllUsersMultiChat().containsKey(userName)) {
                        model.addUser(userName, connection);
                        Set<String> listUsers = new HashSet<>();
                        for (Map.Entry<String, Connection> users : model.getAllUsersMultiChat().entrySet()) {
                            listUsers.add(users.getKey());
                        }
                        connection.send(new Message(MessageType.NAME_ACCEPTED, listUsers));
                        sendMessageAllUsers(new Message(MessageType.USER_ADDED, userName));
                        return userName;
                    } else {
                        connection.send(new Message(MessageType.NAME_USED));
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer("Возникла ошибка при запросе и добавлении нового пользователя\n");
                }
            }
        }

        // Метод messagingBetweenUsers реализует обмен сообщениями между пользователями.
        // Он получает сообщение от клиента и пересылает его всем пользователям (кроме отправителя).
        // Если получено сообщение о выходе пользователя, он удаляется из модели и закрывается соединение.

        private void messagingBetweenUsers(Connection connection, String userName) {
            while (true) {
                try {
                    Message message = connection.receive();
                    if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                        String textMessage = String.format("%s: %s\n", userName, message.getTextMessage());
                        sendMessageAllUsers(new Message(MessageType.TEXT_MESSAGE, textMessage));
                    }
                    if (message.getTypeMessage() == MessageType.DISABLE_USER) {
                        sendMessageAllUsers(new Message(MessageType.REMOVED_USER, userName));
                        model.removeUser(userName);
                        connection.close();
                        gui.refreshDialogWindowServer(String.format("Пользователь с удаленным доступом %s отключился.\n", socket.getRemoteSocketAddress()));
                        break;
                    }
                } catch (Exception e) {
                    gui.refreshDialogWindowServer(String.format("Произошла ошибка при рассылке сообщения от пользователя %s, либо отключился!\n", userName));
                    break;
                }
            }
        }

        // Переопределение метода run для запуска потока.
        // В методе запрашивается имя пользователя, обмениваются сообщениями между пользователями.

        @Override
        public void run() {
            gui.refreshDialogWindowServer(String.format("Подключился новый пользователь с удаленным сокетом - %s.\n", socket.getRemoteSocketAddress()));
            try {
                Connection connection = new Connection(socket);
                String nameUser = requestAndAddingUser(connection);
                messagingBetweenUsers(connection, nameUser);
            } catch (Exception e) {
                gui.refreshDialogWindowServer(String.format("Произошла ошибка при рассылке сообщения от пользователя!\n"));
            }
        }
    }
}
