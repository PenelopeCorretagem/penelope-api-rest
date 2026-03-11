//package penelope.corretagem.penelopeapirest.service;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import penelope.corretagem.penelopeapirest.infrastructure.repository.IUserJpaRepository;
//
//import java.util.Collections;
//
//@Service
//// Serviço responsável por carregar os dados do usuário para autenticação.
//public class JpaUserDetailsService implements UserDetailsService {
//    private final IUserJpaRepository userRepository;
//
//    public JpaUserDetailsService(IUserJpaRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    // Busca o usuário pelo e-mail e retorna os dados necessários para autenticação.
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(userEntity -> User.withUsername(
//                        userEntity.getEmail())
//                        .password(userEntity.getPassword())
//                        .authorities(Collections.emptyList()) // Criação futura de roles (ex: "ROLE_ADMIN")
//                        .build())
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));
//    }
//}