package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class LazySudentDataModel extends LazyDataModel<Student> {

    //private List<Student> datasource;

    private Connection getConnection() {
        try{
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/StudentDB");
            return ds.getConnection();

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    //Constructor
    public LazySudentDataModel() {

//        List<Student> studentList = new ArrayList<>();
//        Connection con = getConnection();
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            stmt = con.createStatement();
//            rs = stmt.executeQuery("select * from students;");
//            while (rs.next()) {
//
//                studentList.add(new Student(rs.getInt(1), rs.getString(2),
//                        rs.getString(3), DateStringConverter.stringToDate(rs.getString(4))));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                rs.close();
//                stmt.close();
//                con.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        this.datasource = studentList;
    }


    @Override
    public List<Student> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

        System.out.println("LazySudentDataModel:load: first :" + first + " pageSize :" + pageSize);

        List<Student> studentList = new ArrayList<>();
        String sortOrderLoc = "";
        String sortFieldLoc = "";
        int totRowCont = 0;

        switch (sortOrder){
            case ASCENDING: sortOrderLoc = "ASC"; break;
            case DESCENDING: sortOrderLoc = "DESC"; break;
            case UNSORTED: sortOrderLoc = "";
        }

        if(sortField == null){ sortField = "id"; }

        switch (sortField){
            case "id": sortFieldLoc = "id"; break;
            case "firstName": sortFieldLoc = "name"; break;
            case "lastName": sortFieldLoc = "surname"; break;
            case "birthDateAsStr": sortFieldLoc = "birthdate";
        }

        String sqlStatement = "SELECT * FROM students ORDER BY " + sortFieldLoc + " " + sortOrderLoc +
                              " LIMIT " + pageSize + " OFFSET " + new Integer(first).toString() + ";";

        System.out.println("LazySudentDataModel:load: sqlStatement :" + sqlStatement);

        try (Connection con = getConnection();
             PreparedStatement ps =  con.prepareStatement(sqlStatement);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                studentList.add(new Student(rs.getInt(1), rs.getString(2),
                        rs.getString(3), DateStringConverter.stringToDate(rs.getString(4))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //this.setRowCount is required to function PF paginator
        sqlStatement = "SELECT COUNT(id) FROM students;";

        try (Connection connx = getConnection();
             PreparedStatement ps =  connx.prepareStatement(sqlStatement);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totRowCont = rs.getInt(1);
                //System.out.println("numberOfRows= " + totRowCont);
            } else {
                //System.out.println("error: could not get the record counts");
            }

        } catch (SQLException e) {
            e.printStackTrace();
       }

        this.setRowCount(totRowCont);
        //System.out.println("dataSize :" + totRowCont);

        return studentList;
    }


    public void removeStudent(Student studentIn) {
        System.out.println("LazySudentDataModel: method-removeStudent:");

        Connection con = getConnection();
        String sqlStatement = "DELETE FROM students WHERE id='" + studentIn.getId() + "';";

        try(Connection conx = con;
            PreparedStatement ps =  conx.prepareCall(sqlStatement);){
            boolean rs = ps.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void addStudent(Student studentIn) {
        Connection con = getConnection();
        String sqlStatement = "INSERT INTO students (id, name, surname, birthdate) VALUES ('" + studentIn.getId() + "', '" +
                               studentIn.getFirstName() + "', '" + studentIn.getLastName() + "', '" +
                               DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +"' );";

        try(Connection conx = con;
            PreparedStatement ps =  conx.prepareCall(sqlStatement);){
                boolean rs = ps.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void editStudent(Student studentIn) {
        Connection con = getConnection();
        String sqlStatement = "UPDATE students SET id =" + studentIn.getId() + ", name = '" + studentIn.getFirstName() +
                              "', surname = '" + studentIn.getLastName() +
                              "', birthdate = '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +
                              "' WHERE id=" + studentIn.getId() +";";

        try(Connection conx = con;
            PreparedStatement ps =  conx.prepareCall(sqlStatement);){
            boolean rs = ps.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
