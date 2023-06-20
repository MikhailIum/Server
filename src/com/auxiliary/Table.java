package com.auxiliary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Table {

    String name;
    ArrayList<Attribute> attrs = new ArrayList<>();

    public Table(String name){
        this.name = name;
    }

    public void addAttr(String name, String type, String additional_params){
        attrs.add(Attribute.createAttribute(name, type, additional_params));
    }
    public void addAttr(String name, String type){
        attrs.add(Attribute.createAttribute(name, type));
    }

    public void createTable(Statement st) throws SQLException {
        StringBuilder cols = new StringBuilder();
        for (Attribute attr : attrs) {
            cols.append(attr.name).append(" ").append(attr.type).append(" ").append(attr.additionalParams).append(",\n");
        }

        String request = "CREATE TABLE IF NOT EXISTS " + name + "(" + cols.substring(0, cols.length()-2) + ")";

        st.execute(request);
    }

    public static void createStudyGroupTable(Statement st) throws SQLException {
        Table studyGroupsTable = new Table("studyGroups");
        studyGroupsTable.addAttr("creationDate", "TIMESTAMP", "NOT NULL");
        studyGroupsTable.addAttr("id", "UUID", "UNIQUE NOT NULL");
        studyGroupsTable.addAttr("name", "TEXT", "NOT NULL CHECK (name != '')");
        studyGroupsTable.addAttr("coord_x", "FLOAT", "NOT NULL CHECK (coord_x <= 301)");
        studyGroupsTable.addAttr("coord_y", "BIGINT", "CHECK (coord_y > -730)");
        studyGroupsTable.addAttr("studentsCount", "INTEGER", "CHECK (studentsCount > 0)");
        studyGroupsTable.addAttr("expelledStudents", "INTEGER", "CHECK (expelledStudents > 0)");
        studyGroupsTable.addAttr("shouldBeExpelled", "BIGINT", "CHECK (shouldBeExpelled > 0)");
        studyGroupsTable.addAttr("semester", "TEXT", "CHECK (semester = ANY('{1, 2, 4, 6, 7}'::text[]))");
        studyGroupsTable.addAttr("admin_name", "TEXT", "NOT NULL CHECK (admin_name != '')");
        studyGroupsTable.addAttr("admin_birthday", "DATE", "NOT NULL");
        studyGroupsTable.addAttr("admin_hairColor", "TEXT", "CHECK (admin_hairColor = ANY('{black, blue, brown}'::text[]))");
        studyGroupsTable.addAttr("nationality", "TEXT", "CHECK (nationality = ANY('{UK, USA, France, Thailand}'::text[]))");
        studyGroupsTable.addAttr("admin_location_name", "TEXT");
        studyGroupsTable.addAttr("admin_location_x", "REAL");
        studyGroupsTable.addAttr("admin_location_y", "INTEGER");
        studyGroupsTable.addAttr("admin_location_z", "BIGINT");

        studyGroupsTable.createTable(st);
    }


}
