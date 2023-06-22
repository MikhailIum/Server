package com.commands;

import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class PasswordCommand extends Command{
    public PasswordCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        String login = data.args[0];
        String password = data.args[1];

        String pepper = "U&@0g*#2(N^";
        MessageDigest md = MessageDigest.getInstance("SHA-256");


        ResultSet rs = listener.st.executeQuery("SELECT password, addition FROM users WHERE username = '" + login + "'");
        if (rs.next()){
            String salt = rs.getString(2);
            byte[] hash = md.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
            String hashString = new String(hash, StandardCharsets.UTF_8);

            if (Objects.equals(rs.getString(1), hashString)){
                return null;
            } else {
                return Message.createMessage("Password is wrong!");
            }
        } else {
            String salt = UUID.randomUUID().toString();
            byte[] hash = md.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
            String hashString = new String(hash, StandardCharsets.UTF_8);

            listener.st.execute("INSERT INTO users VALUES ('" + login + "', '" + hashString + "','" + salt +"')");
            return null;
        }
    }
}
