package example.authentication;

import com.cksource.ckfinder.authentication.Authenticator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * WARNING: Your authenticator should never simply return true. By doing so,
 * you are allowing "anyone" to upload and list the files on your server.
 * You should implement some kind of session validation mechanism to make
 * sure that only trusted users can upload or delete your files.
 */
@Named
public class AlwaysTrueAuthenticator implements Authenticator {
    @Inject
    HttpServletRequest request;

    @Override
    public boolean authenticate() {
        return true;
    }
}
