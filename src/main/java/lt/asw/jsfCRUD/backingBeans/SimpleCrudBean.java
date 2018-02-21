package lt.asw.jsfCRUD.backingBeans;

import lt.asw.jsfCRUD.dao.*;
import lt.asw.jsfCRUD.entity.Student;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;


@ManagedBean   (eager = true)
@RequestScoped //@RequestScoped //@SessionScoped   @RequestScoped
public class SimpleCrudBean {

    private static List<Student> studentList;

    private StudentDao studentDaoIns = new StudentDsDbDaoImpl();
    //private StudentDao studentDaoIns = new StudentDbDaoImpl();
    //private StudentDao studentDaoIns = new StudentLocalDaoImpl();
    private Student studentIns = new Student();
    private Student beforeEditStudent = null;


    @ManagedProperty(value="#{param.editStatus}")
    private boolean editStatus;

    @ManagedProperty(value="#{commonUtils}")
    private CommonUtils util;
    public void setUtil(CommonUtils util) {
        this.util = util;
    }

    // Constructor
    public SimpleCrudBean() {
        //System.out.println("SimpleCrudBean: constructor was finished");
    }

    @PostConstruct
    public void init() {
        if (editStatus == true){
            //dialogTitleMsg = "Edit Student Id: " ;
        }
    }

    public List<Student> getStudentList() {
        return studentDaoIns.getStudentList();
    }

    public void delete(Student itemIn) throws IOException {
        studentDaoIns.removeStudent(itemIn);
        //util.redirectWithGet();
    }

    public void add() {
        System.out.println("SimpleCrudBean: ADD Method: ");
        System.out.println("Student id: " + studentIns.getId() + " birth date: " + DateStringConverter.dateToString(studentIns.getBirthDateAsDate()) );
        List <Student> locStudentList = studentDaoIns.getStudentList();
        studentIns.setId(locStudentList.isEmpty() ? 1 : locStudentList.get(locStudentList.size() - 1).getId() + 1);
        studentDaoIns.addStudent(studentIns);
        studentIns = new Student();

        //util.redirectWithGet();
    }

    public void resetAdd() {
        System.out.println("SimpleCrudBean: resetAdd Method: ");
        studentIns = new Student();

        //util.redirectWithGet();
    }

    public void edit(Student item) {
        System.out.println("SimpleCrudBean: edit Method: ");
        beforeEditStudent = item.clone();
        this.studentIns = item;
        editStatus = true;

        //***//util.redirectWithGet();
    }

    public void saveEdit() {
        System.out.println("SimpleCrudBean: saveEdit Method: ");
        System.out.println("SimpleCrudBean: Student to be saved: " + studentIns.getId() + " "
                                                                   + studentIns.getFirstName() + " "
                                                                   + studentIns.getLastName() + " "
                                                                   + DateStringConverter.dateToString(studentIns.getBirthDateAsDate()));
        studentDaoIns.editStudent(studentIns);
        this.studentIns = new Student();
        editStatus = false;

        //util.redirectWithGet();
    }

    public void cancelEdit() {
        System.out.println("SimpleCrudBean: cancelEdit Method: ");
        // this.studentIns.restore(beforeEditStudent); // required in SessionScope case
                                                       // RequestScope case throws "NullPointerException"
        this.studentIns = new Student();
        editStatus = false;

        //util.redirectWithGet();
    }

    public Student getStudentIns() {
        return studentIns;
    }

    public void setStudentIns(Student studentInsIn) {
        this.studentIns = studentInsIn;
    }

    public boolean isEditStatus() {
        return editStatus;
    }

    public void setEditStatus(boolean editStatus) {
        this.editStatus = editStatus;
    }

    //*****************************************************//

}
