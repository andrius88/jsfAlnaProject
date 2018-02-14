package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class StudentDsDbDaoImpl implements StudentDao {

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
        List<Student> studentList = new ArrayList<>();
        Connection con = getConnection();

        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from students;");
            while(rs.next()) {

                studentList.add(new Student(rs.getInt(1), rs.getString(2),
                rs.getString(3), DateStringConverter.stringToDate(rs.getString(4))));
            }
            con.close();    // change
        }catch(Exception e){
            e.printStackTrace();
        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
        return studentList;
    }

    @Override
    public void removeStudent(Student studentIn) {
        Connection con = getConnection();

        try{
            boolean rs = con.createStatement().execute("delete from students where id='" + studentIn.getId() + "';");
            System.out.println("DELETE query: DELETE FROM students WHERE id='" + studentIn.getId() + "';");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addStudent(Student studentIn) {
        Connection con = getConnection();

        try{
            System.out.println("insert into students (id, name, surname, birthdate) values ('" +
                    studentIn.getId() + "', '" + studentIn.getFirstName() + "', '" +  studentIn.getLastName() + "');");

            boolean rs = con.createStatement().execute("insert into students (id, name, surname, birthdate) " +
                    "values ('" + studentIn.getId() + "', '" + studentIn.getFirstName() + "', '" +
                                  studentIn.getLastName() + "', '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +"' );" );
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void editStudent(Student studentIn) {
        Connection con = getConnection();
        try{
            System.out.println("UPDATE students SET id =" +
                    studentIn.getId() + ", name = '" + studentIn.getFirstName() + "', surname = '" +
                    studentIn.getLastName() + "', birthdate = '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +
                    "' WHERE id=" + studentIn.getId() +";");

            int rs = con.createStatement().executeUpdate("UPDATE students SET id =" +
                    studentIn.getId() + ", name = '" + studentIn.getFirstName() + "', surname = '" +
                    studentIn.getLastName() + "', birthdate = '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +
                    "' WHERE id=" + studentIn.getId() +";");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
