
package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medicos, Long> {
    Page<Medicos> findByStatusTrue(Pageable paginacion);

    @Query(value = """
            SELECT * FROM medicos m
            WHERE
            m.status = 1
            AND m.especialidad = :especialidad
            AND m.id NOT IN (
                SELECT c.medico_id FROM consultas c
                WHERE c.fecha = :fecha
            )
            ORDER BY RAND()
            LIMIT 1
            """, nativeQuery = true)
    Medicos elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad especialidad, LocalDateTime fecha);



    @Query("""
            select m.status
            from medicos m
            where
            m.id = :idMedico
            """)
    boolean findStatusById(Long idMedico);


}
