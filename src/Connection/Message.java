package Connection;

import java.io.Serializable;
import java.util.Set;

public class Message implements Serializable {
    private MessageType typeMessage; // тип сообщения
    private String textMessage; // текст сообщения
    private Set<String> listUsers; // множество имен уже подключившихся пользователей

    // Класс Message представляет сообщение, которое передается между клиентом и сервером.

    // Конструкторы класса Message используются для создания сообщений с разными типами и содержимым.

    // Конструктор с параметрами типа сообщения и текста сообщения создает сообщение, которое содержит
    // указанный тип и текст, а список пользователей остается пустым (null).

    // Конструктор с параметрами типа сообщения и множества имен пользователей создает сообщение,
    // которое содержит указанный тип и список пользователей, а текст сообщения остается пустым (null).

    // Конструктор с параметром типа сообщения создает сообщение только с указанным типом,
    // без текста сообщения и списка пользователей.

    // Методы геттеров используются для получения значений полей сообщения.

    public Message(MessageType typeMessage, String textMessage) {
        this.textMessage = textMessage;
        this.typeMessage = typeMessage;
        this.listUsers = null;
    }

    public Message(MessageType typeMessage, Set<String> listUsers) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = listUsers;
    }

    public Message(MessageType typeMessage) {
        this.typeMessage = typeMessage;
        this.textMessage = null;
        this.listUsers = null;
    }

    public MessageType getTypeMessage() {
        return typeMessage;
    }

    public Set<String> getListUsers() {
        return listUsers;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
