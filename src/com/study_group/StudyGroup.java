package com.study_group;

import com.auxiliary.TextColor;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/** The class of objects that are contained in the collection */
public class StudyGroup implements Serializable {

  private static final long serialVersionUID = 3L;
  private UUID id;
  private final LocalDateTime creationDate;
  private final Person groupAdmin;
  private String name;
  private Coordinates coordinates;
  private Integer studentsCount;
  private int expelledStudents;
  private long shouldBeExpelled;
  private Semester semesterEnum;

  private String user;

  public StudyGroup(
      String name,
      Coordinates coordinates,
      Integer studentsCount,
      int expelledStudents,
      long shouldBeExpelled,
      Semester semesterEnum,
      Person groupAdmin,
      LocalDateTime creationDate,
      UUID id) {
    this.name = name;
    this.coordinates = coordinates;
    this.studentsCount = studentsCount;
    this.groupAdmin = groupAdmin;
    this.expelledStudents = expelledStudents;
    this.shouldBeExpelled = shouldBeExpelled;
    this.semesterEnum = semesterEnum;
    this.creationDate = creationDate;
    this.id = id;
  }

  public void setUser(String username){
    this.user = username;
  }

  public String getUser(){
    return this.user;
  }


  public void setId(UUID id){
    this.id = id;
  }

  public UUID getId(){
    return this.id;
  }

  /**
   * Searches through the collection and finds the group by its name
   *
   * @param groups - collection
   * @param name - name of the group user wants to find
   * @return StudyGroup
   */
  public static StudyGroup findByName(List<StudyGroup> groups, String name) {
    return groups.stream().filter(group -> group.getName().equals(name)).findFirst().orElse(null);
  }


  public String getName() {
    return this.name;
  }

  public LocalDateTime getCreationDate() {
    return this.creationDate;
  }

  @Override
  public String toString() {
    String id_str =
        TextColor.ANSI_PURPLE
            + "\n\tid"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + id
            + TextColor.ANSI_RESET;
    String name_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "name"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + name
            + TextColor.ANSI_RESET;
    String coords_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "coordinates"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + coordinates.toString()
            + TextColor.ANSI_RESET;
    String studs_cnt_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "number of students"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + studentsCount
            + TextColor.ANSI_RESET;
    String studs_exp_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "expelled students"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + expelledStudents
            + TextColor.ANSI_RESET;
    String studs_should_exp_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "should be expelled"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + shouldBeExpelled
            + TextColor.ANSI_RESET;
    String sem_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "semester"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_CYAN
            + semesterEnum
            + TextColor.ANSI_RESET;
    String admin_str =
        TextColor.ANSI_PURPLE
            + "\n\t"
            + "admin"
            + TextColor.ANSI_WHITE
            + ": "
            + TextColor.ANSI_RESET
            + "{"
            + groupAdmin.toString()
            + "\n\t}";
    String username =
            TextColor.ANSI_PURPLE
                    + "\n\t"
                    + "username"
                    + TextColor.ANSI_WHITE
                    + ": "
                    + TextColor.ANSI_CYAN
                    + user
                    + TextColor.ANSI_RESET;

    return "\n{" + username
        + id_str
        + name_str
        + coords_str
        + studs_cnt_str
        + studs_exp_str
        + studs_should_exp_str
        + sem_str
        + admin_str
        + "\n}"
        + "\n\n--------------------------------";
  }

  public Person getAdmin() {
    return groupAdmin;
  }

  /**
   * Making a string of all the fields
   *
   * @return String
   */
  public String getParams() {
    return "'" + name
        + "', "
        + coordinates.get_params()
        + ", "
        + studentsCount
        + ", "
        + expelledStudents
        + ", "
        + shouldBeExpelled
        + ", '"
        + Semester.find(semesterEnum)
        + "', "
        + groupAdmin.getParams() + ", '" + user + "'";
  }
}
