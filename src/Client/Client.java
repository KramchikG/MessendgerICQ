package Client;

import Connection.*;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Connection connection; // Объект для обмена сообщениями с сервером
    private static ModelGuiClient model; // Модель для хранения списка пользователей
    private static ViewGuiClient gui; // Представление - графический интерфейс
    private volatile boolean isConnect = false; // Флаг, отображающий состояние подключения клиента к серверу

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    // Точка входа в клиентское приложение
    public static void main(String[] args) {
        Client client = new Client();
        model = new ModelGuiClient();
        gui = new ViewGuiClient(client);
        gui.initFrameClient();
        while (true) {
            if (client.isConnect()) {
                client.nameUserRegistration();
                client.receiveMessageFromServer();
                client.setConnect(false);
            }
        }
    }

    // Метод подключения клиента к серверу
    protected void connectToServer() {
        // Если клиент не подключен к серверу, выполняем подключение
        if (!isConnect) {
            while (true) {
                try {
                    // Получаем адрес и порт сервера из диалоговых окон
                    String addressServer = gui.getServerAddressFromOptionPane();
                    int port = gui.getPortServerFromOptionPane();
                    // Создаем сокет и объект Connection для обмена сообщениями с сервером
                    Socket socket = new Socket(addressServer, port);
                    connection = new Connection(socket);
                    isConnect = true;
                    gui.addMessage("Сервисное сообщение: Вы подключились к серверу.\n");
                    break;
                } catch (Exception e) {
                    gui.errorDialogWindow("Произошла ошибка! Возможно, вы ввели неправильный адрес сервера или порт. Попробуйте еще раз.");
                    break;
                }
            }
        } else {
            gui.errorDialogWindow("Вы уже подключены!");
        }
    }

    // Метод регистрации имени пользователя на клиентском приложении
    protected void nameUserRegistration() {
        while (true) {
            try {
                Message message = connection.receive();
                // Если получено запрос на имя от сервера, вызываем диалоговое окно для ввода имени и отправляем его на сервер
                if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {
                    String nameUser = gui.getNameUser();
                    connection.send(new Message(MessageType.USER_NAME, nameUser));
                }
                // Если получено сообщение, что имя уже используется, выводим сообщение об ошибке и повторяем ввод имени
                if (message.getTypeMessage() == MessageType.NAME_USED) {
                    gui.errorDialogWindow("Данное имя уже используется. Введите другое имя.");
                    String nameUser = gui.getNameUser();
                    connection.send(new Message(MessageType.USER_NAME, nameUser));
                }
                // Если имя принято сервером, получаем список всех подключенных пользователей и выходим из цикла
                if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                    gui.addMessage("Сервисное сообщение: Ваше имя принято!\n");
                    model.setUsers(message.getListUsers());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                gui.errorDialogWindow("Произошла ошибка при регистрации имени. Попробуйте переподключиться.");
                try {
                    connection.close();
                    isConnect = false;
                    break;
                } catch (IOException ex) {
                    gui.errorDialogWindow("Ошибка при закрытии соединения.");
                }
            }
        }
    }

    // Метод отправки сообщения на сервер для передачи другим пользователям
    protected void sendMessageOnServer(String text) {
        try {
            connection.send(new Message(MessageType.TEXT_MESSAGE, text));
        } catch (Exception e) {
            gui.errorDialogWindow("Ошибка при отправке сообщения.");
        }
    }

    // Метод для приема сообщений от сервера, отправленных другими клиентами
    protected void receiveMessageFromServer() {
        while (isConnect) {
            try {
                Message message = connection.receive();
                // Если получено сообщение типа TEXT_MESSAGE, добавляем текст сообщения в окно переписки
                if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                    gui.addMessage(message.getTextMessage());
                }
                // Если получено сообщение типа USER_ADDED, добавляем пользователя в список и выводим сообщение о присоединении пользователя в окно переписки
                if (message.getTypeMessage() == MessageType.USER_ADDED) {
                    model.addUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Сервисное сообщение: Пользователь %s присоединился к чату.\n", message.getTextMessage()));
                }
                // Аналогично для сообщений типа REMOVED_USER (отключение других пользователей)
                if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                    model.removeUser(message.getTextMessage());
                    gui.refreshListUsers(model.getUsers());
                    gui.addMessage(String.format("Сервисное сообщение: Пользователь %s покинул чат.\n", message.getTextMessage()));
                }
            } catch (Exception e) {
                gui.errorDialogWindow("Ошибка при приеме сообщения от сервера.");
                setConnect(false);
                gui.refreshListUsers(model.getUsers());
                break;
            }
        }
    }

    // Метод для отключения клиента от сервера
    protected void disableClient() {
        try {
            if (isConnect) {
                connection.send(new Message(MessageType.DISABLE_USER));
                model.getUsers().clear();
                isConnect = false;
                gui.refreshListUsers(model.getUsers());
            } else {
                gui.errorDialogWindow("Вы уже отключены.");
            }
        } catch (Exception e) {
            gui.errorDialogWindow("Сервисное сообщение: Произошла ошибка при отключении.");
        }
    }
}
