package khoa.training.hibernate.service;

import jdk.nashorn.internal.objects.annotations.Setter;
import khoa.training.hibernate.dao.IStudentDAO;
import khoa.training.hibernate.model.Studentv1;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This will have method marked as @Transactional.
 * Since this class does not implement any interface, Spring will
 * enhance it with CGLibProxy
 */
public class DummyCGLibProxyTransactionalService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStudent() {
        Studentv1 studentv1 = (Studentv1) studentDAO.findById(Studentv1.class,3);
        studentv1.setFirstName(studentv1.getFirstName() + "_DummyCGLibProxyTransactionalService");
        studentv1.setLastName(studentv1.getLastName() + "_DummyCGLibProxyTransactionalService");
        //nonTransactionalService.processStudent(studentv1);
        // because in studentDAO.findById method, readOnly is specified
        // Hibernate may set flushmode to NEVER, thus prevent any flush()
        // to happen. One way to fix this is to decorate studentDAO.update
        // with @Transactional(propagation = Propagation.REQUIRES_NEW)
        studentDAO.update(studentv1);
    }

    @Setter
    public void setNonTransactionalService(INonTransactionalService nonTransactionalService) {
        this.nonTransactionalService = nonTransactionalService;
    }
    @Setter
    public void setStudentDAO(IStudentDAO studentDAO)
    {
        this.studentDAO = studentDAO;
    }

    private INonTransactionalService nonTransactionalService;
    private IStudentDAO studentDAO;
}
