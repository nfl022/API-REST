package med.voll.api.domain.pacientes;

import med.voll.api.domain.medico.Medicos;
import med.voll.api.domain.pacientes.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {


    Page<Paciente> findByStatusTrue(Pageable paginacion);

    @Query(value = """
            select p.status
            from Pacientes p
            where
            p.id = :idPaciente
            """, nativeQuery = true)
    Boolean findActivoById(Long idPaciente);
}
