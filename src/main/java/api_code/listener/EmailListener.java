package api_code.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailListener {

    private final JavaMailSender mailSender;

    @Value("${app.url.recuperar-senha}")
    private String baseUrl;

    public EmailListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "email-recuperar")
    public void consumir(Map<String, String> payload) {
        String email = payload.get("email");
        String nome = payload.get("nome");
        String token = payload.get("token");

        String link = baseUrl + "?token=" + token;

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email);
        mensagem.setSubject("Recuperação de Senha");
        mensagem.setText("Olá " + nome + ",\n\nClique no link abaixo para redefinir sua senha:\n" + link
                + "\n\nO link expira em 2 horas.");

        mailSender.send(mensagem);
    }
}
