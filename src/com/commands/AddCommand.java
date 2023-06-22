package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;

import java.sql.ResultSet;
import java.util.UUID;

public class AddCommand extends Command {
    public AddCommand(String add, String addNewGroup) {
        super(add, addNewGroup);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        ResultSet rs = listener.st.executeQuery("SELECT * FROM gen_random_uuid ()");
        rs.next();
        data.group.setId(UUID.fromString(rs.getString(1)));
        data.group.setUser(data.args[0]);
        String values = "('" + data.group.getCreationDate() + "', '" + data.group.getId() + "', " +
                data.group.getParams() + ")";
        int isAddedToDB = listener.st.executeUpdate("INSERT INTO studygroups VALUES " + values);

        if (isAddedToDB == 1) {
            listener.groups.add(data.group);
            SaveCommand.isSaved = false;
            return Message.createMessage("New group has been added");
        } else {
            return Message.createMessage("New group hasn't been added, check the parameters", new CollectionException());
        }
    }


}
