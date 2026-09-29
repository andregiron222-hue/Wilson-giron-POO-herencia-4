public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo,
                          double tarifaDiaria, double capacidadToneladas) {

        super(placa, marca, modelo, tarifaDiaria);

        if (capacidadToneladas <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        this.capacidadToneladas = capacidadToneladas;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias deben ser mayores que cero."
            );
        }

        double tarifaBase = getTarifaDiaria() * dias;
        double recargo = 100 * capacidadToneladas * dias;

        return tarifaBase + recargo;
    }

    @Override
    public String getTipo() {
        return "Camioneta de carga";
    }

    @Override
    public String getDetalles() {
        return "Capacidad: "
                + String.format("%.2f", capacidadToneladas)
                + " toneladas";
    }
}