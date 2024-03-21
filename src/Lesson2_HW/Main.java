package Lesson2_HW;

import Lesson2_HW.client.domain.ClientController;
import Lesson2_HW.client.ui.ClientGUI;
import Lesson2_HW.server.domain.ServerController;
import Lesson2_HW.server.repository.FileStorage;
import Lesson2_HW.server.ui.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(new ServerWindow(), new FileStorage());

        new ClientController(new ClientGUI(), serverController);
        new ClientController(new ClientGUI(), serverController);
    }
}
