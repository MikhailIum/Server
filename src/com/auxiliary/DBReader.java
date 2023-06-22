package com.auxiliary;

import com.study_group.*;
import org.postgresql.jdbc.PgResultSet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

/** Reads a csv file and creates a collection using data from the file */
public class DBReader {

  private String name;
  private String x;
  private String y;

  private String creationDate;

  private String studentsCount;

  private String expelledStudents;

  private String shouldBeExpelled;

  private String semesterEnum;

  private String admin_name;

  private String birthday;

  private String hairColor;

  private String nationality;

  private String location_x;

  private String location_y;

  private String location_z;

  private String location_name;

  private UUID id;


  /** Makes a collection using starting file */
  public static LinkedList<StudyGroup> makeCollection(Statement statement) throws IOException, SQLException {
    LinkedList<StudyGroup> groups = new LinkedList<>();
    ResultSet result = statement.executeQuery("SELECT * FROM studygroups");
    while (result.next()){
      var name = DBReader.getColValue(result, 3, String.class);

      var x = DBReader.getColValue(result, 4, Double.class);
      var y = DBReader.getColValue(result, 5, Long.class);

      var studentsCount = DBReader.getColValue(result, 6, Integer.class);
      var expelledStudents = DBReader.getColValue(result, 7, Integer.class);
      var shouldBeExpelled = DBReader.getColValue(result, 8, Long.class);
      var semester = Semester.find(DBReader.getColValue(result, 9, String.class));

      var adminName = DBReader.getColValue(result, 10, String.class);
      var adminBirthday = DBReader.getColValue(result, 11, LocalDate.class);
      var adminHairColor = Color.find(DBReader.getColValue(result, 12, String.class));
      var nationality = Country.find(DBReader.getColValue(result, 13, String.class));

      var location_x = DBReader.getColValue(result, 15, Float.class);
      var location_y = DBReader.getColValue(result, 16, Integer.class);
      var location_z = DBReader.getColValue(result, 17, Long.class);
      var location_name = DBReader.getColValue(result, 14, String.class);
      var adminLocation = new Location(location_x, location_y, location_z, location_name);
      var admin = new Person(adminName, adminBirthday, adminHairColor, nationality, adminLocation);

      var creationDate = DBReader.getColValue(result, 1, LocalDateTime.class);
      var id = DBReader.getColValue(result, 2, UUID.class);

      StudyGroup toAdd = new StudyGroup(name, new Coordinates(x, y), studentsCount, expelledStudents, shouldBeExpelled, semester, admin, creationDate, id);

      var username = DBReader.getColValue(result, 18, String.class);

      toAdd.setUser(username);
      groups.add(toAdd);
    }
    return groups;
  }



  private static <T> T getColValue(ResultSet res, int ind, Class<T> cl) throws SQLException {
    return res.getObject(ind, cl);
  }

}
