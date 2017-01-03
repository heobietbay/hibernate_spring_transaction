package khoa.training.hibernate;

import khoa.training.hibernate.model.Address;
import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Student;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.hibernate.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by trandangkhoa on 4/17/2015.
 */
public class QueryExample {

    public static void main( String[] args )
    {
        System.out.println("*** BEGIN QueryExample ***");
       //     QueryExample.testGetAddressV1ById_01(3);
      //     QueryExample.testGetStudentV1ById_02(4);
    //       QueryExample.testQueryStudentV1ByName_03("Quan");
   //    QueryExample.testQueryStudentV1ByAddress_04("unknown");
        QueryExample.testQueryStudentV1ByName_05("Quan");
        System.out.println("*** END ***");
    }

    /**
     * Note how we close the session. A join fetch is used.
     * @param name the name to search for.
     */
    private static void testQueryStudentV1ByName_05(String name)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(  "select student " +
                                            "from Studentv1 as student join fetch student.addressv1Set " +
                                            "where student.firstName = :name or student.lastName = :name ");
        query.setParameter("name", name);
        List list = query.list();
        session.close();
        if(list != null && !list.isEmpty())
        {
            System.out.println("========== Student info  ===========");
            System.out.println(list.get(0));
        }
    }

    /**
     * Demonstrate how we search using join.
     * @param addressLocation
     */
    public static void testQueryStudentV1ByAddress_04(String addressLocation)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(
                " select student" +
                        " from Studentv1 as student left join student.addressv1Set as addresses " +
                        " where addresses.location like :location "
        );
        query.setParameter("location", "%" + addressLocation + "%");

        List list = query.list();
        //session.close();
        if(list != null && !list.isEmpty())
        {
            System.out.println("========== Student info  ===========");
            System.out.println(list.get(0));
        }
       session.close();
    }

    /**
     * Simple query.
     * @param name
     */
    private static void testQueryStudentV1ByName_03(String name)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select student from Studentv1 as student where firstName = :name or lastName = :name ");
        query.setParameter("name", name);
        List list = query.list();
        if(list != null && !list.isEmpty())
        {
            System.out.println("========== Student info  ===========");
            System.out.println(list.get(0));
        }
        session.close();
    }

    /**
     * Using get by id.
     * @param studentId
     */
    private static void testGetStudentV1ById_02(Integer studentId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Studentv1 studentv1 = (Studentv1) session.get(Studentv1.class, studentId);

        if(studentv1 != null)
        {
            System.out.println("========== Student info  ===========");
            System.out.println(studentv1);
        }
        session.close();
    }
    private static void testGetAddressV1ById_01(Integer addressId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Addressv1 addressv1 = (Addressv1) session.get(Addressv1.class, addressId);

        // What happens if I close session here?
        session.close();
        if(addressv1 != null)
        {
            System.out.println("========== Address info  ===========");
            System.out.println(addressv1);
        }
       // session.close();
    }
    private static void testGetAddressById_01(Integer addressId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Address addressv1 = (Address) session.get(Address.class, addressId);

        // What happens if I close session here?
        //session.close();
        if(addressv1 != null)
        {
            System.out.println("========== Address info  ===========");
            System.out.println(addressv1);

            if(addressv1.getStudentId() != null)
            {
                Student student = (Student) session.get(Student.class, addressv1.getStudentId());
                System.out.println(student);
            }
        }
        session.close();
    }
}
