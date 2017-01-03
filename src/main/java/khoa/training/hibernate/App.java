package khoa.training.hibernate;

import khoa.training.hibernate.dao.IStudentDAO;
import khoa.training.hibernate.dao.StudentDAOImpl;
import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.hibernate.service.IStudentService;
import khoa.training.hibernate.util.HibernateUtil;
import org.hibernate.jdbc.Work;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        ApplicationContext context = new ClassPathXmlApplicationContext("application_context.xml");
       // demoInsertStudentInOwnTransaction1();
        demoInsertManyStudent(context);

        Scanner keyboard = new Scanner(System.in);
        System.out.println("What do you want to do?");
        int choice = keyboard.nextInt();
        while(choice != 9)
        {
            if(choice == 1)
            {
                findAndUpdateStudentThatLiveIn(context,"2");
            }
            else if (choice == 2)
            {
                findStudentThatLiveIn(context,"2");
            }

            choice = keyboard.nextInt();
        }
    }
    private static void demoInsertManyStudent(ApplicationContext context)
    {
        IStudentService studentService = (IStudentService) context.getBean("studentService");

        List<Studentv1> lst = createManyStudents(10);
        studentService.insertManyStudent(lst);
    }
    private static void findStudentThatLiveIn(ApplicationContext context,String addr)
    {
        IStudentService studentService = (IStudentService) context.getBean("studentService");
        Studentv1 studentv1 = studentService.findStudentLiveIn(addr);
        System.out.println(studentv1.getFirstName() + " " + studentv1.getLastName());
    }
    private static void findAndUpdateStudentThatLiveIn(ApplicationContext context,String addr)
    {
        IStudentService studentService = (IStudentService) context.getBean("studentService");
        studentService.findAndUpdateStudentThatLiveIn(addr);
    }
    private static void demoInsertStudentInOwnTransaction1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application_context.xml");
        IStudentDAO studentDAO = (IStudentDAO) context.getBean("studentDAO");

        Studentv1 student = new Studentv1();

        student.setDob(new Date());
        student.setFirstName("With own transaction 222");
        student.setLastName("Demo");

        studentDAO.insertStudentWithOwnTransaction(student);
    }

    private static List<Studentv1> createManyStudents(int howMany)
    {
        List<Studentv1> res = new LinkedList<Studentv1>();

        for(int i = 0 ; i < howMany; i++ )
        {
            Studentv1 student = new Studentv1();

            student.setDob(new Date());
            student.setFirstName("Demo new student D" + i);
            student.setLastName("Demo" + i);

            Addressv1 addressv1 = new Addressv1();
            addressv1.setLocation("Location " + i);
            addressv1.setStudent(student);

            student.setAddressv1Set(new HashSet<Addressv1>());
            student.getAddressv1Set().add(addressv1);

            res.add(student);
        }

        return res;
    }
    private static void testHibernateConnection() {
        HibernateUtil.getSessionFactory().openSession().doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                System.out.println("We are here, there must be connection");
            }
        });
    }
}
