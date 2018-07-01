package org.art.java_core.design.patterns.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Mediator pattern (from GoF) - code example.
 * Simple chat simulation.
 */
public class Mediator {

    public static void main(String[] args) {

        TextChat chat = new TextChat();

        IChatActor admin = new ChatAdmin("Admin", chat);
        IChatActor user1 = new ChatUser("Tom", chat);
        IChatActor user2 = new ChatUser("Mark", chat);

        chat.setAdmin(admin);
        chat.addUser(user1);
        chat.addUser(user2);

        user1.sendMessage("Hello, I'd like to join you!");
        admin.sendMessage("Hey! No problems!");
    }
}

interface IChatActor {

    void sendMessage(String message);

    void getMessage(String message);
}

class ChatAdmin implements IChatActor {

    private IChat chat;

    private String identifier;

    public ChatAdmin(String identifier, IChat chat) {
        this.identifier = identifier;
        this.chat = chat;
    }

    @Override
    public void sendMessage(String message) {
        System.out.printf("%s sends the message: %s%n", identifier, message);
        chat.sendMessage(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println(this.identifier + " gets the message: " + message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatAdmin)) return false;

        ChatAdmin chatAdmin = (ChatAdmin) o;

        return identifier != null ? identifier.equals(chatAdmin.identifier) : chatAdmin.identifier == null;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }
}

class ChatUser implements IChatActor {

    private IChat chat;

    private String identifier;

    public ChatUser(String identifier, IChat chat) {
        this.identifier = identifier;
        this.chat = chat;
    }

    @Override
    public void sendMessage(String message) {
        System.out.printf("%s sends the message: %s%n", identifier, message);
        chat.sendMessage(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println(this.identifier + " gets the message: " + message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatUser)) return false;

        ChatUser chatUser = (ChatUser) o;

        return identifier != null ? identifier.equals(chatUser.identifier) : chatUser.identifier == null;
    }

    @Override
    public int hashCode() {
        return identifier != null ? identifier.hashCode() : 0;
    }
}

interface IChat {

    void sendMessage(String message, IChatActor user);
}

class TextChat implements IChat {

    private IChatActor admin;

    private List<IChatActor> users = new ArrayList<>();

    public void setAdmin(IChatActor admin) {
        this.admin = admin;
    }

    public void addUser(IChatActor user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String message, IChatActor user) {
        Objects.requireNonNull(user);
        for (IChatActor u : users) {
            if (!user.equals(u)){
                u.getMessage(message);
            }
        }
        if (admin != null && !admin.equals(user)) {
            admin.getMessage(message);
        }
    }
}