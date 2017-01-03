package khoa.training.hibernate.dao;

import khoa.training.hibernate.model.Studentv1;

import java.util.List;

/**
 * Created by trandangkhoa on 4/22/2015.
 */
public interface IStudentDAO extends IBaseDAO {
     void insertStudentWithOwnTransaction(Studentv1 studentv1);
     void insertStudent(Studentv1 studentv1);
     void update(Studentv1 studentv1);
     List<Studentv1> findAll();
}
