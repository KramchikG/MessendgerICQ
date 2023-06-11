package Client;

import java.util.HashSet;
import java.util.Set;

public class ModelGuiClient {
    // В модели клиентского приложения хранится множество подключенных пользователей
    private Set<String> users = new HashSet<>();

    // Метод для получения множества подключенных пользователей
    protected Set<String> getUsers() {
        return users;
    }

    // Метод для добавления пользователя в множество подключенных пользователей
    protected void addUser(String nameUser) {
        users.add(nameUser);
    }

    // Метод для удаления пользователя из множества подключенных пользователей
    protected void removeUser(String nameUser) {
        users.remove(nameUser);
    }

    // Метод для установки множества подключенных пользователей
    protected void setUsers(Set<String> users) {
        this.users = users;
    }
}
