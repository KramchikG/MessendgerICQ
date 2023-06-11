package Server;

import Connection.Connection;

import java.util.HashMap;
import java.util.Map;

public class ModelGuiServer {
    // Модель хранит карту со всеми подключившимися клиентами. Ключ - имя клиента, значение - объект Connection.
    private Map<String, Connection> allUsersMultiChat = new HashMap<>();

    /**
     * Возвращает карту со всеми подключившимися клиентами.
     *
     * @return карта со всеми подключившимися клиентами
     */
    protected Map<String, Connection> getAllUsersMultiChat() {
        return allUsersMultiChat;
    }

    /**
     * Добавляет нового пользователя в карту подключившихся клиентов.
     *
     * @param nameUser    имя пользователя
     * @param connection  объект Connection для пользователя
     */
    protected void addUser(String nameUser, Connection connection) {
        allUsersMultiChat.put(nameUser, connection);
    }

    /**
     * Удаляет пользователя из карты подключившихся клиентов.
     *
     * @param nameUser    имя пользователя
     */
    protected void removeUser(String nameUser) {
        allUsersMultiChat.remove(nameUser);
    }
}
