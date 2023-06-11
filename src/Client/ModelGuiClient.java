package Client;

import java.util.HashSet;
import java.util.Set;

public class ModelGuiClient {

    private Set<String> users = new HashSet<>();

    /**
     * Отримати множину користувачів.
     *
     * @return множина користувачів
     */
    protected Set<String> getUsers(){
        return users;
    }

    /**
     * Додати користувача до множини користувачів.
     *
     * @param nameUser ім'я користувача для додавання
     */
    protected void addUser(String nameUser) {
        users.add(nameUser);
    }

    /**
     * Видалити користувача з множини користувачів.
     *
     * @param nameUser ім'я користувача для видалення
     */
    protected void removeUser(String nameUser) {
        users.remove(nameUser);
    }

    /**
     * Встановити множину користувачів.
     *
     * @param users множина користувачів для встановлення
     */
    protected void setUsers(Set<String> users){
        this.users = users;
    }
}
