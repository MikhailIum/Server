package com.commands;

import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;

public class AddCommand extends Command {
    public AddCommand(String add, String addNewGroup) {
        super(add, addNewGroup);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        listener.groups.add(data.group);
        SaveCommand.isSaved = false;
        return Message.createMessage("New group has been added");
    }


}
