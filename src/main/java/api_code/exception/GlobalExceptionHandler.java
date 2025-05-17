package api_code.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
