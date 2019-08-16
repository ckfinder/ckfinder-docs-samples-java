package example.ckfinder.listener;

import com.cksource.ckfinder.config.Config;
import com.cksource.ckfinder.event.LoadConfigEvent;
import com.cksource.ckfinder.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * A listener that dynamically changes the location of user files on application start.
 * <p>
 * It simply replaces the "{user.dir}" placeholder defined in the backend.root
 * configuration with value obtained from "user.dir" system property.
 */
@Named
public class LoadConfigListener implements Listener<LoadConfigEvent> {
    private static Logger logger = LoggerFactory.getLogger(LoadConfigListener.class);

    @Override
    public void onApplicationEvent(LoadConfigEvent loadConfigEvent) {
        String userDir = System.getProperty("user.dir");

        Config config = loadConfigEvent.getConfig();
        Map<String, Config.Backend> backendConfigs = config.getBackends();

        for (Config.Backend backendConfig : backendConfigs.values()) {
            if (backendConfig.getAdapter().equals("local")) {
                String originalRoot = backendConfig.getRoot();
                String newRoot = originalRoot.replace("{user.dir}", userDir);

                backendConfig.setRoot(newRoot);

                Path dirPath = Paths.get(newRoot);

                logger.info(String.format("Checking CKFinder files root directory for backend \"%s\"", backendConfig.getName()));

                if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
                    logger.info(String.format("CKFinder files root directory for backend \"%s\": %s", backendConfig.getName(), dirPath));
                } else {
                    logger.info(String.format("CKFinder files root directory for backend \"%s\" doesn't exist", backendConfig.getName()));

                    try {
                        Files.createDirectories(dirPath);
                        logger.info(String.format("Created CKFinder files root directory for backend \"%s\" under %s", backendConfig.getName(), dirPath));
                    } catch (IOException e) {
                        logger.error(String.format("Could not create CKFinder files root directory for backend \"%s\" under %s", backendConfig.getName(), dirPath));
                    }
                }
            }
        }
    }
}
