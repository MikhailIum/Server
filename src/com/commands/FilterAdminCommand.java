package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;
import com.study_group.*;
import java.util.ArrayList;
import java.util.Scanner;import java.util.stream.Collectors;

/** Shows admins whose names lexicographically greater than given */
public class FilterAdminCommand extends Command {
    public FilterAdminCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        String name = data.args[0];

        Person.changeTabs();
        Location.changeTabs();

        String text = listener.groups.stream()
                .map(StudyGroup::getAdmin)
                .filter(admin -> name.compareTo(admin.getName()) < 0)
                .map(Person::toString)
                .collect(Collectors.joining());

        Person.changeTabs();
        Location.changeTabs();

        if (text.isEmpty()) {
            return Message.createMessage("Nothing was found\n", new CollectionException());
        }

        return Message.createMessage(text);
    }
}
