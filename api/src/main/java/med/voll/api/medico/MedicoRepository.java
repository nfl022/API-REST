package med.voll.api.medico;

import med.voll.api.controller.MedicoController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {


    Page<Medico> findByStatusTrue(Pageable paginacion);
}
