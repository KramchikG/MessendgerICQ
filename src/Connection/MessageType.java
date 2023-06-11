package Connection;

public enum MessageType {
    REQUEST_NAME_USER,  // Запрос имени пользователя
    TEXT_MESSAGE,  // Текстовое сообщение
    NAME_ACCEPTED,  // Принятое имя пользователя
    USER_NAME,  // Имя пользователя
    NAME_USED,  // Имя пользователя уже используется
    USER_ADDED,  // Пользователь добавлен
    DISABLE_USER,  // Отключение пользователя
    REMOVED_USER;  // Пользователь удален
}