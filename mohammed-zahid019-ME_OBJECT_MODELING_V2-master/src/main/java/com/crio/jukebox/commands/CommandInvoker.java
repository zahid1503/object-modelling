package com.crio.jukebox.commands;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.crio.jukebox.exceptions.CommandNotPresentException;


public class CommandInvoker {


    private static final Map<String, ICommand> commandMap = new HashMap<>();


    public void registry(String commandName, ICommand commmand) {
        commandMap.put(commandName, commmand);
    }


    public void executeCommand(String commandName, List<String> data) {
        ICommand command = commandMap.get(commandName);

        if (command == null) {
            throw new CommandNotPresentException(commandName + " is not valid");
        }
        command.execute(data);
    }

}

