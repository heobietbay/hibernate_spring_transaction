package khoa.training.jpa.service;

import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.jpa.repository.AddressRepository;
import khoa.training.jpa.repository.StudentRepository;
import khoa.training.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by trandangkhoa on 2/6/2017.
 */
@Service(value = "studentService")
public class StudentServiceImpl implements IStudentService {

    public List<Studentv1> findAll() {
        return studentRepository.findAll();
    }

    public List<Addressv1> findAllAddress() {
        return addressRepository.findAll();
    }

    public Studentv1 findStudentLiveIn(String addr) {
        List<Studentv1> studentv1List = studentRepository.findStudentLiveIn(addr);
        return studentv1List.get(0);
    }

    @Deprecated
    public Object merge(Object object) {
        throw new UnsupportedOperationException("Not support in JPA implementation");
    }

    public Studentv1 findById(Integer id) {
        return studentRepository.findOne(id);
    }

    public void saveOrUpdate(Studentv1 studentv1) {
        studentRepository.save(studentv1);
    }

    public void updateStudentInAddress(Addressv1 addressv1) {

    }

    public void insertManyStudent(List<Studentv1> list) {
        studentRepository.save(list);
    }

    public void findAndUpdateStudentThatLiveIn(String addr) {
        List<Studentv1> studentv1List = studentRepository.findStudentLiveIn(addr);
        studentv1List.get(0).setLastName("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        studentRepository.save(studentv1List.get(0));
    }

    public void updateStudent(Studentv1 studentv1) {

    }

    public void deleteList(List<Studentv1> list) {

    }

    public void deleteAddressList(List<Addressv1> list) {

    }


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;
}
