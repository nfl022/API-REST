package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarDoctores(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                  UriComponentsBuilder uriComponentsBuilder){
       Medicos medico = medicoRepository.save(new Medicos(datosRegistroMedico));
       DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(),
               medico.getEmail(), medico.getTelefono(),
               new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                       medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                       medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

/*
    @GetMapping
    public List<DatosListaMedico> listamedicos() {
        return medicoRepository.findAll()
                .stream()
                .map(DatosListaMedico::new)
                .toList();
    }
*/
//paginacion
     /*    return medicoRepository.findAll(paginacion)
                .map(DatosListaMedico::new);*/
    @GetMapping
    public ResponseEntity<Page<DatosListaMedico>>  listamedicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(medicoRepository.findByStatusTrue(paginacion)
                .map(DatosListaMedico::new));


    }

    @PutMapping
    @Transactional
    public ResponseEntity DatosActualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medicos medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(),
                medico.getEmail(), medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }
    //Delete logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity EliminarMedico (@PathVariable Long id) {
        Medicos medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico  (@PathVariable Long id) {
        Medicos medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(),
                medico.getEmail(), medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }

    //Delete base datos
   /* public void EliminarMedico (@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/


}

