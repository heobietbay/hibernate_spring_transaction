package khoa.training.hibernate.model;

import java.util.*;

/**
 * Created by trandangkhoa on 4/16/2015.
 */
public class Studentv1 {

    private Integer studentId;
    private String firstName;
    private String lastName;
    private Date dob;
    /**
     * This is marked as <timestamp> in hibernate mapping.
     * This is meant to be updated by Hibernate itself
     * Manually update this can cause optimistic locking exception.
     * https://www.intertech.com/Blog/versioning-optimistic-locking-in-hibernate/
     */
    private Date lastModified;

    private Set<Addressv1> addressv1Set = new HashSet<Addressv1>();

    public Integer getStudentId() {
        return studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Studentv1 studentv1 = (Studentv1) o;

        return studentId != null ? studentId.equals(studentv1.studentId) : studentv1.studentId == null;
    }

    @Override
    public int hashCode() {
        return studentId != null ? studentId.hashCode() : 0;
    }

    @Override

    public String toString() {
        return "Studentv1{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", lastModified=" + lastModified +
                '}';
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Set<Addressv1> getAddressv1Set() {
        return addressv1Set;
    }

    public void setAddressv1Set(Set<Addressv1> addressv1Set) {
        this.addressv1Set = addressv1Set;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
