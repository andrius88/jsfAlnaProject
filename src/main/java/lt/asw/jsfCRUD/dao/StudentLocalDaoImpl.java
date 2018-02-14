package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentLocalDaoImpl implements StudentDao {

    private static List<Student> studentList = new ArrayList<>();

    static {
        Student studentIns1 = new Student();
        studentIns1 = new Student();
        studentIns1.setId(1);
        studentIns1.setFirstName("John");
        studentIns1.setLastName("Doe");
        studentIns1.setBirthDateAsDate(DateStringConverter.stringToDate("2001-10-21"));
        studentIns1.setBirthDateAsStr("2001-10-21");
        //studentIns1.setBirthDateAsStr(DateStringConverter.dateToString(studentIns1.getBirthDateAsDate()));
        studentList.add(studentIns1);

        Student studentIns2 = new Student();
        studentIns2.setId(2);
        studentIns2.setFirstName("Peter");
        studentIns2.setLastName("Mark");
        studentIns2.setBirthDateAsDate(DateStringConverter.stringToDate("1999-03-13"));
        studentIns2.setBirthDateAsStr("1999-03-13");
        //studentIns2.setBirthDateAsStr(DateStringConverter.dateToString(studentIns2.getBirthDateAsDate()));
        studentList.add(studentIns2);
    }

    @Override
    public List<Student> getStudentList(){
        return studentList;
    }

    @Override
    public void removeStudent(Student studentIn){
        studentList.remove(studentIn);
    }

    @Override
    public void addStudent(Student studentIn){
        studentList.add(studentIn);
    }

    @Override
    public void editStudent(Student studentIn) {
        for (Student studInFor : studentList) {
            if (studInFor.getId() == studentIn.getId()){
                studInFor.setFirstName(studentIn.getFirstName());
                studInFor.setLastName(studentIn.getLastName());
                studInFor.setBirthDateAsDate(studentIn.getBirthDateAsDate());
                studInFor.setBirthDateAsStr(studentIn.getBirthDateAsStr());

            }
        }
    }
}
