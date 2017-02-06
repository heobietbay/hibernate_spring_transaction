package khoa.training.jpa;

import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.service.IStudentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

public class JpaApp
{
    public static void main( String[] args )
    {

        ApplicationContext context = new ClassPathXmlApplicationContext("khoa/training/jpa/config/application_context.xml");
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
}
