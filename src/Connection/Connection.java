package Connection;

import java.io.*;
import java.net.Socket;

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    // Класс Connection представляет сокетное соединение с сервером или клиентом.
    // Он обеспечивает отправку и получение сообщений по сокетному соединению.

    // Конструктор принимает объект Socket и создает объекты ObjectOutputStream и ObjectInputStream
    // для отправки и получения данных по сокетному соединению.
    // Если возникает IOException при создании потоков, он пробрасывается вверх.

    // Метод send отправляет сообщение по сокетному соединению.
    // Он синхронизирован, чтобы избежать конфликтов при одновременной отправке сообщений из разных потоков.
    // Сообщение передается в ObjectOutputStream, который сериализует его и отправляет через сокет.
    // Если возникает IOException при отправке сообщения, он пробрасывается вверх.

    // Метод receive принимает сообщение по сокетному соединению.
    // Он также синхронизирован, чтобы избежать конфликтов при одновременном приеме сообщений из разных потоков.
    // Сообщение считывается из ObjectInputStream и десериализуется.
    // Если возникает IOException или ClassNotFoundException при чтении сообщения, они пробрасываются вверх.

    // Метод close закрывает потоки чтения, записи и сокет.
    // Он вызывается при завершении работы с сокетным соединением или при возникновении ошибки.
    // Если возникает IOException при закрытии потоков или сокета, они пробрасываются вверх.

    public void send(Message message) throws IOException {
        synchronized (this.out) {
            out.writeObject(message);
        }
    }

    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (this.in) {
            Message message = (Message) in.readObject();
            return message;
        }
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
