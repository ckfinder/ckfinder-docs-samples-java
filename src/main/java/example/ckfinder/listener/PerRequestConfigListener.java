package example.ckfinder.listener;

import com.cksource.ckfinder.acl.AclRule;
import com.cksource.ckfinder.acl.Permission;
import com.cksource.ckfinder.event.GetConfigForRequestEvent;
import com.cksource.ckfinder.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Named
public class PerRequestConfigListener implements Listener<GetConfigForRequestEvent> {
    private static final Logger logger = LoggerFactory.getLogger(PerRequestConfigListener.class);

    @Override
    public void onApplicationEvent(GetConfigForRequestEvent event) {
        HttpServletRequest request =  event.getRequest();

        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("user"))
                .findFirst();

        String username = cookie.isPresent() ? cookie.get().getValue() : "anonymous";

        logger.info("User in cookie is: " + username);

        // Make everything in Images resource type read-only for anonymous user
        if (username.equals("anonymous")) {
            ArrayList<AclRule> aclRules = event.getConfig().getAccessControl();

            AclRule imagesReadOnlyRule = new AclRule();
            imagesReadOnlyRule
                    .setResourceType("Images")
                    .setFolder("/")
                    .setRole("*")
                    .setPermission(Permission.FILE_VIEW, true)
                    .setPermission(Permission.FILE_CREATE, false)
                    .setPermission(Permission.FILE_DELETE, false)
                    .setPermission(Permission.FILE_RENAME, false)
                    .setPermission(Permission.FOLDER_CREATE, false)
                    .setPermission(Permission.FOLDER_VIEW, true)
                    .setPermission(Permission.FOLDER_DELETE, false)
                    .setPermission(Permission.FOLDER_RENAME, false);

            aclRules.add(imagesReadOnlyRule);
        }
    }
}