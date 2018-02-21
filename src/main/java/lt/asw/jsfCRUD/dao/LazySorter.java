package lt.asw.jsfCRUD.dao;

import lt.asw.jsfCRUD.entity.Student;
import org.primefaces.model.SortOrder;

import java.util.Comparator;

public class LazySorter implements Comparator<Student> {

    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(Student student1, Student student2) {
        try {
            Object value1 = Student.class.getField(this.sortField).get(student1);
            Object value2 = Student.class.getField(this.sortField).get(student2);

            int value = ((Comparable)value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }

}
