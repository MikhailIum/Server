package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;
import com.study_group.StudyGroup;

import java.util.stream.Collectors;

/**
 * shows the whole collection
 */
public class ShowCommand extends Command {
    public ShowCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (listener.groups.isEmpty())
            return Message.createMessage("Collection is empty!!\n", new CollectionException());
        else {
            return Message.createMessage(listener.groups.stream().map(StudyGroup::toString).collect(Collectors.joining()));
        }
    }
}