package penelope.corretagem.penelopeapirest.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import penelope.corretagem.penelopeapirest.data.domain.repository.UserRepository;
import penelope.corretagem.penelopeapirest.service.TokenService;
import penelope.corretagem.penelopeapirest.service.exception.UserNotFoundException;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  TokenService tokenService;
  UserRepository userRepository;

  public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);

    if (token != null) {
      // Valida o token e pega o email (Se for inválido, o seu TokenService lança exception ou retorna null, o try/catch ou fluxo deve tratar)
      var email = tokenService.getEmailFromToken(token);

      if (email != null) {

        // Busca o usuário no banco para pegar as permissões (Authorities) reais dele
        var user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        UserDetails userDetails = new User(user.getEmail(), user.getPassword(), Collections.emptyList());

        var authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());

        // Salva o usuário no contexto desta requisição
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    // Segue o fluxo (vai para o Controller ou próximo filtro)
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    // Remove o prefixo "Bearer " para pegar só o hash
    return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.replace("Bearer ", "") : null;    }
}
