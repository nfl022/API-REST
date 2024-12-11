package med.voll.api.domain.medico;

public record DatosListaMedico(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email,
        String telefono
) {
    public DatosListaMedico(Medicos medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad().toString(),
                medico.getDocumento(),
                medico.getEmail(),
                medico.getTelefono()
        );
    }


}