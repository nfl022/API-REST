package med.voll.api.domain.pacientes;

import med.voll.api.domain.medico.Medicos;

public record DatosListaPaciente(
        Long id,
        String nombre,
        String email,
        String documento
) {

    public DatosListaPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento()
        );
    }


}


