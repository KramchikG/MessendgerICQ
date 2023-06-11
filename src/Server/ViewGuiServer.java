package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewGuiServer {
    private JFrame frame = new JFrame("Запуск сервера"); // Создание окна приложения
    private JTextArea dialogWindow = new JTextArea(10, 40); // Создание текстового поля для вывода сообщений
    private JButton buttonStartServer = new JButton("Запустить сервер"); // Кнопка "Запустить сервер"
    private JButton buttonStopServer = new JButton("Остановить сервер"); // Кнопка "Остановить сервер"
    private JPanel panelButtons = new JPanel(); // Панель с кнопками
    private final Server server; // Ссылка на сервер

    public ViewGuiServer(Server server) {
        this.server = server;
    }

    // Метод инициализации графического интерфейса приложения сервера
    protected void initFrameServer() {
        dialogWindow.setEditable(false);
        dialogWindow.setLineWrap(true); // Автоматический перенос строки в JTextArea
        dialogWindow.setBackground(Color.BLACK);
        dialogWindow.setForeground(Color.WHITE);
        frame.add(new JScrollPane(dialogWindow), BorderLayout.CENTER); // Добавление JTextArea в окно приложения

        // Создание и добавление панели с кнопками разговоров
        JPanel panelConversationList = new JPanel();
        JButton buttonConversation1 = new JButton("Разговор 1");
        JButton buttonConversation2 = new JButton("Разговор 2");
        JButton buttonConversation3 = new JButton("Разговор 3");
        panelConversationList.setBackground(Color.DARK_GRAY);
        panelConversationList.add(buttonConversation1);
        panelConversationList.add(buttonConversation2);
        panelConversationList.add(buttonConversation3);
        frame.add(panelConversationList, BorderLayout.NORTH); // Добавление панели разговоров в верхнюю часть окна

        panelButtons.setBackground(Color.DARK_GRAY);
        panelButtons.add(buttonStartServer);
        panelButtons.add(buttonStopServer);
        frame.add(panelButtons, BorderLayout.SOUTH); // Добавление панели с кнопками в нижнюю часть окна
        frame.pack();
        frame.setLocationRelativeTo(null); // При запуске отображает окно по центру экрана
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Класс обработки события при закрытии окна приложения Сервера
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
                System.exit(0);
            }
        });
        frame.setVisible(true);

        buttonStartServer.setBackground(Color.GREEN);
        buttonStopServer.setBackground(Color.RED);
        buttonConversation1.setBackground(Color.GRAY);
        buttonConversation2.setBackground(Color.GRAY);
        buttonConversation3.setBackground(Color.GRAY);

        buttonStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = getPortFromOptionPane();
                server.startServer(port);
            }
        });
        buttonStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopServer();
            }
        });

        buttonConversation1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действия при выборе разговора 1
                server.stopServer();
            }
        });

        buttonConversation2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действия при выборе разговора 2
                // ...
            }
        });

        buttonConversation3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действия при выборе разговора 3
                // ...
            }
        });
    }

    // Метод, который добавляет в текстовое окно новое сообщение
    public void refreshDialogWindowServer(String serviceMessage) {
        dialogWindow.append(serviceMessage);
    }

    // Метод вызывающий диалоговое окно для ввода порта сервера
    protected int getPortFromOptionPane() {
        while (true) {
            String port = JOptionPane.showInputDialog(
                    frame, "Введите порт сервера:",
                    "Ввод порта сервера",
                    JOptionPane.QUESTION_MESSAGE
            );
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        frame, "Введен некорректный порт сервера. Попробуйте еще раз.",
                        "Ошибка ввода порта сервера", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
