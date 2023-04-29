package com.main;

import com.auxiliary.CSVReader;
import com.auxiliary.CommandMapMaker;
import com.auxiliary.TextColor;
import com.commands.Command;
import com.commands.SaveCommand;
import com.opencsv.bean.CsvToBeanBuilder;
import com.study_group.StudyGroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Listener {

  public LinkedList<StudyGroup> groups;
  public String FILE_PATH = System.getenv("FILE_PATH").replace("'", "");
  public Map<String, Command> commands;
  List<CSVReader> beans;

  public void start() {
    commands = CommandMapMaker.makeDefault();
    try {
      FileReader fr = new FileReader(FILE_PATH);
      beans = new CsvToBeanBuilder(fr).withType(CSVReader.class).build().parse();
      groups = CSVReader.makeCollection(beans);
    } catch (NullPointerException | IOException ex) {
        System.out.println(FILE_PATH);
      System.out.println(
          TextColor.ANSI_YELLOW + "You should add environment variable as a path to the file:");
      System.out.println(
          "Write \"export FILE_PATH=(path to file)\" before starting" + TextColor.ANSI_RESET);
      System.exit(0);
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
}
