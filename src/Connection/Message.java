package Connection;

import java.io.Serializable;
import java.util.Set;

public class Message implements Serializable {
    private MessageType typeMessage; //тип сообщения
    private String textMessage; //текст сообщения
    private Set<String> listUsers; //множество имен уже подлючившихся пользователей

    /**
     * Конструктор для сообщения с типом и текстом.
     *
     * @param typeMessage  Тип сообщения
     * @param textMessage  Текст сообщения
     */
    public Message(MessageType typeMessage, String textMessage) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
        this.listUsers = null;
    }

    /**
     * Конструктор для сообщения с типом и списком пользователей.
     *
     * @param typeMessage  Тип сообщения
     * @param listUsers    Множество имен уже подключившихся пользователей
     */
    public Message(MessageType typeMessage, Set<String> listUsers) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = listUsers;
    }

    /**
     * Конструктор для сообщения только с типом.
     *
     * @param typeMessage  Тип сообщения
     */
    public Message(MessageType typeMessage) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = null;
    }

    /**
     * Получает тип сообщения.
     *
     * @return Тип сообщения
     */
    public MessageType getTypeMessage() {
        return typeMessage;
    }

    /**
     * Получает список пользователей.
     *
     * @return Множество имен уже подключившихся пользователей
     */
    public Set<String> getListUsers() {
        return listUsers;
    }

    /**
     * Получает текст сообщения.
     *
     * @return Текст сообщения
     */
    public String getTextMessage() {
        return textMessage;
    }

}