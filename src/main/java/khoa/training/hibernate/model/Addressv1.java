package khoa.training.hibernate.model;

/**
 * Created by trandangkhoa on 4/16/2015.
 */
public class Addressv1 {

    private Integer addressId;
    private String location;
    
    @Override
	public String toString() {
		return "Addressv1 [addressId=" + addressId + ", location=" + location + ", " +
                "student=" + (student != null ? student.getFirstName() : " not associated")
                + "]";
	}

	private Studentv1 student;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Studentv1 getStudent() {
        return student;
    }

    public void setStudent(Studentv1 student) {
        this.student = student;
    }

}
