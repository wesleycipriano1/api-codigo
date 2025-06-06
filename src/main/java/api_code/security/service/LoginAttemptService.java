package api_code.security.service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import api_code.exception.CamposVaziosException;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 5;

    private static final long BLOCK_DURATION = 120;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String getKey(String email) {
        return "login:attempts:" + email;
    }

    public void loginSucceeded(String email) {
        redisTemplate.delete(getKey(email));
    }

    public void loginFailed(String email) {
        String key = getKey(email);
        Long attempts = redisTemplate.opsForValue().increment(key);

        if (attempts != null && attempts == 1) {
            redisTemplate.expire(key, BLOCK_DURATION, TimeUnit.MINUTES);
        }
    }

    public boolean isBlocked(String email) {
        String key = getKey(email);
        String attemptsStr = redisTemplate.opsForValue().get(key);
        if (attemptsStr == null) {
            return false;
        }
        int attempts = Integer.parseInt(attemptsStr);
        return attempts >= MAX_ATTEMPT;
    }

    public long getRemainingBlockTime(String email, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new CamposVaziosException("TimeUnit não pode ser nulo");
        }
        String key = getKey(email);
        Long expire = redisTemplate.getExpire(key, timeUnit);
        if(expire == null || expire < 0) {
            return 0;
        }
        return expire;
    }
}
