package com.pedro.jpa.libraryapi.security;

import com.pedro.jpa.libraryapi.model.Usuario;
import com.pedro.jpa.libraryapi.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (verifyInstance(authentication)) {
            String login = authentication.getName();
            Usuario usuario = usuarioService.obterPorLogin(login);
            if (usuario != null) {
                authentication = new CustomAuthentication(usuario);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean verifyInstance(Authentication authentication) {
        return authentication instanceof JwtAuthenticationToken;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();
            if (principal instanceof CustomAuthentication authentication) {
                OAuth2TokenType tokenType = context.getTokenType();
                if (OAuth2TokenType.ACCESS_TOKEN.equals(tokenType)) {
                    Collection<GrantedAuthority> authorities = authentication.getAuthorities();
                    List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList();

                    context.getClaims()
                            .claim("authorities", list)
                            .claim("email", authentication.getUsuario().getEmail());
                }
            }
        };
    }

}
