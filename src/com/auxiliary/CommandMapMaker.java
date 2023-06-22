package com.auxiliary;

import com.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates a HashMap of possible commands(key = command's name, value = instance of command classes
 * which extend Command)
 */
public class CommandMapMaker {

  /**
   * Making default set of commands
   *
   * @return HashMap(key = command ' s name, value = instance of command classes which extend
   *     Command)
   */
  public static Map<String, Command> makeDefault() {
    Map<String, Command> commands = new HashMap<>();

    commands.put("password", new PasswordCommand("password", null));
    commands.put("login", new LoginCommand("login", null));
    commands.put("info", new InfoCommand("info", "get info about the collection"));
    commands.put("show", new ShowCommand("show", "show all the groups"));
    commands.put("add", new AddCommand("add", "add new group"));
    commands.put("update", new UpdateCommand("update (name)", "update group by its name"));
    commands.put("remove", new RemoveCommand("remove (name)", "remove group by its name"));
    commands.put("clear", new ClearCommand("clear", "remove all the groups"));
    commands.put("remove_first", new RemoveFirstCommand("remove_first", "remove the first group"));
    commands.put("head", new HeadCommand("head", "show the first group"));
    commands.put("update_element", new UpdateElementCommand("update_element", ""));
    commands.put(
        "add_if_min",
        new AddIfMinCommand("add_if_min", "add a group if its name is lexicographically lowest"));
    commands.put(
        "filter_greater_than_group_admin",
        new FilterAdminCommand(
            "filter_greater_than_group_admin",
            "show admins whose name is lexicographically greater than written one"));
    commands.put(
        "filter_contains_name",
        new FilterNameCommand(
            "filter_contains_name (name)", "show groups whose names contain the specified string"));
    commands.put(
        "print_field_ascending_group_admin",
        new ShowAdminsCommand(
            "print_field_ascending_group_admin", "show group admins of all the groups"));
    return commands;
  }
}
