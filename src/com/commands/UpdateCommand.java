package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Hint;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;
import com.study_group.StudyGroup;

import java.util.Arrays;
import java.util.Objects;

public class UpdateCommand extends Command {
    public UpdateCommand(String s, String updateGroupByItsName) {
        super(s, updateGroupByItsName);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (listener.groups.isEmpty()) {
            return Message.createMessage("Collection is empty!", new CollectionException());
        }
        if (data.args.length == 1){
            return Hint.nameHint(data.args.length, listener);
        }

        StudyGroup toUpdate = StudyGroup.findByName(listener.groups, data.args[1]);
        if (toUpdate == null) {
            Message message;
            message = Message.createMessage("There is no such a group!", new CollectionException());
            message.addMessage(Hint.nameHint(2, listener).text);
            return message;
        }
        if (Objects.equals(toUpdate.getUser(), data.login)) {
            SaveCommand.isSaved = false;
            listener.st.execute("DELETE FROM studygroups WHERE name = '" + toUpdate.getName() + "'");
            listener.groups.remove(toUpdate);
            return Message.createMessage(toUpdate);
        } else {
            return Message.createMessage("You do not have access to the groups you have not created", new CollectionException());
        }
    }
}
