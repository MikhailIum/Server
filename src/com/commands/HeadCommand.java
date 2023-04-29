package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;

/** Shows the first element of the collection */
public class HeadCommand extends Command {
    public HeadCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (listener.groups.isEmpty())
            return Message.createMessage("Collection is empty!!\n", new CollectionException());
        else return Message.createMessage(listener.groups.stream().findFirst().get().toString());
    }
}
