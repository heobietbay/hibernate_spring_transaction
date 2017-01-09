package khoa.training.hibernate.scenario.error;

import khoa.training.hibernate.dao.BaseDAOImpl;
import khoa.training.hibernate.dao.IBaseDAO;
import khoa.training.hibernate.model.Studentv1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This aims to demonstrate scenario:
 *    org.hibernate.HibernateException: Illegal attempt to associate a collection with two open sessions.
 *
 * This error can occur in many scenario.
 */

public class ObjectAssociatedWithTwoOpenSession {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("scenario/error/ObjectAssociatedWithTwoOpenSession_context.xml");
        IBaseDAO myDAO = (IBaseDAO) context.getBean("myDAO");
        // should prepare some data first before doing this.
        prepareData(myDAO);
        myDAO.clear();

        MyService myService = (MyService) context.getBean("myService");
        myService.scenario1_TwoTransactionMethodMarkedWithPropagationRequiredNew();
    }

    private static void prepareData(IBaseDAO myDAO) {
        Studentv1 data = new Studentv1();
        data.setFirstName("Student 1");
        data.setLastName("Student 1");
        myDAO.saveOrUpdate(data);
    }
}

class MyService {

    /**
     * Scenario 1: 2 Transaction Method Marked With Propagation REQUIRES_NEW.
     * <p>This method will be marked with @Transactional(propagation = Propagation.REQUIRES_NEW)
     * Inside it will first retrieved an object, then use its myDAO to update object.</p>
     * Since myDAO update method also marked with @Transactional(propagation = Propagation.REQUIRES_NEW),
     * should expect error occurs.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void scenario1_TwoTransactionMethodMarkedWithPropagationRequiredNew()
    {
        Studentv1 studentv1 = (Studentv1) myDAO.findById(Studentv1.class,1);
        studentv1.setFirstName("For demo error purpose");

        myDAO.saveOrUpdate(studentv1);
    }

    /**
     * Setter.
     * @param myDAO
     */
    public void setMyDAO(IBaseDAO myDAO) {
        this.myDAO = myDAO;
    }
    IBaseDAO myDAO;
}
class MyDAO extends BaseDAOImpl {

    /**
     * Override super method, by adding addition (propagation = Propagation.REQUIRES_NEW)
     * @param object
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveOrUpdate(Object object) {
        super.saveOrUpdate(object);
    }

    protected Class getToLockClass() {
        return null;
    }

    protected String getUpdateHql() {
        return null;
    }
}