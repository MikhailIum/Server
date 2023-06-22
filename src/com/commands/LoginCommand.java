package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;

import javax.sql.RowSet;
import java.sql.ResultSet;

public class LoginCommand extends Command{
    public LoginCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        ResultSet rs = listener.st.executeQuery("SELECT * FROM users WHERE username = '" + data.args[0] + "'");
        if (rs.next()){
            return null;
        } else return Message.createMessage("You are not signed-up! Create a new user (" + data.args[0] + "):");
    }
}
