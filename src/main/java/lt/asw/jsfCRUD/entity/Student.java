package lt.asw.jsfCRUD.entity;

import lt.asw.jsfCRUD.dao.DateStringConverter;

import java.util.Date;

//@ManagedBean
//@SessionScoped
public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private String birthDateAsStr;
    private Date birthDateAsDate;

    // constructor
    public Student() {
    }

    public Student(int id, String firstNameIn, String lastNameIn, Date birthDateIn) {
        this.id = id;
        this.firstName = firstNameIn;
        this.lastName = lastNameIn;
        this.birthDateAsDate = birthDateIn;
        this.birthDateAsStr = DateStringConverter.dateToString(birthDateIn) ;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDateAsStr() { return birthDateAsStr; }

    public void setBirthDateAsStr(String birthDateAsStr) {
        this.birthDateAsStr = birthDateAsStr;
        this.birthDateAsDate = DateStringConverter.stringToDate(birthDateAsStr);
    }

    public Date getBirthDateAsDate() { return birthDateAsDate; }

    public void setBirthDateAsDate(Date birthDateAsDate) {
        this.birthDateAsDate = birthDateAsDate;
        this.birthDateAsStr = DateStringConverter.dateToString(birthDateAsDate);
    }

    @Override
    public Student clone() {
        return new Student(id, firstName, lastName, birthDateAsDate);
    }

    public void restore(Student studentIn) {
        this.id = studentIn.getId();
        this.firstName = studentIn.getFirstName();
        this.lastName = studentIn.getLastName();
        this.birthDateAsDate = studentIn.getBirthDateAsDate();
        this.birthDateAsStr = DateStringConverter.dateToString(this.birthDateAsDate) ;

    }

}
