package org.art.java_core.design.patterns.mediator;

import java.util.ArrayList;
import java.util.List;

public class MediatorPatt {
    public static void main(String[] args) {
        TextChat chat = new TextChat();

        User admin = new Admin(chat);
        User u1 = new SimpleUser(chat);
        User u2 = new SimpleUser(chat);

        chat.setAdmin(admin);
        chat.addUser(u1);
        chat.addUser(u2);

        u1.sendMessage("Hello, I am user");
        admin.sendMessage("Admin went into chat");
    }
}

interface User {
    void sendMessage(String message);
    void getMessage(String message);
}

class Admin implements User {
    Chat chat;
    public Admin(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void sendMessage(String message) {
        chat.sendMessage(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("Admin gets the message: " + message);
    }
}

class SimpleUser implements User {
    Chat chat;
    public SimpleUser(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void sendMessage(String message) {
        chat.sendMessage(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("User gets the message: " + message);
    }
}

interface Chat {
    void sendMessage(String message, User user);
}

class TextChat implements Chat {

    User admin;
    List<User> users = new ArrayList<>();

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String message, User user) {
        for (User u : users) {
            u.getMessage(message);
        }
        admin.getMessage(message);
    }
}