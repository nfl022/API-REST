package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medicos;
import med.voll.api.domain.pacientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListaPaciente>> listapacientes(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(pacienteRepository.findByStatusTrue(paginacion)
                .map(DatosListaPaciente::new));
    }


    @PutMapping
    @Transactional
    public ResponseEntity DatosActualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(new DatosRegistroPaciente(paciente.getId(), paciente.getNombre(), paciente.getDocumento(),
                paciente.getEmail(), paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity EliminarMedico (@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<DatosRegistroPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                  UriComponentsBuilder uriComponentsBuilder){
    Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosRegistroPaciente datosRegistroPaciente1 = new DatosRegistroPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumento(),
        new DatosDireccion(paciente.getDireccion().getCalle(),paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRegistroPaciente);
}
}