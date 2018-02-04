package triangle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import triangle.model.State;
import triangle.model.Triangle;

import java.util.ArrayList;
import java.util.List;


public class StorageServiceInMemory implements StorageService {
    private static final Logger log = LoggerFactory.getLogger(StorageServiceInMemory.class);

    private List<Triangle> trianglesPreCalc = new ArrayList<>();
    private List<Triangle> trianglesPostCalc = new ArrayList<>();
    private Integer maxIdTrianglesPostCalc = 0;

    @Override
    public synchronized void add(Triangle trianglePreCalc) {
        log.info("StorageServiceInMemory.add method call with triangleInput: " + trianglePreCalc);
        Triangle.validateTriangleFromClient(trianglePreCalc);
        try {
            trianglesPreCalc.add(trianglePreCalc);
        } catch (Exception e) {
            log.warn("StorageServiceInMemory.add FAILED-_-_- ");
            log.warn(e.getMessage());
        }
    }

    @Override
    public void update(Triangle trianglePostCalc) {
        log.info("StorageServiceInMemory.update method call with triangle: " + trianglePostCalc);

        try {
            trianglesPostCalc.add(trianglePostCalc);
            maxIdTrianglesPostCalc = trianglePostCalc.getId();
        } catch (Exception e) {
            log.warn("StorageServiceInMemory.update FAILED-_-_- ");
            log.warn(e.getMessage());
        }
    }

    @Override
    public List<Triangle> getEntities(State state) {
        log.info("StorageServiceInMemory.getEntities method call with state: " + state);
        log.debug("trianglesPreCalc: " + trianglesPreCalc);

        log.debug("before getEntities action" );
        debugLists();

        if (state.equals(State.PRE_CALC)) {
            List<Triangle> trianglesPreCalcCopy = new ArrayList<>();
            synchronized (this) {
                trianglesPreCalcCopy = new ArrayList<Triangle>(trianglesPreCalc);
                trianglesPreCalc.clear();
            }
            debugLists();
            return trianglesPreCalcCopy;
        } else {
            List<Triangle> trianglesPostCalcCopy = new ArrayList<Triangle>(trianglesPostCalc);
            debugLists();
            return trianglesPostCalcCopy;
        }
    }

    private void debugLists() {
        log.debug("======triangles Post Calc BEGIN=======");
        log.debug(trianglesPostCalc.toString());
        log.debug("======triangles __Post__ Calc END=======");

        log.debug("======triangles Pre Calc BEGIN=======");
        log.debug(trianglesPreCalc.toString());
        log.debug("======triangles --Pre Calc-- END=======");
    }

    @Override
    public long getEntitiesCount() {
        log.info("StorageServiceInMemory.getEntitiesCount method call");
        return trianglesPreCalc.size() + trianglesPostCalc.size();
    }

    @Override
    public Triangle getEntityById(Integer id) {
        log.info("StorageServiceInMemory.getEntityById method call with id: " + id);
        if (id <= 0) {
            throw new IllegalArgumentException("id can not be smaller then 1");
        } else if (maxIdTrianglesPostCalc >= id) {
            try {//the List location begin from 0 while Triangle Id begin from 1
                return trianglesPostCalc.get(id - 1);

            } catch (Exception e) {
                // the maven debug still not work on my computer so i use this printing to check algo of this method
                log.error("StorageServiceInMemory.getEntityById -  BUG BUG BUG (part A) probably the count is wrong");
                throw new IllegalArgumentException("StorageServiceInMemory.getEntityById -  BUG BUG BUG (part A) probably the count is wrong");
            }
        } else if ((id - maxIdTrianglesPostCalc) <= trianglesPreCalc.size()) {
            try {
                System.out.println("trianglesPreCalc.get(id - maxIdTrianglesPostCalc - 1): " + trianglesPreCalc.get(id - maxIdTrianglesPostCalc - 1));
                return trianglesPreCalc.get(id - maxIdTrianglesPostCalc - 1);
            } catch (Exception e) {
                // the maven debug still not work on my computer so i use this printing to check algo of this method
                log.error("StorageServiceInMemory.getEntityById -  BUG BUG BUG (part B)probably the count is wrong");
                throw new IllegalArgumentException("StorageServiceInMemory.getEntityById -  BUG BUG BUG (part B) probably the count is wrong");
            }
        } else {
            throw new IllegalArgumentException("The id: " + id + " over max length ");
        }
    }
}



