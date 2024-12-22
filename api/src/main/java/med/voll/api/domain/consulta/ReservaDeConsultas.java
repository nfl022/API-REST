package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medicos;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.Medicos;
import med.voll.api.domain.pacientes.PacienteRepository;
import med.voll.api.domain.pacientes.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){

        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidationException("No existe un paciente con el id informado");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidationException("No existe un médico con el id informado");
        }

        //validaciones
        validadores.forEach(v -> v.validar(datos));

        var medico = elegirMedico(datos);
        if(medico == null){
            throw new ValidationException("No existe un médico disponible en ese horario");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    private Medicos elegirMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidationException("Es necesario elegir una especialidad cuando no se elige un médico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }
}