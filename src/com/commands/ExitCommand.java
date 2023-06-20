package com.commands;

import com.auxiliary.Message;
import com.main.Data;
import com.main.Listener;

public class ExitCommand{

    public void execute(Data data, Listener listener) throws Exception {
        System.exit(0);
    }
}
