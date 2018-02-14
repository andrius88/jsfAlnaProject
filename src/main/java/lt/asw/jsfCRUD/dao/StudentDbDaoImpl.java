package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;

public class StudentDbDaoImpl implements StudentDao {

    private Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_schema?autoReconnect=true&useSSL=false","root","sysop");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getStudentList() {
        List<Student> studentList = new ArrayList<>();
        //Date birthDate;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            ResultSet rs = getConnection().createStatement().executeQuery("select * from students;");
            while(rs.next()) {
                //System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
                //birthDate = DateStringConverter.stringToDate(rs.getString(4));

                studentList.add(new Student(rs.getInt(1), rs.getString(2),
                                            rs.getString(3),  DateStringConverter.stringToDate(rs.getString(4)) ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return studentList;
    }

    @Override
    public void removeStudent(Student studentIn) {
        try{
            boolean rs = getConnection().createStatement().execute("delete from students where id='" + studentIn.getId() + "';");
            System.out.println("DELETE query: DELETE FROM students WHERE id='" + studentIn.getId() + "';");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addStudent(Student studentIn) {
        try{
            System.out.println("insert into students (id, name, surname, birthdate ) values ('" +
                    studentIn.getId() + "', '" + studentIn.getFirstName() + "', '" +
                    studentIn.getLastName() + "', '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate()) + "');");

            boolean rs = getConnection().createStatement().execute("insert into students (id, name, surname, birthdate) " +
                    "values ('" + studentIn.getId() + "', '" + studentIn.getFirstName() + "', '" +
                    studentIn.getLastName() + "', '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate())  +"' );" );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void editStudent(Student studentIn) {
        try{
            System.out.println("UPDATE students SET id =" +
                    studentIn.getId() + ", name = '" + studentIn.getFirstName() + "', surname = '" +
                    studentIn.getLastName() + "', birthdate = '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate()) + "' WHERE id=" + studentIn.getId() +";");

            int rs = getConnection().createStatement().executeUpdate("UPDATE students SET id =" +
                    studentIn.getId() + ", name = '" + studentIn.getFirstName() + "', surname = '" +
                    studentIn.getLastName() + "', birthdate = '" + DateStringConverter.dateToString(studentIn.getBirthDateAsDate()) + "' WHERE id=" + studentIn.getId() +";");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
