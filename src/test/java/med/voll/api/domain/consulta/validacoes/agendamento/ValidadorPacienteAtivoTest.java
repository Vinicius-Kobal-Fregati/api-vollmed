package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.paciente.PacienteRepository;
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
class ValidadorPacienteAtivoTest {

    @Autowired
    ValidadorPacienteAtivo validador;

    @MockBean
    PacienteRepository repository;

    DadosAgendamentoConsulta dados = new DadosAgendamentoConsulta(1l, 1l, LocalDateTime.now(), Especialidade.DERMATOLOGIA);

    @Test
    @DisplayName("Não deve lançar exceção quando o paciente for ativo")
    void validarCenario1() {
        when(repository.findAtivoById(any())).thenReturn(true);

        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    @DisplayName("Deve lançar exceção ValidationException quando o paciente for inativo")
    void validarCenario2() {
        when(repository.findAtivoById(any())).thenReturn(false);

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }
}