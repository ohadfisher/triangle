package triangle.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import triangle.model.StorageServiceImplNames;
import triangle.service.StorageService;
import triangle.service.StorageServiceDb;
import triangle.service.StorageServiceInMemory;

@Configuration
public class TriangleConfig {
    @Value(("${triangle.storageServiceImpl}"))
    StorageServiceImplNames storageServiceImplName;

    @Bean
    public StorageService storageService() {
        if (storageServiceImplName == StorageServiceImplNames.STORAGE_SERVICE_DB) {
            return new StorageServiceDb();
        } else { //storageServiceImplName == StorageServiceImplNames.STORAGE_SERVICE_IN_MEMORY)
            return new StorageServiceInMemory();
        }
    }
}




