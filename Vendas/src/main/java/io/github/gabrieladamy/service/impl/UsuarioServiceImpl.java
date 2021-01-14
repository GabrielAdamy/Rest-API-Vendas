package io.github.gabrieladamy.service.impl;

import io.github.gabrieladamy.domain.entity.Usuario;
import io.github.gabrieladamy.domain.repository.UsuarioRepository;
import io.github.gabrieladamy.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não cadastrado!"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder().
                username(usuario.getLogin()).
                password(usuario.getSenha()).
                roles(roles).
                build();
    }
}












  /*
  TESTE
  @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals("adamy")){
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }


        return User.
                builder().
                username("adamy").
                password(encoder.encode("123")).
                roles("USER", "ADMIN").
                build();
    }*/