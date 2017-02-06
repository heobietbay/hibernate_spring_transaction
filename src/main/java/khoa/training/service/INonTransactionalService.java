package khoa.training.service;

import khoa.training.hibernate.model.Studentv1;

/**
 * Created by khoa on 1/2/2017.
 */
public interface INonTransactionalService {
    void processStudent(Studentv1 studentv1);
}
