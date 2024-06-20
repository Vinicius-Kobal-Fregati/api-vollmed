package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
        @NotBlank(message = "O logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "O bairro é obrigatório")
        String bairro,
        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "O CEP está fora do formato padrão")
        String cep,
        @NotBlank(message = "A cidade é obrigatória")
        String cidade,
        @NotBlank(message = "A UF é obrigatória")
        String uf,
        String complemento,
        String numero) {
}
