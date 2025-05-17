package api_code.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        StackTraceElement origem = ex.getStackTrace()[0];
        String localErro = origem.getClassName() + "." + origem.getMethodName() + "():" + origem.getLineNumber();

        logger.error("Erro de validação | Local: {}", localErro);

        Map<String, String> errosValidacao = new HashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            errosValidacao.put(erro.getField(), erro.getDefaultMessage());
        }

        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.BAD_REQUEST.value());
        corpoResposta.put("erro", "Erro de validação nos campos.");
        corpoResposta.put("detalhes", errosValidacao);

        return ResponseEntity.badRequest().body(corpoResposta);
    }

    @ExceptionHandler(ExceptionPai.class)
    public ResponseEntity<Map<String, Object>> handleExceptionPai(ExceptionPai ex) {
        StackTraceElement origem = ex.getStackTrace()[0];
        String localErro = origem.getClassName() + "." + origem.getMethodName() + "():" + origem.getLineNumber();

        logger.error("Exceção capturada: {} | Local: {}", ex.getMessage(), localErro);

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", HttpStatus.BAD_REQUEST.value());
        erro.put("erro", ex.getMessage());

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleExceptionGeral(Exception ex) {
        StackTraceElement origem = ex.getStackTrace()[0];
        String localErro = origem.getClassName() + "." + origem.getMethodName() + "():" + origem.getLineNumber();

        logger.error("Erro inesperado: {} | Local: {}", ex.getMessage(), localErro, ex);

        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        erro.put("erro", "Erro inesperado. Tente novamente mais tarde.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
