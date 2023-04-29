package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Hint;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;

public class AddIfMinCommand extends Command {
    public AddIfMinCommand(String addIfMin, String s) {
        super(addIfMin, s);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (data.args[2].compareTo(listener.groups.getFirst().getName()) > 0) {
            Message message = Message.createMessage("This isn't the lowest name\n", new CollectionException());
            message.addMessage(Hint.nameHint(1, listener).text);
            return message;
        }
        else return Message.createMessage("");
    }
}
