package com.main;

import com.auxiliary.CommandMapMaker;
import com.auxiliary.DBReader;
import com.auxiliary.Table;
import com.commands.Command;
import com.commands.SaveCommand;
import com.study_group.StudyGroup;

import javax.sql.RowSet;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Listener {

  public LinkedList<StudyGroup> groups;
  public Map<String, Command> commands;
  public Statement st;
  private Connection connection;

  public void start() {
    commands = CommandMapMaker.makeDefault();
    try {
        Properties info = new Properties();
        info.load(new FileInputStream("db.cfg"));
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/prog", info);
        st = connection.createStatement();
        Table.createStudyGroupTable(st);
        Table.createUsersTable(st);

        groups = DBReader.makeCollection(st);

    } catch (SQLException e) {
        System.out.println("Failed to connect to the database");
        System.exit(0);
        throw new RuntimeException(e);
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }



  public void executeCommands() throws Exception {
      groups.sort(
          new Comparator<StudyGroup>() {
            @Override
            public int compare(StudyGroup o1, StudyGroup o2) {
              return o1.getName().compareTo(o2.getName());
            }

            @Override
            public boolean equals(Object obj) {
              return obj.equals(this);
            }
          });
      SaveCommand.execute(this);
  }

  public void finish() throws SQLException {
      st.close();
      connection.close();
  }

}
