package med.voll.api.medico;

public record DatosListaMedico(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email,
        String telefono
) {
    public DatosListaMedico(Medico medico) {
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