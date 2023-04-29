package com.commands;

import com.auxiliary.CollectionException;
import com.auxiliary.Message;
import com.auxiliary.TextColor;
import com.main.Data;
import com.main.Listener;
import com.study_group.*;
import org.apache.commons.collections.bidimap.AbstractDualBidiMap;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

/** Shows all admins of the groups */
public class ShowAdminsCommand extends Command {
    public ShowAdminsCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public Message execute(Data data, Listener listener) throws Exception {
        if (listener.groups.isEmpty()){
            return Message.createMessage("Collection is empty!!\n", new CollectionException());
        }
        Person.changeTabs();
        Location.changeTabs();

        Message message = Message.createMessage(
            listener.groups.stream()
                .map(StudyGroup::getAdmin)
                .sorted(
                        new Comparator<Person>() {
                            @Override
                            public int compare(Person o1, Person o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        })
                .map(Person::toString).collect(Collectors.joining()));
        Person.changeTabs();
        Location.changeTabs();

        return message;
    }
}
