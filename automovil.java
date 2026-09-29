public class Automovil extends Vehiculo {
    private int cantidadPasajeros;
    private boolean automatico;

    public Automovil(String placa, String marca, String modelo,
                     double tarifaDiaria, int cantidadPasajeros,
                     boolean automatico) {

        super(placa, marca, modelo, tarifaDiaria);

        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de pasajeros debe ser mayor que cero."
            );
        }

        this.cantidadPasajeros = cantidadPasajeros;
        this.automatico = automatico;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    @Override
    public double calcularCosto(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException(
                    "Los dias deben ser mayores que cero."
            );
        }

        double total = getTarifaDiaria() * dias;

        if (automatico) {
            total += 50 * dias;
        }

        return total;
    }

    @Override
    public String getTipo() {
        return "Automovil";
    }

    @Override
    public String getDetalles() {
        return "Pasajeros: " + cantidadPasajeros
                + " | Transmision: "
                + (automatico ? "Automatica" : "Manual");
    }
}