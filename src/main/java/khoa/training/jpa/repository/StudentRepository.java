package khoa.training.jpa.repository;

import khoa.training.hibernate.model.Studentv1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by trandangkhoa on 2/6/2017.
 */
public interface StudentRepository extends JpaRepository<Studentv1, Integer> {

    @Query("select addr.student from Addressv1 as addr where addr.location like %:addr% ")
    List<Studentv1> findStudentLiveIn(@Param("addr")String addr);
}
