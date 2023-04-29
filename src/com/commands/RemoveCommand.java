package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Hint;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;
import com.study_group.StudyGroup;

/** Remove an element by its name */
public class RemoveCommand extends Command {
    public RemoveCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (listener.groups.isEmpty()) {
            return Message.createMessage("The collection is already empty!\n", new CollectionException());
        }

        if (data.args.length == 1) {
            return Hint.nameHint(data.args.length, listener);
        }

        StudyGroup to_remove = StudyGroup.findByName(listener.groups, data.args[1]);
        if (to_remove != null) {
            listener.groups.remove(to_remove);
            SaveCommand.isSaved = false;
            return Message.createMessage("The group has been removed");
        } else {
            Message message = Message.createMessage("There is no such a group!\n", new CollectionException());
            message.addMessage(Hint.nameHint(2, listener).text);
            return message;
        }
    }
}
