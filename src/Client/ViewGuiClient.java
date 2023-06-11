package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class ViewGuiClient {
    private final Client client;
    private JFrame frame = new JFrame("Чат");
    private JTextArea messages = new JTextArea(30, 20);
    private JTextArea users = new JTextArea(30, 15);
    private JPanel panel = new JPanel();
    private JTextField textField = new JTextField(40);
    private JButton buttonDisable = new JButton("Отключиться");
    private JButton buttonConnect = new JButton("Подключиться");
    private JLabel loggedInUserLabel = new JLabel();

    public ViewGuiClient(Client client) {
        this.client = client;
    }

    // Метод, инициализирующий графический интерфейс клиентского приложения
    protected void initFrameClient() {
        // Настройка компонентов GUI
        messages.setEditable(false);
        users.setEditable(false);
        frame.add(new JScrollPane(messages), BorderLayout.CENTER);
        frame.add(new JScrollPane(users), BorderLayout.EAST);
        panel.add(textField);
        panel.add(buttonConnect);
        panel.add(buttonDisable);
        panel.add(loggedInUserLabel);
        loggedInUserLabel.setText("Пользователь: ");
        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // Отображение окна по центру экрана
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    // Метод для добавления сообщения в текстовую область
    protected void addMessage(String text) {
        messages.append(text);
    }

    // Метод обновляет список имен подключившихся пользователей
    protected void refreshListUsers(Set<String> listUsers) {
        users.setText("");
        if (client.isConnect()) {
            StringBuilder text = new StringBuilder("Список пользователей:\n");
            for (String user : listUsers) {
                text.append(user).append("\n");
            }
            users.append(text.toString());
        }
    }


    // Отображает окно для ввода всех необходимых данных: адреса сервера, порта и имени пользователя
    protected void showInputDialog() {
        // Запрос адреса сервера
        String serverAddress = JOptionPane.showInputDialog(frame, "Введите адрес сервера:", "Ввод данных", JOptionPane.QUESTION_MESSAGE);
        if (serverAddress == null) {
            // Окно было закрыто или отменено
            return;
        }

        // Запрос порта сервера
        int port = -1;
        boolean validPort = false;
        while (!validPort) {
            String portString = JOptionPane.showInputDialog(frame, "Введите порт сервера:", "Ввод данных", JOptionPane.QUESTION_MESSAGE);
            if (portString == null) {
                // Окно было закрыто или отменено
                return;
            }
            try {
                port = Integer.parseInt(portString.trim());
                validPort = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Введен некорректный порт сервера. Попробуйте еще раз.", "Ошибка ввода порта сервера", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Запрос имени пользователя
        String username = JOptionPane.showInputDialog(frame, "Введите имя пользователя:", "Ввод данных", JOptionPane.QUESTION_MESSAGE);
        if (username == null) {
            // Окно было закрыто или отменено
            return;
        }


    }

    // Отображает окно ошибки с заданным текстом
    protected void showErrorDialog(String text) {
        JOptionPane.showMessageDialog(frame, text, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
