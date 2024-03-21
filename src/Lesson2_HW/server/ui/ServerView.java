package Lesson2_HW.server.ui;

import Lesson2_HW.server.domain.ServerController;

public interface ServerView {
    void showMessage(String message);
    void setServerController(ServerController serverController);
}