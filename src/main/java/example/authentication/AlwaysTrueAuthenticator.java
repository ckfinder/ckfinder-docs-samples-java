package example.authentication;

import com.cksource.ckfinder.authentication.Authenticator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class AlwaysTrueAuthenticator implements Authenticator {
    @Inject
    HttpServletRequest request;

    @Override
    public boolean authenticate() {
        return true;
    }
}
