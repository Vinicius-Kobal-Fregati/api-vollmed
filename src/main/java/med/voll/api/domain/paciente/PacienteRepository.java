package med.voll.api.domain.paciente;

import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    Optional<Paciente> findByIdAndAtivoTrue(Long id);

    @Query("""
            SELECT p.ativo 
            FROM Paciente p
            WHERE p.id = :id
            """)
    boolean findAtivoById(Long id);
}
