package khoa.training.hibernate;

import khoa.training.hibernate.model.Address;
import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Student;
import khoa.training.hibernate.model.Studentv1;
import khoa.training.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

/**
 * Created by trandangkhoa on 4/17/2015.
 */
public class InsertDeleteUpdateExample {

    public static void main( String[] args )
    {
        System.out.println("*** BEGIN InsertDeleteUpdateExample ***");
       // InsertDeleteUpdateExample.testInsertStudent_01() ;
      //      InsertDeleteUpdateExample.testInsertAddress_02();
      //  InsertDeleteUpdateExample.testInsertAddressV1_03();
        System.out.println("*** END ***");
    }

    /**
     * Basic insert.
     */
    public static void testInsertStudent_01() {
        Student student = new Student();

        student.setDob(new Date());
        student.setFirstName("Demo new student");
        student.setLastName("Nguyen");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();

    }

    /**
     * Basic insert.
     */
    public static void testInsertAddress_02() {
        Address address = new Address();

        address.setLocation("Khoa's location");
        address.setStudentId(2);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(address);
        transaction.commit();
        session.close();

    }

    /**
     * Set student object to address then insert
     */
    public static void testInsertAddressV1_03() {
        Addressv1 address = new Addressv1();

        address.setLocation("Some interesting place");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Studentv1 studentv1 = (Studentv1) session.get(Studentv1.class, 3);
        address.setStudent(studentv1);
        session.save(address);

        transaction.commit();
        session.close();

    }

    /**
     * Using saveOrUpdate
     */
    public static void testInsertOrUpdateAddressV1_04() {
        Addressv1 address = new Addressv1();

        address.setAddressId(1);
        address.setLocation("Some unknown location updated");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Studentv1 studentv1 = (Studentv1) session.get(Studentv1.class, 4);
        address.setStudent(studentv1);
        session.saveOrUpdate(address);

        transaction.commit();
        session.close();

    }

    /**
     * Simple delete
     */
    public static void testDeleteAddressV1_05() {
        Addressv1 address = new Addressv1();

        address.setAddressId(1);


        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();


        session.delete(address);

        transaction.commit();
        session.close();

    }
}
