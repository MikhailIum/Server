package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;

import java.util.Objects;

/** Removes first group from the collection(lexicographically first) */
public class RemoveFirstCommand extends Command {
    public RemoveFirstCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (!listener.groups.isEmpty()) {
            if (!Objects.equals(data.login, listener.groups.getFirst().getUser())){
                return Message.createMessage("You do not have access to the groups you have not created", new CollectionException());
            }
            listener.st.execute("DELETE FROM studygroups WHERE name = '" + listener.groups.getFirst().getName() + "'");
            listener.groups.removeFirst();
            SaveCommand.isSaved = false;
            return Message.createMessage("First element has been removed");
        } else {
            return Message.createMessage("Collection is empty!!\n", new CollectionException());
        }
    }
}
