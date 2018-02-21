package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;
import org.primefaces.model.SortOrder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentDsDbDaoLazyImpl implements StudentDao {

    private Connection getConnection() {
        try{
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/StudentDB");
            return ds.getConnection();

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Student> getStudentList() {
        System.out.println(this.getClass().toString() + ": getStudentList:");
        List<Student> studentDataModel = new ArrayList<>();
        String sqlStatement = "SELECT * FROM students;";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sqlStatement);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()) {

                studentDataModel.add(new Student(rs.getInt(1), rs.getString(2),
                        rs.getString(3), DateStringConverter.stringToDate(rs.getString(4))));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return studentDataModel;
    }


    public List<Student> getStudentList(int first, int pageSize, String sortField, SortOrder sortOrder) {
        System.out.println(this.getClass().toString() + ": getStudentList(String sqlStmt):");

        List<Student> studentDataModel = new ArrayList<>();
        String sortOrderLoc = "";
        String sortFieldLoc = "";
        int totRowCont = 0;

        switch (sortOrder) {
            case ASCENDING:
                sortOrderLoc = "ASC";
                break;
            case DESCENDING:
                sortOrderLoc = "DESC";
                break;
            case UNSORTED:
                sortOrderLoc = "";
        }

        if (sortField == null) {
            sortField = "id";
        }

        switch (sortField) {
            case "id":
                sortFieldLoc = "id";
                break;
            case "firstName":
                sortFieldLoc = "name";
                break;
            case "lastName":
                sortFieldLoc = "surname";
                break;
            case "birthDateAsStr":
                sortFieldLoc = "birthdate";
        }

        String sqlStatement = "SELECT * FROM students ORDER BY " + sortFieldLoc + " " + sortOrderLoc +
                " LIMIT " + pageSize + " OFFSET " + new Integer(first).toString() + ";";

        System.out.println(this.getClass().toString() + ": load: sqlStatement :" + sqlStatement);
        //this.getClass().toString().split("[.]").

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sqlStatement);
             ResultSet rs = ps.executeQuery()){

            while(rs.next()) {

                studentDataModel.add(new Student(rs.getInt(1), rs.getString(2),
                        rs.getString(3), DateStringConverter.stringToDate(rs.getString(4))));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return studentDataModel;
    }

    public int getStudentCount(){
        int totStudentCount = 0;
        String sqlStatement = "SELECT COUNT(id) FROM students;";

        try (Connection con = getConnection();
             PreparedStatement ps =  con.prepareStatement(sqlStatement);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totStudentCount = rs.getInt(1);
                //System.out.println("numberOfRows= " + totRowCont);
            } else {
                totStudentCount = 0;
                //System.out.println("error: could not get the record counts");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totStudentCount;
    }


    @Override
    public void removeStudent(Student studentIn) {
        System.out.println(this.getClass().toString() + ": removeStudent:");
        String sqlStatement = "DELETE FROM students WHERE id='" + studentIn.getId() + "';";
        System.out.println("DELETE query:" + sqlStatement);

        try(Connection con = getConnection();
            PreparedStatement ps =  con.prepareCall(sqlStatement);){
            boolean rs = ps.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addStudent(Student studentIn) {
        System.out.println(this.getClass().toString() + ": addStudent:");
        String sqlStatement = "INSERT INTO students (id, name, surname, birthdate) " +
                              "VALUES ('" + studentIn.getId() + "', '" + studentIn.getFirstName() + "', '" +
                              studentIn.getLastName() + "', '" +
                              DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +"' );";
        System.out.println("INSERT query:" + sqlStatement);

        try(Connection con = getConnection();
            PreparedStatement ps =  con.prepareCall(sqlStatement);){
                boolean rs = ps.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void editStudent(Student studentIn) {
        System.out.println(this.getClass().toString() + ": editStudent:");
        String sqlStatement = "UPDATE students SET id =" + studentIn.getId() + ", name = '" + studentIn.getFirstName() +
                "', surname = '" + studentIn.getLastName() +
                "', birthdate = '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +
                "' WHERE id=" + studentIn.getId() +";";
        System.out.println("UPDATE query:" + sqlStatement);

        try(Connection con = getConnection();
            PreparedStatement ps =  con.prepareCall(sqlStatement);){
                boolean rs = ps.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
