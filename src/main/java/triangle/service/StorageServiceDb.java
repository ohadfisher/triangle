package triangle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import triangle.configuration.repository.TriangleRepository;
import triangle.model.State;
import triangle.model.Triangle;

import java.util.List;


public class StorageServiceDb implements StorageService {
    private static final Logger log = LoggerFactory.getLogger(StorageServiceDb.class);


    @Autowired
    private TriangleRepository triangleRepository;

    @Override
    public void add(Triangle triangleInput) {
        log.info("StorageServiceDb.add method call with triangleInput: " + triangleInput);
        Triangle.validateTriangleFromClient(triangleInput);
        try {
            triangleRepository.save(triangleInput);
        } catch (DataAccessException dataAccessException) {
            log.error("StorageServiceDb.add exception: " + dataAccessException.getMessage());
        }
    }

    @Override
    public void update(Triangle trianglePostCalc) {
        log.info("StorageServiceDb.update method call with trianglePostCalc: " + trianglePostCalc);//+ triangleInput);
        try {
            triangleRepository.save(trianglePostCalc);
        } catch (DataAccessException dataAccessException) {
            log.warn("StorageServiceDb.save exception: " + dataAccessException.getMessage());
        }


    }

    @Override
    public List<Triangle> getEntities(State state) {
        log.info("StorageServiceDb.getEntities method call with state: " + state);

        List<Triangle> triangles = null;
        try {
            triangles = triangleRepository.findByState(state.name());
        } catch (DataAccessException dataAccessException) {
            log.warn("StorageServiceDb.getEntities exception: " + dataAccessException.getMessage());
        }
        return triangles;
    }

    @Override
    public Triangle getEntityById(Integer id) {
        log.info("StorageServiceDb.getEntityById method call with Id: " + id);

        Triangle result = null;
        try {
            result = triangleRepository.findById(id);

        } catch (DataAccessException dataAccessException) {
            log.warn("StorageServiceDb.getEntityById exception: " + dataAccessException.getMessage());
        }
        log.info("StorageServiceDb.getEntityById result: " + result);
        return result;
    }

    @Override
    public long getEntitiesCount() {
        log.info("StorageServiceDb.getEntitiesCount method call");
        long result = 0;
        try {
            result = triangleRepository.count();
        } catch (DataAccessException dataAccessException) {
            log.warn("StorageServiceDb.getEntityCount exception: " + dataAccessException.getMessage());
        }
        log.info("StorageServiceDb.getEntitiesCount result: " + result);
        return result;
    }

}
