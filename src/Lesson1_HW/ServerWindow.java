package Lesson1_HW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final String pathFile = "src\\Lesson1_HW\\log.txt";
    private final JPanel panelTopServer = new JPanel(new GridLayout(1, 2));
    private final JPanel panelCenterServer = new JPanel(new BorderLayout());
    private final JTextArea tfMessageServer = new JTextArea();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private boolean isServerWorking;
    private static final String strStart = "Сервер запущен";
    private static final String strStop = "Сервер остановлен";

    protected String userMessageOnServer(String userMessage) throws IOException {
        writeFile(userMessage);
        printMessageOnServer(userMessage);
        return userMessage;
    }
    protected static ArrayList readFile() throws FileNotFoundException {
        ArrayList arrayList = new ArrayList();
        Scanner scanner = new Scanner(new File(ServerWindow.pathFile));
        while (scanner.hasNextLine()) {
            arrayList.add(scanner.nextLine());
        }
        scanner.close();
        return arrayList;
    }
    private void writeFile(String userMessage) throws IOException {
        ArrayList availableRecords = readFile();
        Path path = Paths.get(pathFile);
        if (availableRecords.size() != 0) {
            Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
        }
        Files.write(path, userMessage.getBytes(), StandardOpenOption.APPEND);
    }
    protected void printMessageOnServer(String userMessage) throws IOException {
        if (tfMessageServer.getText().equals("")) {
            tfMessageServer.setText(userMessage);
        } else tfMessageServer.setText(tfMessageServer.getText() + "\n" + userMessage);
    }
    protected boolean serverCheck() {
        if (isServerWorking) return true;
        else return false;
    }
    protected ServerWindow() {
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = true;
                System.out.println("Server started " + isServerWorking + "\n");

                if (tfMessageServer.getText().equals("")) {
                    tfMessageServer.setText(strStart);
                } else tfMessageServer.setText(tfMessageServer.getText() + "\n" + strStart);
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isServerWorking = false;
                System.out.println("Server stopped " + isServerWorking + "\n");

                if (tfMessageServer.getText().equals("")) {
                    tfMessageServer.setText(strStop);
                } else tfMessageServer.setText(tfMessageServer.getText() + "\n" + strStop);
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);

        panelTopServer.add(btnStart);
        panelTopServer.add(btnStop);
        add(panelTopServer, BorderLayout.SOUTH);

        panelCenterServer.add(tfMessageServer);
        add(panelCenterServer);

        setVisible(true);
    }
}