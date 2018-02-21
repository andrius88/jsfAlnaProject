package lt.asw.jsfCRUD.backingBeans;

import lt.asw.jsfCRUD.dao.*;
import lt.asw.jsfCRUD.entity.Student;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.List;


@ManagedBean
@ViewScoped

public class SimpleCrudBeanLazy {

     private StudentDsDbDaoLazyImpl studentDaoIns = new StudentDsDbDaoLazyImpl();
    //private StudentDao studentDaoIns = new StudentDsDbDaoLazyImpl();
    //private StudentDao studentDaoIns = new StudentDbDaoImpl();
    //private StudentDao studentDaoIns = new StudentLocalDaoImpl();
    private Student studentIns = new Student();
    private Student beforeEditStudent = null;

    private StudentLazyDataModel studentLazyIns;

    //    @ManagedProperty(value="#{param.editStatus}")
    private boolean editStatus;

    // Constructor
    public SimpleCrudBeanLazy() {
        //System.out.println("SimpleCrudBean: constructor was finished");
    }

    @PostConstruct
    public void init() {
        studentLazyIns = new StudentLazyDataModel(studentDaoIns);
    }

    public LazyDataModel<Student> getStudentLazyIns() {
        return studentLazyIns;
    }

    public void delete(Student itemIn) throws IOException {
        System.out.println("SimpleCrudBeanLazy: method-delete");
        studentDaoIns.removeStudent(itemIn);

    }

    public void add() {
        System.out.println("SimpleCrudBean: ADD Method: ");
//        System.out.println("Student id: " + studentIns.getId() + " name: " + studentIns.getFirstName() + " surname: " + studentIns.getLastName() +
//                           " birth date: " + DateStringConverter.dateToString(studentIns.getBirthDateAsDate()) );

        List <Student> locStudentList = studentDaoIns.getStudentList();
        if(!locStudentList.isEmpty()){
            studentIns.setId(locStudentList.get(locStudentList.size() - 1).getId() + 1);
        }else{
            studentIns.setId(1);
        }

        studentDaoIns.addStudent(studentIns); ;
        studentIns = new Student();
    }

    public void resetAdd() {
        System.out.println("SimpleCrudBean: resetAdd Method: ");
        studentIns = new Student();

    }

    public void edit(Student item) {
        System.out.println("SimpleCrudBean: edit Method: ");
        beforeEditStudent = item.clone();
        this.studentIns = item;
        editStatus = true;

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

    }

    public void cancelEdit() {
        System.out.println("SimpleCrudBean: cancelEdit Method: ");
        // this.studentIns.restore(beforeEditStudent); // required in SessionScope case
                                                       // RequestScope case throws "NullPointerException"
        this.studentIns = new Student();
        editStatus = false;
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

}
