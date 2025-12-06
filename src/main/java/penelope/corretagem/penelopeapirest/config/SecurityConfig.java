package penelope.corretagem.penelopeapirest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    private static final String[] AUTH_WHITELIST = {
            "/auth/",
            "/webhooks/",
            "/h2-console/",
            "/v3/api-docs/",
            "/api/v1/swagger-ui/",
            "/swagger-ui.html",
            "/swagger-resources/",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security"
    };

    // Define o algoritmo de criptografia de senhas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Expõe o gerenciador de autenticação configurado pelo Spring Security.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configura a cadeia de filtros de segurança, incluindo CORS, CSRF, sessões e regras de autorização.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(antMatcher("/auth/**")).permitAll()

                                // 2. Libera o Swagger explicitamente com AntPathRequestMatcher
                                .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()
                                .requestMatchers(antMatcher("/api-docs/**")).permitAll()
                                .requestMatchers(antMatcher("/swagger-resources/")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.POST, "/users")).permitAll()
                                .requestMatchers(antMatcher("/contact-us")).permitAll()
                                .requestMatchers(antMatcher(HttpMethod.GET, "/advertisement")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/webjars/")).permitAll()

                                // 3. CRUCIAL: Libera a rota de erro do Spring (senão você toma 403 no erro)
                                .requestMatchers(antMatcher("/error")).permitAll()

                                // 4. Libera H2 Console (se estiver usando)
                                .requestMatchers(antMatcher("/h2-console/")).permitAll()
                                .anyRequest().authenticated()
                ).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
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