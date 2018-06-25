package org.art.java_core.design.patterns.command;

import java.util.LinkedList;

/**
 * Command pattern (from GoF) - simple code example.
 * Receiver object manipulations.
 */
public class Command {

    public static void main(String[] args) {

        CommandReceiver receiver = new CommandReceiver();

        AbstractCommand start = new StartCommand(receiver);
        AbstractCommand stop = new StopCommand(receiver);
        AbstractCommand reset = new ResetCommand(receiver);

        Invoker invoker = new Invoker();

        invoker.invokeCommand(start);
        invoker.invokeCommand(stop);
        invoker.invokeCommand(reset);

        System.out.println(reset.getHistory());
    }
}


abstract class AbstractCommand {

    private static final CommandManager COMMAND_MANAGER = new CommandManager();

    abstract void execute();

    LinkedList<AbstractCommand> getHistory() {
        return COMMAND_MANAGER.getHistory();
    }

    void addToHistory(AbstractCommand command) {
        COMMAND_MANAGER.addToHistory(command);
    }

    public String toString() {
        String commandClassName = getClass().getSimpleName();
        return "Command: " + commandClassName.substring(0, commandClassName.indexOf("Command"));
    }
}

//Concrete 'Start' command
class StartCommand extends AbstractCommand {

    private CommandReceiver commandReceiver;

    StartCommand(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void execute() {
        if (commandReceiver.start()) {
            addToHistory(this);
        }
    }
}

//Concrete 'Stop' command
class StopCommand extends AbstractCommand {

    private CommandReceiver commandReceiver;

    StopCommand(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void execute() {
        if (commandReceiver.stop()) {
            addToHistory(this);
        }
    }
}

//Concrete 'Reset' command
class ResetCommand extends AbstractCommand {

    private CommandReceiver commandReceiver;

    ResetCommand(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void execute() {
        if (commandReceiver.reset()) {
            addToHistory(this);
        }
    }
}

//Command Manager (saves command history)
class CommandManager {

    private int maxHistoryLength = 100;

    private LinkedList<AbstractCommand> history = new LinkedList<>();

    void addToHistory(AbstractCommand command) {
        history.addFirst(command);
        if (history.size() > maxHistoryLength) {
            history.removeLast();
        }
    }

    LinkedList<AbstractCommand> getHistory() {
        return new LinkedList<>(history);
    }
}

//Command processor (receiver)
class CommandReceiver {

    boolean start() {
        try {
            System.out.println("Receiver is running...");
            // Command processing ...
            return true;
        } catch (Exception e) {
            // Error logging ...
            return false;
        }
    }

    boolean stop() {
        try {
            System.out.println("Receiver was stopped!");
            // Command processing ...
            return true;
        } catch (Exception e) {
            // Error logging ...
            return false;
        }
    }

    boolean reset() {
        try {
            System.out.println("Receiver reset...");
            // Command processing ...
            return true;
        } catch (Exception e) {
            // Error logging ...
            return false;
        }
    }
}

//Invoker
class Invoker {

    public void invokeCommand(AbstractCommand command) {
        command.execute();
    }
}
