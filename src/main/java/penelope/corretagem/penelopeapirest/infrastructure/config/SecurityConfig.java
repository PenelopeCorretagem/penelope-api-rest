package penelope.corretagem.penelopeapirest.infrastructure.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import penelope.corretagem.penelopeapirest.infrastructure.filter.SecurityFilter;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final Environment environment;

    public SecurityConfig( SecurityFilter securityFilter, Environment environment) {
        this.securityFilter = securityFilter;
        this.environment = environment;
    }

    private static final String[] AUTH_WHITELIST = {
            "/v1/auth/**",
            "/error",
            "/v1/contact-us",
            "/swagger-ui/**",
            "/api-docs/**",

            "/v1/users/forgot-password",
            "/v1/users/reset-password",
            "/v1/users/validate-reset-token",

            "/v1/auth/login",

            "/v1/advertisements",
            "/v1/advertisements/{id}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {

                    if (environment.acceptsProfiles(Profiles.of("dev"))) {
                        authorize.requestMatchers(PathRequest.toH2Console()).permitAll();
                    }

                    authorize
                            .requestMatchers(AUTH_WHITELIST).permitAll()
                            // Permite criar usuário sem estar logado
                            .requestMatchers(antMatcher(HttpMethod.POST, "/v1/users")).permitAll()
                            // Permite ver anúncios sem estar logado
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/advertisements/**")).permitAll()
                            .anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8081", // Back
                "http://localhost:3000",  // dev
                "http://localhost:3001",  // homol
                "http://localhost:3002"   // prod
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}