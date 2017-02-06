package khoa.training.hibernate.service;

import jdk.nashorn.internal.objects.annotations.Setter;
import khoa.training.hibernate.dao.IAddressDAO;
import khoa.training.hibernate.dao.IStudentDAO;
import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.service.DummyCGLibProxyTransactionalService;
import khoa.training.service.INonTransactionalService;
import khoa.training.service.IStudentService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by trandangkhoa on 4/22/2015.
 */
public class StudentServiceImpl implements IStudentService, ApplicationContextAware {

    IStudentService instance;

    /**
     * This sets the application context as this is needed for this method to obtain a reference to the proxy instance that is put in
     * place of this class by Spring.
     * @param applicationContext this is the Spring application context.
     * @throws BeansException on error.
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        instance = (IStudentService)applicationContext.getBean("studentService");
    }

    @Transactional
    public List<Studentv1> findAll() {
        return studentDAO.findAll();
    }

    @Transactional
    public void deleteList(List<Studentv1> list) {
        for(Studentv1 studentv1: list)
        {
            studentDAO.delete(studentv1);
        }
    }

    @Transactional
    public List<Addressv1> findAllAddress() {
        return addressDAO.findAll();
    }

    @Transactional
    public void deleteAddressList(List<Addressv1> list) {
        for(Addressv1 addressv1: list)
        {
            addressDAO.delete(addressv1);
        }
    }

    @Transactional
    public void insertManyStudent(List<Studentv1> list) {
        for(Studentv1 studentv1 : list)
        {
            studentDAO.insertStudent(studentv1);
            if(studentv1.getAddressv1Set() != null && !studentv1.getAddressv1Set().isEmpty())
            {
                for(Addressv1 addressv1 : studentv1.getAddressv1Set())
                {
                    addressDAO.insertAddress(addressv1);
                }
            }
        }
    }

    @Transactional
    public void findAndUpdateStudentThatLiveIn(String addr) {
        final Addressv1 addressv1 = addressDAO.find(addr);
        if(addressv1 != null)
        {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // Do this so the addressv1 is associated with a session
                   final Addressv1 addressv1Temp = (Addressv1) instance.merge(addressv1);
                   instance.updateStudentInAddress(addressv1Temp);
                  //  updateStudent(addressv1Temp);
                }
            });
            thread.start();

           //updateStudent(addressv1);
        }
    }

    @Transactional
    public Studentv1 findStudentLiveIn(String addr) {
        return addressDAO.findStudentLiveIn(addr);
    }

    @Transactional
    public void updateStudentInAddress(Addressv1 addressv1) {
        Studentv1 studentv1 = addressv1.getStudent();
        studentv1.setFirstName("Modified name");
        studentDAO.update(studentv1);
    }
    @Transactional
    public void updateStudent(Studentv1 studentv1) {

        // Modified this to demo optimistic locking exception
        // studentv1.setLastModified(new Date());

        updateStudent();
        dummyCGLibProxyTransactionalService.updateStudent();
        if(true)
        {
            // Please read http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/transaction.html
            // and http://stackoverflow.com/questions/6222600/transactional-method-calling-another-method-without-transactional-anotation
            throw new RuntimeException("For testing transactional, should expect that StudentServiceImpl.updateStudent() not done anything, " +
                    "while dummyCGLibProxyTransactionalService.updateStudent() WILL WORK because it is another instance called.");
        };

        // This cause non unique object exception, will investigate later
       // studentDAO.update(studentv1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStudent() {
        Studentv1 studentv1 = findById(2);
        studentv1.setFirstName(studentv1.getFirstName() + "_StudentServiceImpl");
        studentDAO.update(studentv1);
    }

    public Studentv1 findById(Integer id) {
        return (Studentv1) studentDAO.findById(Studentv1.class,id);
    }

    @Transactional
    public void saveOrUpdate(Studentv1 studentv1) {
        studentDAO.saveOrUpdate(studentv1);
    }

    public Object merge(Object object) {
        return studentDAO.merge(object);
    }

    @Setter
    public void setNonTransactionalService(INonTransactionalService nonTransactionalService) {
        this.nonTransactionalService = nonTransactionalService;
    }

    public void setStudentDAO(IStudentDAO studentDAO)
    {
        this.studentDAO = studentDAO;
    }

    public void setAddressDAO(IAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public void setInstance(IStudentService instance) {
        this.instance = instance;
    }

    public void setDummyCGLibProxyTransactionalService(DummyCGLibProxyTransactionalService dummyCGLibProxyTransactionalService) {
        this.dummyCGLibProxyTransactionalService = dummyCGLibProxyTransactionalService;
    }

    INonTransactionalService nonTransactionalService;

    DummyCGLibProxyTransactionalService dummyCGLibProxyTransactionalService;

    private IStudentDAO studentDAO;

    private IAddressDAO addressDAO;
}
