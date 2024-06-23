package med.voll.api.domain.consulta.validacoes.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidadorHorarioAntecedenciaTest {

    @Autowired
    ValidadorHorarioAntecedencia validador;

    @Test
    @DisplayName("Deveria não lançar exceção se o horário da consulta for mais de 30 minutos após o horário atual")
    void validarCenario1() {
        var dados = new DadosAgendamentoConsulta(1l, 1l, LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    @DisplayName("Deveria lançar a exceção ValidacaoException se o horário da consulta for em menos de 30 minutos")
    void validarCenario2() {
        var dados = new DadosAgendamentoConsulta(1l, 1l, LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }
}