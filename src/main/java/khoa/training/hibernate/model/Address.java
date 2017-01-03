package khoa.training.hibernate.model;

/**
 * Created by trandangkhoa on 4/16/2015.
 */
public class Address {

    private Integer addressId;
    private String location;
    private Integer studentId;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", location='" + location + '\'' +
                ", studentId=" + studentId +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

}
