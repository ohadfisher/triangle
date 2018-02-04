package triangle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import triangle.model.State;
import triangle.model.Triangle;

import java.util.List;

@Configuration
@EnableScheduling
public class AlgoService {
    private static final Logger log = LoggerFactory.getLogger(AlgoService.class);


    @Autowired
    StorageService storageService;

    @Scheduled(fixedDelay = 15000)
    public void update() {
        log.info("^^^AlgoService.update^^^ ");

        List<Triangle> trianglesPreCalc = storageService.getEntities(State.PRE_CALC);

        trianglesPreCalc.stream().
                forEach(trianglePreCalc -> {
                    Triangle trianglePostCalc = new Triangle(trianglePreCalc);
                    storageService.update(trianglePostCalc);
                    log.info("trianglePostCalc:: " + trianglePostCalc);
                });
    }


    @Scheduled(fixedDelay = 30000)
    public void totalEntitiesCounter() {
        log.info("^^^AlgoService.totalEntitiesCounter^^^ ");

        long totalEntitiesCount = storageService.getEntitiesCount();
        log.info("totalEntitiesCount - " + totalEntitiesCount);
    }


}
