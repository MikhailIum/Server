package com.commands;

import com.auxiliary.TextColor;
import com.main.Listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class SaveCommand {
    /**
     * Saves the collection to the file
     */


    public static void execute(Listener listener) throws Exception {
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(listener.FILE_PATH));

        listener.groups.stream()
                .forEach(
                        group -> {
                            try {
                                out.write(group.getParams());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

        isSaved = true;
        out.close();
    }

    public static boolean isSaved = true;
}
