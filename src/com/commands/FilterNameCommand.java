package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;
import com.study_group.StudyGroup;
import java.util.ArrayList;

/** Shows those groups which names contain certain substring */
public class FilterNameCommand extends Command {
    public FilterNameCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        String name = data.args[1];
        boolean ifGroupExists = false;
        Message message = Message.createMessage("");
        for (StudyGroup group : listener.groups) {
            if (group.getName().contains(name)) {
                message.addMessage(group.toString());
                ifGroupExists = true;
            }
        }
        if (!ifGroupExists) {
            message = Message.createMessage("There aren't groups with a name contains this string\n", new CollectionException());
        }
        return message;
    }

}

