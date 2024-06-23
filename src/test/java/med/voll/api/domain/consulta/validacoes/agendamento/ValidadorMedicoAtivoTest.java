package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ValidadorMedicoAtivoTest {

    @Autowired
    ValidadorMedicoAtivo validador;

    @MockBean
    MedicoRepository repository;

    Medico medicoAtivo;

    @BeforeEach
    void setUp() {
        var dadosEnderecoCadastroMedicoAtivo = new DadosEndereco(
                "logradouro",
                "bairro",
                "12345678",
                "Bebedouro",
                "SP",
                null,
                null);
        var dadosCadastroMedicoAtivo = new DadosCadastroMedico(
                "Médico ativo",
                "medico_ativo@gmail.com",
                "17995-5874",
                "2413",
                Especialidade.DERMATOLOGIA,
                dadosEnderecoCadastroMedicoAtivo
                );
        medicoAtivo = new Medico(dadosCadastroMedicoAtivo);
    }


    @Test
    @DisplayName("Não deve retornar exeção quando o id do médico não for passado")
    void validarCenario1() {
        var dados = new DadosAgendamentoConsulta(
                medicoAtivo.getId(),
                1l,
                LocalDateTime.now(),
                Especialidade.DERMATOLOGIA);

        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    @DisplayName("Não deve retornar exeção quando o id do médico for passado e ele estiver ativo")
    @WithMockUser
    void validarCenario2() {
        var dados = new DadosAgendamentoConsulta(
                1l,
                1l,
                LocalDateTime.now(),
                Especialidade.DERMATOLOGIA);

        when(repository.findAtivoById(any())).thenReturn(medicoAtivo.getAtivo());

        assertDoesNotThrow(() -> validador.validar(dados));
    }

    @Test
    @DisplayName("Deve lançar exeção ValidacaoException quando o id do médico for passado e ele não estiver ativo")
    @WithMockUser
    void validarCenario3() {
        var dados = new DadosAgendamentoConsulta(
                1l,
                1l,
                LocalDateTime.now(),
                Especialidade.DERMATOLOGIA);

        medicoAtivo.excluir();
        when(repository.findAtivoById(any())).thenReturn(medicoAtivo.getAtivo());

        assertThrows(ValidacaoException.class, () -> validador.validar(dados));
    }
}