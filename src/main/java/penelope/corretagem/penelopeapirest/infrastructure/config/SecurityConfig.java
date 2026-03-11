package penelope.corretagem.penelopeapirest.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    private static final String[] AUTH_WHITELIST = {
            "/auth/**",
            "/error",
            "/contact-us",
            "/swagger-ui/**",
            "/api-docs/**",
            "/h2-console/**",
            // Liberamos o POST de criação de usuário na V2
            "/api/v2/usuarios/esqueci-senha",
            "/api/v2/usuarios/redefinir-senha",
            "/api/v2/auth/login",
            "/api/v2/contact-us"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // Permite criar usuário sem estar logado
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v2/usuarios")).permitAll()
                        // Permite ver anúncios sem estar logado
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/v2/anuncios/**")).permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Define as configurações de CORS para permitir requisições do frontend.
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
        // Cabeçalhos permitidos
        configuration.setAllowedHeaders(List.of("*"));
        // Permite credenciais
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}