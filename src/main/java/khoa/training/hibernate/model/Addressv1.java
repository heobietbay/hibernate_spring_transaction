package khoa.training.hibernate.model;

import javax.persistence.*;

/**
 * Created by trandangkhoa on 4/16/2015.
 */
@Entity
@Table(name = "address")
public class Addressv1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Integer addressId;


    @Column(name = "location",nullable = false,length = 200)
    private String location;
    
    @Override
	public String toString() {
		return "Addressv1 [addressId=" + addressId + ", location=" + location + "]";
	}

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


    @ManyToOne
    @JoinColumn(name = "student_id")
    private Studentv1 student;

    public Studentv1 getStudent() {
        return student;
    }

    public void setStudent(Studentv1 student) {
        this.student = student;
    }

}
