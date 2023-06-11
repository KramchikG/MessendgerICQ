package Connection;

import java.io.*;
import java.net.Socket;

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    /**
     * Конструктор класса Connection.
     *
     * @param socket сокет для установленного соединения
     * @throws IOException если возникла ошибка при создании потоков ввода-вывода
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }



    /**
     * Закрывает потоки чтения, записи и сокет.
     *
     * @throws IOException если возникла ошибка при закрытии потоков или сокета
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}