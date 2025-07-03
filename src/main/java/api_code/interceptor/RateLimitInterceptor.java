package api_code.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import api_code.exception.BloqueadoException;
import api_code.exception.CamposVaziosException;
import api_code.exception.TokenInvalidoException;
import api_code.security.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterService rateLimiterService;

    private static final int LIMITE_POR_MINUTO = 3;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new TokenInvalidoException("Token inválido ou não autenticado");
        }

        String id = auth.getName();

        if (id == null || id.isBlank()) {
            throw new CamposVaziosException("id não pode ser nulo ou vazio");
        }

        String caminho = request.getRequestURI();
        String chave = id + ":" + caminho;

        boolean permitido = rateLimiterService.permitirAcesso(chave, LIMITE_POR_MINUTO, 60);

        if (!permitido) {
            throw new BloqueadoException(chave + " excedeu o limite de requisições tente novamente mais tarde");
        }

        return true;
    }
}