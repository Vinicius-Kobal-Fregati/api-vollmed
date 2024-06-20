package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
         @NotNull(message = "O email é obrigatório")
         Long id,
         String nome,
         String telefone,
         @Valid
         DadosEndereco endereco) {
}
