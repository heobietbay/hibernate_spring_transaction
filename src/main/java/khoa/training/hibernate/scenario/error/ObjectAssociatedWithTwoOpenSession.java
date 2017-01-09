package khoa.training.hibernate.scenario.error;

import khoa.training.hibernate.dao.BaseDAOImpl;
import khoa.training.hibernate.dao.IBaseDAO;
import khoa.training.hibernate.model.Studentv1;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

        MyService myService = (MyService) context.getBean("myService");
        try {
            myService.scenario1_TwoTransactionMethodMarkedWithPropagationRequiredNew();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            myService.scenario2_Open2DifferentSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        System.out.println("Scenario 1: 2 Transaction Method Marked With Propagation REQUIRES_NEW.");
        Studentv1 studentv1 = (Studentv1) myDAO.findById(Studentv1.class,1);
        studentv1.setFirstName("For demo error purpose");

        myDAO.saveOrUpdate(studentv1);

        System.out.println("Scenario 1: END");
    }

    /**
     * Scenario 2: mannually open 2 sessions,
     *  <p>one for query an object</p>
     *  latter updates that object.
     */
    public void scenario2_Open2DifferentSession(){

        System.out.println("Scenario 2: Open 2 Different Session");
        Session session1 = myDAO.openSession();
        Studentv1 studentv1 = (Studentv1) session1.get(Studentv1.class,1);
        studentv1.setFirstName("For demo error purpose");

        Session session2 = myDAO.openSession();
        Transaction transaction = session2.beginTransaction();
        session2.update(studentv1);
        transaction.commit();

        System.out.println(myDAO.findById(Studentv1.class,1));
        System.out.println("Scenario 2: END");
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