package api_code.security.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final String PREFIX = "limite:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean permitirAcesso(String chave, int limite, int janelaEmSegundos) {
        Long tentativas = redisTemplate.opsForValue().increment(PREFIX + chave);

        if (tentativas != null && tentativas == 1) {
            redisTemplate.expire(PREFIX + chave, Duration.ofSeconds(janelaEmSegundos));
        }

        return tentativas != null && tentativas <= limite;
    }
}

