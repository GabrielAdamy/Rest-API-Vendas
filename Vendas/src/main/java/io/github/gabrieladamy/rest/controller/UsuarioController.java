package io.github.gabrieladamy.rest.controller;

import io.github.gabrieladamy.domain.entity.Usuario;
import io.github.gabrieladamy.exception.SenhaInvalidaException;
import io.github.gabrieladamy.rest.dto.CredenciaisDTO;
import io.github.gabrieladamy.rest.dto.TokenDTO;
import io.github.gabrieladamy.security.jwt.JwtService;
import io.github.gabrieladamy.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder; //criptografar a senha
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha()); //criptografar a senha
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){

        try {
            Usuario usuario = Usuario.
                            builder().
                            login(credenciais.getLogin()).
                            senha(credenciais.getSenha()).
                            build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.geraToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);

        }catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
