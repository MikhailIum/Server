package com.auxiliary;

import com.opencsv.bean.CsvBindByPosition;
import com.study_group.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/** Reads a csv file and creates a collection using data from the file */
public class CSVReader {

  private static final String FILE_PATH = System.getenv("FILEPATH");

  @CsvBindByPosition(position = 0)
  private String name;

  @CsvBindByPosition(position = 1)
  private String x;

  @CsvBindByPosition(position = 2)
  private String y;

  @CsvBindByPosition(position = 3)
  private String creationDate;

  @CsvBindByPosition(position = 4)
  private String studentsCount;

  @CsvBindByPosition(position = 5)
  private String expelledStudents;

  @CsvBindByPosition(position = 6)
  private String shouldBeExpelled;

  @CsvBindByPosition(position = 7)
  private String semesterEnum;

  @CsvBindByPosition(position = 8)
  private String admin_name;

  @CsvBindByPosition(position = 9)
  private String birthday;

  @CsvBindByPosition(position = 10)
  private String hairColor;

  @CsvBindByPosition(position = 11)
  private String nationality;

  @CsvBindByPosition(position = 12)
  private String location_x;

  @CsvBindByPosition(position = 13)
  private String location_y;

  @CsvBindByPosition(position = 14)
  private String location_z;

  @CsvBindByPosition(position = 15)
  private String location_name;

  @CsvBindByPosition(position = 16)
  private UUID id;

  /** Makes a collection using starting file */
  public static LinkedList<StudyGroup> makeCollection(List<CSVReader> list) throws IOException {
    LinkedList<StudyGroup> groups = new LinkedList<>();
    for (CSVReader r : list) {
      if (r.location_name == null) {
        System.out.println(
            TextColor.ANSI_RED
                + "Line "
                + (list.indexOf(r) + 1)
                + " in file "
                + FILE_PATH
                + "have not enough parameters");
        System.exit(0);
      }

      r.getStudyGroup(r, list, groups);
    }
    return groups;
  }

  /** Called when there is a format mistake in the starting file */
  private void incorrectArgument(String arg, List<CSVReader> list, CSVReader r) {
    System.out.println(
        "\n" + TextColor.ANSI_RED + "Line " + (list.indexOf(r) + 1) + " in file " + FILE_PATH);
    System.out.println("\"" + arg + "\" provided" + TextColor.ANSI_RESET);
  }

  private String getName(
      CSVReader r, List<CSVReader> list, String type, boolean admin, boolean location) {
    String name = type;
//    if (!Check.checkName(type)) {
//      incorrectArgument(type, list, r);
//      if (admin) System.out.println("Admin:");
//      if (location) System.out.println("Admin's location:");
//      name = AddCommand.getGroupName();
//    }
    return name;
  }

  private Coordinates getCoords(CSVReader r, List<CSVReader> list) throws IOException {
    Coordinates cords;
//    String coords = r.x + " " + r.y;
//    if (!Check.checkCoords(coords, " ")) {
//      incorrectArgument("(" + r.x + ", " + r.y + ")", list, r);
//      cords = AddCommand.getCoords();
//    } else
      cords = new Coordinates(Double.parseDouble(r.x), Long.parseLong(r.y));
    return cords;
  }

  private String getStudentsCount(
      CSVReader r, List<CSVReader> list, String studentsType, String typeName) throws IOException {
    String studentsCount = studentsType;
//    if (!Check.checkStudentsCount(studentsType, typeName)) {
//      incorrectArgument(studentsType, list, r);
//      studentsCount = String.valueOf(AddCommand.getStudentsCount(typeName));
//    }
    return studentsCount;
  }

  private Semester getSemester(CSVReader r, List<CSVReader> list) throws IOException {
    Semester sem = Semester.find(r.semesterEnum);
//    if (!Check.checkEnum(r.semesterEnum, 1, 5)) {
//      incorrectArgument(r.semesterEnum, list, r);
//      sem = AddCommand.getSemester();
//    }
    return sem;
  }

  private LocalDate getBirthday(CSVReader r, List<CSVReader> list) throws IOException {
    LocalDate birthday;
//    if (!Check.checkBirthday(r.birthday)) {
//      incorrectArgument(r.birthday, list, r);
//      System.out.println("Admin:");
//      birthday = AddCommand.getBirthday();
//    } else {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
      birthday = LocalDate.parse(r.birthday, formatter);
//    }
    return birthday;
  }

  private Color getColor(CSVReader r, List<CSVReader> list) throws IOException {
    Color color = Color.find(r.hairColor);
//    if (!Check.checkEnum(Color.findNum(r.hairColor), 1, 3)) {
//      incorrectArgument(r.hairColor, list, r);
//      System.out.println("Admin:");
//      color = AddCommand.getHairColor();
//    }
    return color;
  }

  private Country getNation(CSVReader r, List<CSVReader> list) throws IOException {
    Country nation = Country.find(r.nationality);
//    if (!Check.checkEnum(Country.findNum(r.nationality), 1, 4)) {
//      incorrectArgument(r.nationality, list, r);
//      System.out.println("Admin:");
//      nation = AddCommand.getNationality();
//    }
    return nation;
  }

  private String getLocationCoordinate(CSVReader r, List<CSVReader> list, String type, String letter) throws IOException {
//    if (!Check.checkCoordinate(type, letter)) {
//      incorrectArgument(type, list, r);
//      System.out.println("Admin's location");
//      type = AddCommand.getCoordinate(letter);
//    }
    return type;
  }

  private Location getLocation(CSVReader r, List<CSVReader> list, LinkedList<StudyGroup> groups)
      throws IOException {
    String locationName = r.getName(r, list, r.location_name, false, true);
    String locationX = r.getLocationCoordinate(r, list, r.location_x, "x");
    String locationY = r.getLocationCoordinate(r, list, r.location_y, "y");
    String locationZ = r.getLocationCoordinate(r, list, r.location_z, "z");
    return new Location(
        Float.parseFloat(locationX),
        Integer.parseInt(locationY),
        Long.parseLong(locationZ),
        locationName);
  }

  private Person getAdmin(CSVReader r, List<CSVReader> list, LinkedList<StudyGroup> groups)
      throws IOException {
    String adminName = r.getName(r, list, r.admin_name, true, false);
    LocalDate birthday = r.getBirthday(r, list);
    Color color = r.getColor(r, list);
    Country nation = r.getNation(r, list);
    Location loc = getLocation(r, list, groups);
    return new Person(adminName, birthday, color, nation, loc);
  }

  private void getStudyGroup(CSVReader r, List<CSVReader> list, LinkedList<StudyGroup> groups)
      throws IOException {
    String name = r.getName(r, list, r.name, false, false);
    Coordinates cords = r.getCoords(r, list);
    String studentsCount = r.getStudentsCount(r, list, r.studentsCount, "Number of students");
    String expelledStudents =
        r.getStudentsCount(r, list, r.expelledStudents, "Number of expelled students");
    String shouldBeExpelled =
        r.getStudentsCount(
            r, list, r.shouldBeExpelled, "Number of students who should be expelled");
    Semester semester = r.getSemester(r, list);
    Person admin = getAdmin(r, list, groups);
    groups.add(
        new StudyGroup(
            name,
            cords,
            Integer.parseInt(studentsCount),
            Integer.parseInt(expelledStudents),
            Long.parseLong(shouldBeExpelled),
            semester,
            admin,
            LocalDateTime.parse(r.creationDate),
            r.id));
  }
}
