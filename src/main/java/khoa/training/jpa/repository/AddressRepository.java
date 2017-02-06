package khoa.training.jpa.repository;

import khoa.training.hibernate.model.Addressv1;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by trandangkhoa on 2/6/2017.
 */
public interface AddressRepository extends JpaRepository<Addressv1, Integer> {
}
