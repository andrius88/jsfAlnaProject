package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;

import java.util.List;

public interface StudentDao {

    public List<Student> getStudentList();

    public void removeStudent(Student studentIn);

    public void addStudent(Student studentIn);

    public void editStudent(Student studentIn);

}
