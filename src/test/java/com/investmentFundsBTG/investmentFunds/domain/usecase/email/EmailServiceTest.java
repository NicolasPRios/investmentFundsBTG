package com.investmentFundsBTG.investmentFunds.domain.usecase.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void enviarCorreo_ShouldConfigureAndSendEmailCorrectly() {
        String destinado = "cliente@correo.com";
        String asunto = "Confirmación de Suscripción";
        String mensaje = "Bienvenido al fondo de inversión.";

        emailService.enviarCorreo(destinado, asunto, mensaje);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        verify(mailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(destinado, Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals(asunto, sentMessage.getSubject());
        assertEquals(mensaje, sentMessage.getText());
    }
}
