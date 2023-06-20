package com.commands;

import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;
import com.study_group.StudyGroup;

public class UpdateElementCommand extends Command {
    public UpdateElementCommand(String updateElement, String s) {
        super(updateElement, s);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        listener.commands.get("add").execute(data, listener);
        return Message.createMessage("");
    }
}
