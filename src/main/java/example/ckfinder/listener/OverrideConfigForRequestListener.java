package example.ckfinder.listener;

import com.cksource.ckfinder.config.Config;
import com.cksource.ckfinder.event.GetConfigForRequestEvent;
import com.cksource.ckfinder.event.LoadConfigEvent;
import com.cksource.ckfinder.exception.InvalidRequestException;
import com.cksource.ckfinder.filesystem.Backend;
import com.cksource.ckfinder.listener.Listener;
import com.cksource.ckfinder.resourcetype.ResourceType;
import com.cksource.ckfinder.resourcetype.ResourceTypeFactory;
import com.cksource.ckfinder.utils.PathUtils;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static com.cksource.ckfinder.utils.PathUtils.normalizePath;

@Named
public class OverrideConfigForRequestListener implements Listener<GetConfigForRequestEvent> {
    private static Logger logger = LoggerFactory.getLogger(LoadConfigListener.class);

    @Override
    public void onApplicationEvent(GetConfigForRequestEvent event) {
        Config config = event.getConfig();
        Config.ResourceType siteImagesResourceTypeConfig = config.getResourceTypeConfig("SiteImages");

        HttpServletRequest request = event.getRequest();
        Cookie siteIdCookie = WebUtils.getCookie(request, "site-id");

        // Cast to int to avoid path traversal
        String siteId = String.valueOf(
            siteIdCookie == null ? 1 : Integer.parseInt(siteIdCookie.getValue()) // Use 1 as the default
        );

        logger.info("Site id from cookie: " + (siteIdCookie == null ? "[not set, using 1 as default]" : siteId));

        String resourceTypeDir = siteImagesResourceTypeConfig.getDirectory().replace("{siteId}", siteId);
        logger.info("Resource type directory template: " + siteImagesResourceTypeConfig.getDirectory());
        logger.info("Site resource type directory: " + resourceTypeDir);

        // Make sure the resource type directory exists
        String resourceTypeFullPath = PathUtils.combinePaths(System.getProperty("user.dir"), "userfiles", resourceTypeDir);
        logger.info("Site resource type directory absolute path: " + resourceTypeFullPath);
        Path fsDirectoryPath = Paths.get(resourceTypeFullPath);


        // If the site resource type directory does not exist, create one.
        if (!Files.exists(fsDirectoryPath)) {
            logger.info(String.format("The directory for site \"%s\" doesn't exist", siteId));
            try {
                Files.createDirectories(fsDirectoryPath);
                logger.info(String.format("Created directory for site \"%s\" under %s", siteId, fsDirectoryPath));
            } catch (IOException e) {
                logger.error(String.format("Could not create directory for site \"%s\" under %s", siteId, fsDirectoryPath));
            }
        }

        siteImagesResourceTypeConfig.setDirectory(resourceTypeDir);

        // For easier testing update the label
        siteImagesResourceTypeConfig.setLabel(siteImagesResourceTypeConfig.getLabel() + " (siteId: " + siteId + ")");
    }
}
