package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepository repositrory;

    public void validar(DatosReservaConsulta datos) {
        //eleccion del medico opcional
        if(datos.idMedico() == null) {
            return;
        }
        var medicoEstaActivo = repositrory.findStatusById(datos.idMedico());
        if(!medicoEstaActivo){
            throw new ValidationException("Consulta no puede ser reservada con medico excluido");
        }
    }
}