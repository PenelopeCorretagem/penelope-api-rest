package penelope.corretagem.penelopeapirest.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import penelope.corretagem.penelopeapirest.core.gateway.ITokenGateway;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final ITokenGateway tokenGateway;

    public SecurityFilter(ITokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {
            var email = tokenGateway.getEmailFromToken(token);
            var accessLevel = tokenGateway.getAccessLevelFromToken(token);

            if (email != null && !email.isBlank() && accessLevel != null && !accessLevel.isBlank()) {
                var authority = new SimpleGrantedAuthority("ROLE_" + accessLevel.toUpperCase(Locale.ROOT));

                var authentication = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(authority)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}