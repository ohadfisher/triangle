package triangle.configuration.repository;

import org.springframework.data.repository.CrudRepository;
import triangle.model.Triangle;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called triangleRepository
// CRUD refers Create, Read, Update, Delete


public interface TriangleRepository extends CrudRepository<Triangle, Long> {
    List<Triangle> findByState(String state);

    Triangle findById(Integer id);

}
