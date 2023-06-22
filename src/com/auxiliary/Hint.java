package com.auxiliary;


import com.main.Listener;
import com.study_group.StudyGroup;
import java.util.Iterator;
import java.util.stream.Collectors;

/** Gives user a hint about what he\she can print */
public class Hint {

    /**
     * Gives a hint if the user forgot to print an argument
     *
     * @param numberOfArgs - number of arguments putted by the user
     */
    private static Message checkHasAnArgument(int numberOfArgs) {
        if (numberOfArgs == 1) {
            return Message.createMessage("This command should have an argument", new CollectionException());
        }
        return Message.createMessage("");
    }

    /** Gives a hint about names of the groups */
    public static Message nameHint(int numberOfArgs, Listener listener) {
        Message message = checkHasAnArgument(numberOfArgs);
        message.addMessage("Possible names:\n" + "{");

        message.addMessage(listener.groups.stream()
                .limit(listener.groups.size() - 1)
                .map(studyGroup -> studyGroup.getName() + ", ")
                .collect(Collectors.joining()) + listener.groups.get(listener.groups.size()-1).getName() + "\n}");
        return message;
    }
}
