package khoa.training.service;

import khoa.training.hibernate.dao.IAddressDAO;
import khoa.training.hibernate.dao.IStudentDAO;
import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Demo usage of non transactional service, to see what may happens.
 * This class will contain DAO that is marked transactional, but itself is not.
 * Since this class implement an interface, IF MARKED @Transactional
 * Spring will create JDKAopProxy.
  */
public class NonTransactionalService implements INonTransactionalService
{

    public void updateStudent(Studentv1 studentv1) {
        studentv1.setFirstName("Modified name");
        studentDAO.update(studentv1);
    }

    /**
     * These weird method is for trying to understand how Spring @Transactional works
     * Should read more on JDkProxy, CGLIBProxy
     * @param studentv1
     */
    public void processStudent(Studentv1 studentv1) {
        List<Studentv1> lst = studentDAO.findAll();
        for(Studentv1 st : lst)
        {
            if(st.getStudentId().equals(studentv1.getStudentId()))
            {
                logger.info("\t Do not update this student");
                continue;
            }

            if(st.getAddressv1Set()!= null && !st.getAddressv1Set().isEmpty())
            {
                logger.info("\t updating address");
                Addressv1 addressv1 = (Addressv1) st.getAddressv1Set().toArray()[0];
                addressv1.setLocation(addressv1.getLocation() + "_modified");

                // only update 1 record
                break;
            }
            // technically not needed since this object already in the session
            //    studentDAO.saveOrUpdate(st);
        }
        studentDAO.clear();
    }
    public void setStudentDAO(IStudentDAO studentDAO)
    {
        this.studentDAO = studentDAO;
    }

    public void setAddressDAO(IAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    private IStudentDAO studentDAO;
    private IAddressDAO addressDAO;

    org.slf4j.Logger logger = LoggerFactory.getLogger(NonTransactionalService.class);

}
