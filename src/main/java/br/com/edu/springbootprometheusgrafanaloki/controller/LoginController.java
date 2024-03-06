package br.com.edu.springbootprometheusgrafanaloki.controller;

import br.com.edu.springbootprometheusgrafanaloki.dto.LoginDTO;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    Counter loginSuccess;

    Counter loginError;

    public LoginController(MeterRegistry registry) {
        loginSuccess = Counter.builder("login_success")
                .description("Login efetuado com sucesso")
                .register(registry);

        loginError = Counter.builder("login_error")
                .description("Falha no login")
                .register(registry);

    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {

        if(loginDTO.email().equals("teste@teste.com") && loginDTO.password().equals("1234")) {
            loginSuccess.increment();
            return ResponseEntity.ok("Sucesso");
        }

        loginError.increment();
        return ResponseEntity.badRequest().build();
    }
}
