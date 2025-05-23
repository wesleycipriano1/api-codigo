package api_code.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import api_code.security.jwt.FiltroJwt;
import api_code.security.service.ServicoDetalhesUsuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ServicoDetalhesUsuario servicoDetalhesUsuario;
    private final FiltroJwt filtroJwt;

    public SecurityConfig(ServicoDetalhesUsuario servicoDetalhesUsuario, FiltroJwt filtroJwt) {
        this.servicoDetalhesUsuario = servicoDetalhesUsuario;
        this.filtroJwt = filtroJwt;
    }




    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(servicoDetalhesUsuario)
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .cors() // Habilita o uso do bean de configuração de CORS
        .and()
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/**", "/api/recuperar-senha/**").permitAll()
            .anyRequest().authenticated())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}


}
