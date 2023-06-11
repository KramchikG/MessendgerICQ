package Server;

import Connection.Connection;

import java.util.HashMap;
import java.util.Map;

public class ModelGuiServer {
    // Модель хранит карту со всеми подключившимися клиентами.
    // Ключ - имя клиента, значение - объект Connection.

    private Map<String, Connection> allUsersMultiChat = new HashMap<>();

    // Поле allUsersMultiChat представляет собой карту, где ключом является имя клиента,
    // а значением - объект Connection, представляющий сокетное соединение с клиентом.

    // Метод getAllUsersMultiChat возвращает карту со всеми подключенными клиентами.

    protected Map<String, Connection> getAllUsersMultiChat() {
        return allUsersMultiChat;
    }

    // Метод addUser добавляет клиента в карту.
    // Он принимает имя пользователя и объект Connection и добавляет их в карту allUsersMultiChat.

    protected void addUser(String nameUser, Connection connection) {
        allUsersMultiChat.put(nameUser, connection);
    }

    // Метод removeUser удаляет клиента из карты.
    // Он принимает имя пользователя и удаляет соответствующую запись из карты allUsersMultiChat.

    protected void removeUser(String nameUser) {
        allUsersMultiChat.remove(nameUser);
    }
}
