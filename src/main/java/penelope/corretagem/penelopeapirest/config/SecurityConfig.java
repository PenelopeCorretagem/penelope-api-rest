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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import penelope.corretagem.penelopeapirest.config.properties.CorsProperties;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  final SecurityFilter securityFilter;
  final CorsProperties corsProperties;

  public SecurityConfig(SecurityFilter securityFilter, CorsProperties corsProperties) {
    this.securityFilter = securityFilter;
    this.corsProperties = corsProperties;
  }

  private static final String[] AUTH_WHITELIST = {
    "/auth/**",
    "/error",
    "/contact-us",
    "/cal",

    // Swagger
    "/swagger-ui/**",
    "/api-docs/**",
    "/swagger-resources/**",
    "/webjars/**",

    // H2
    "/h2-console/**"
  };

  // Define o algoritmo de criptografia de senhas usando BCrypt
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Expõe o gerenciador de autenticação configurado pelo Spring Security.
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  // Configura a cadeia de filtros de segurança, incluindo CORS, CSRF, sessões e regras de autorização.
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors(Customizer.withDefaults())
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        // Libera whitelist inteira
        .requestMatchers(AUTH_WHITELIST).permitAll()

        // Libera POST /users
        .requestMatchers(antMatcher(HttpMethod.POST, "/users")).permitAll()

        // Libera TODOS os GET /advertisement/**
        .requestMatchers(antMatcher(HttpMethod.GET, "/advertisement/**")).permitAll()

        .anyRequest().authenticated()
      )
      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  // Define as configurações de CORS para permitir requisições do frontend.
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(corsProperties.allowedOrigins());
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