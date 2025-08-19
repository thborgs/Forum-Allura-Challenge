package com.br.alura.forum.controller;

import com.br.alura.forum.infra.auth.DadosAutenticacao;
import com.br.alura.forum.infra.security.TokenService;
import com.br.alura.forum.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dados.nome(), dados.senha());

        Authentication authentication = manager.authenticate(authenticationToken);

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        tokenService.autenticarUsuario(usuarioLogado);

        return ResponseEntity.ok("Login realizado com sucesso!");
    }
}
