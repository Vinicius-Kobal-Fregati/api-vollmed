package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email está fora do formato padrão")
        String email,
        @NotBlank(message = "O telefone é obrigatório")
        String telefone,
        @NotBlank(message = "O CRM é obrigatório")
        @Pattern(regexp = "\\d{4,6}", message = "Formato de CRM está inválido") // 4 a 6 dígitos
        String crm,
        @NotNull(message = "A especialidade é obrigatória")
        Especialidade especialidade,
        @NotNull(message = "O endereço é obrigatório")
        @Valid
        DadosEndereco endereco) {

}
