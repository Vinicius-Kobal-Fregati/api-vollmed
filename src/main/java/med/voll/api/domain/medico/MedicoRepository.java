package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    Optional<Medico> findByIdAndAtivoTrue(Long id);

    Optional<Medico> getReferenceByIdAndAtivoTrue(Long id);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.ativo = true
            AND m.especialidade = :especialidade
            AND m.id NOT IN(
                SELECT c.medico.id FROM Consulta c
                WHERE c.data = :data
                AND c.motivoCancelamento is null
            )
            ORDER BY rand()
            limit 1
            """)
    Medico escolherMedicoAleatorioNaData(Especialidade especialidade, LocalDateTime data);

    @Query("""
            SELECT m.ativo 
            FROM Medico m
            WHERE m.id = :id
            """)
    Boolean findAtivoById(Long id);
}
