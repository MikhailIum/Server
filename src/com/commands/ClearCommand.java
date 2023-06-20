package com.commands;


import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;

import java.util.LinkedList;

/** Removes all the groups from the collection */
public class ClearCommand extends Command {
    public ClearCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        listener.st.execute("DELETE FROM studygroups");
        listener.groups = new LinkedList<>();
        SaveCommand.isSaved = false;
        return Message.createMessage("Collection is successfully cleared!");
    }
}