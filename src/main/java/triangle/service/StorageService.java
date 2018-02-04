package triangle.service;

import org.springframework.beans.factory.annotation.Qualifier;
import triangle.model.State;
import triangle.model.Triangle;

import java.util.List;

@Qualifier("iMemoryBean")
public interface StorageService {


    void add(Triangle triangle);

    void update(Triangle trianglePreCalc);

    List<Triangle> getEntities(State state);

    long getEntitiesCount();

    Triangle getEntityById(Integer id);


}
