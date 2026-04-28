package penelope.corretagem.penelopeapirest.infrastructure.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private static final String ROLE_ADMINISTRADOR = "ADMINISTRADOR";

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

            "/v1/auth/login"
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
                            // Permite ver alguns anúncios sem estar logado
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/advertisements")).permitAll()
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/advertisements/latest")).permitAll()
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/advertisements/estate/**")).permitAll()
                            // Detalhe do anúncio é público; regra de anúncio inativo é tratada no Use Case
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/advertisements/*")).permitAll()
                            // Perfil do usuário logado
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/users/profile")).authenticated()
                            // Ações administrativas em anúncios
                            .requestMatchers(antMatcher(HttpMethod.POST, "/v1/advertisements")).hasRole(ROLE_ADMINISTRADOR)
                            .requestMatchers(antMatcher(HttpMethod.PUT, "/v1/advertisements/**")).hasRole(ROLE_ADMINISTRADOR)
                            .requestMatchers(antMatcher(HttpMethod.PATCH, "/v1/advertisements/**")).hasRole(ROLE_ADMINISTRADOR)
                            .requestMatchers(antMatcher(HttpMethod.DELETE, "/v1/advertisements/**")).hasRole(ROLE_ADMINISTRADOR)
                            // Ações administrativas em diferenciais
                            .requestMatchers(antMatcher(HttpMethod.POST, "/v1/amenities")).hasRole(ROLE_ADMINISTRADOR)
                            .requestMatchers(antMatcher(HttpMethod.PATCH, "/v1/amenities/**")).hasRole(ROLE_ADMINISTRADOR)
                            .requestMatchers(antMatcher(HttpMethod.DELETE, "/v1/amenities/**")).hasRole(ROLE_ADMINISTRADOR)
                            // Ações administrativas em usuários
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/users")).hasRole(ROLE_ADMINISTRADOR)
                            .requestMatchers(antMatcher(HttpMethod.GET, "/v1/users/*")).permitAll()
                            .requestMatchers(antMatcher(HttpMethod.PUT, "/v1/users/**")).permitAll()
                            .requestMatchers(antMatcher(HttpMethod.DELETE, "/v1/users/**")).permitAll()
                            // Upload de imagens de anúncios
                            .requestMatchers(antMatcher(HttpMethod.POST, "/v1/images")).hasRole(ROLE_ADMINISTRADOR)
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