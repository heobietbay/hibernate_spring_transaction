package khoa.training.hibernate;

import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.hibernate.service.IStudentService;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by khoa on 12/30/2016.
 */
public class HIbernateIdentityDemo {

    /**
     * Please read those topic for deeper understanding
     *  - http://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/transaction.html
     *  - http://stackoverflow.com/questions/6222600/transactional-method-calling-another-method-without-transactional-anotation
     *
     * Please view following class and methods:
     * Please observe how @Transactional and Propagation is used
     *
     * - StudentDAOImpl
     *  + public Object findById(Class T, Serializable id)
     *  + public void update(Studentv1 studentv1)
     *  + public List<Studentv1> findAll()
     *
     *  - DummyCGLibProxyTransactionalService
     *
     *  - NonTransactionalService
     *
     *  - StudentServiceImpl
     *    + public void updateStudent(Studentv1 studentv1)
     * @param args
     */
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("application_context.xml");
        IStudentService studentService = (IStudentService) context.getBean("studentService");

        demoInsertManyStudent(studentService);

        try{
            System.err.println("About to call studentService.updateStudent(studentService.findById(1))");
            studentService.updateStudent(studentService.findById(1));
        }
        catch (RuntimeException e)
        {
            System.err.println(e.getMessage());
        }

        System.err.println("About to view all student");
        List<Studentv1> lst = studentService.findAll();
        for(Studentv1 studentv1 : lst)
        {
            System.out.println(studentv1);
            System.out.println(" \t" + studentv1.getAddressv1Set().toArray()[0]);
        }
    }


    private static void demoInsertManyStudent(IStudentService studentService)
    {
        List<Studentv1> lst = createManyStudents(3);
        studentService.insertManyStudent(lst);
    }
    private static List<Studentv1> createManyStudents(int howMany)
    {
        List<Studentv1> res = new LinkedList<Studentv1>();

        for(int i = 0 ; i < howMany; i++ )
        {
            Studentv1 student = new Studentv1();
            student.setDob(new Date());
            student.setFirstName("Demo new student D" + "_" + (i + 1));
            student.setLastName("Demo");

            Addressv1 addressv1 = new Addressv1();
            addressv1.setLocation("Location " + (i + 1));
            addressv1.setStudent(student);

            student.setAddressv1Set(new HashSet<Addressv1>());
            student.getAddressv1Set().add(addressv1);
            res.add(student);
        }

        return res;
    }

    static org.slf4j.Logger logger = LoggerFactory.getLogger(HIbernateIdentityDemo.class);
}
