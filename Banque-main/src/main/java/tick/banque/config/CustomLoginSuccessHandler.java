package tick.banque.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_CLIENT = "ROLE_CLIENT";
    private static final String ROLE_AGENT = "ROLE_AGENT";
    private static final String ADMIN_PATH = "/admin";
    private static final String CLIENT_PATH = "/client";
    private static final String AGENT_PATH = "/agent";
    private static final String LOGIN_ERROR_PATH = "/login?error=true";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains(ROLE_ADMIN)) {
            response.sendRedirect(ADMIN_PATH);
        } else if (roles.contains(ROLE_CLIENT)) {
            response.sendRedirect(CLIENT_PATH);
        } else if (roles.contains(ROLE_AGENT)) {
            response.sendRedirect(AGENT_PATH);
        } else {
            response.sendRedirect(LOGIN_ERROR_PATH);
        }
    }
}
