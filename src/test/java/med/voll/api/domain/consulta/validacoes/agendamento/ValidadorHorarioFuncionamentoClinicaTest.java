package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidadorHorarioFuncionamentoClinicaTest {

    @Autowired
    ValidadorHorarioFuncionamentoClinica validador;

    Long id = 1l;
    Especialidade especialidade = Especialidade.CARDIOLOGIA;

    @Test
    @DisplayName("Não deveria lançar a exceção ValidacaoException ao marcar em dia útil entre às 7:00 e 18:00")
    void validar1() {
        var dadosSituacao1 = new DadosAgendamentoConsulta(
                id,
                id,
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(7),
                especialidade);
        var dadosSituacao2 = new DadosAgendamentoConsulta(
                id,
                id,
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).withHour(18),
                especialidade);

        assertDoesNotThrow(() -> validador.validar(dadosSituacao1));
        assertDoesNotThrow(() -> validador.validar(dadosSituacao2));
    }

    @Test
    @DisplayName("Deveria lançar a exceção ValidacaoException ao marcar consulta no domingo")
    void validar2() {
        var dados = new DadosAgendamentoConsulta(
                id,
                id,
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)),
                especialidade);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }

    @Test
    @DisplayName("Deveria lançar a exceção ValidacaoException ao marcar consulta antes das 7:00")
    void validar3() {
        var dados = new DadosAgendamentoConsulta(
                id,
                id,
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(6),
                especialidade);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }

    @Test
    @DisplayName("Deveria lançar a exceção ValidacaoException ao marcar consulta após às 18:00")
    void validar4() {
        var dados = new DadosAgendamentoConsulta(
                id,
                id,
                LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(19),
                especialidade);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }
}