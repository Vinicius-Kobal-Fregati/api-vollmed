package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ValidadorMedicoComConsultaNoMesmoHorarioTest {

    @Autowired
    ValidadorMedicoComConsultaNoMesmoHorario validador;

    @MockBean
    ConsultaRepository repository;

    DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(1l, 1l, LocalDateTime.now(), Especialidade.DERMATOLOGIA);

    @Test
    @DisplayName("Não deve lançar exceção quando o médico não houver outra consulta no dia e horário")
    void validarCenario1() {
        when(repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(any(), any())).thenReturn(false);

        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    @DisplayName("Deve lançar a exceção ValidationException quando o médico tiver outra consulta no dia e horário")
    void validarCenario2() {
        when(repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(any(), any())).thenReturn(true);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }
}