package com.commands;

import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;
import com.study_group.StudyGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.ObjectUtils.max;

public class InfoCommand extends Command{

    public InfoCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        LocalDateTime last_update = LocalDateTime.MIN;
        for (StudyGroup group : listener.groups) {
            last_update = max(last_update, group.getCreationDate());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        Message message = Message.createMessage("Collection name: Study Group, number of elements: " + listener.groups.size());
        assert last_update != null;
        if (!listener.groups.isEmpty())
            message.addMessage("Last update: " + last_update.format(formatter) + "\n");
        return message;
    }
}
