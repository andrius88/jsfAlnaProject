package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

public class StudentLazyDataModel extends LazyDataModel<Student> {

    private StudentDsDbDaoLazyImpl studentDaoRef;

    //Constructor
    public StudentLazyDataModel(StudentDsDbDaoLazyImpl studentDaoIn) {
        this.studentDaoRef = studentDaoIn;
    }

    @Override
    public List<Student> load(int firstIn, int pageSizeIn, String sortFieldIn, SortOrder sortOrderIn, Map<String,Object> filtersIn) {
        System.out.println(this.getClass().toString() + ":load: first :" + firstIn + " pageSize :" + pageSizeIn);

        List<Student> studentList;
        int totRowCont;

        // SQL request to mach LazyDataModel.load parameters
        studentList = studentDaoRef.getStudentList(firstIn, pageSizeIn, sortFieldIn, sortOrderIn);

        // SQL request to get total count of rows
        totRowCont = studentDaoRef.getStudentCount();

        //this.setRowCount is required to function PF paginator
        this.setRowCount(totRowCont);

        return studentList;
    }
}
