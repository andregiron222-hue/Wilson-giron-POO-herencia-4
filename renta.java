import java.util.ArrayList;

public class RentaMovil {
    private ArrayList<Vehiculo> vehiculos;
    private double ingresosAcumulados;

    public RentaMovil() {
        vehiculos = new ArrayList<>();
        ingresosAcumulados = 0;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return false;
        }

        if (buscarVehiculo(vehiculo.getPlaca()) != null) {
            return false;
        }

        vehiculos.add(vehiculo);
        return true;
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null) {
            return null;
        }

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return vehiculo;
            }
        }

        return null;
    }

    public void mostrarFlota() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
            return;
        }

        System.out.println("\n========== FLOTA ==========");

        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    public void cotizar(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println("No existe un vehiculo con esa placa.");
            return;
        }

        if (dias <= 0) {
            System.out.println("Los dias deben ser enteros positivos.");
            return;
        }

        double total = vehiculo.calcularCosto(dias);

        System.out.println("\n========== COTIZACION ==========");
        System.out.println(vehiculo);
        System.out.println("Dias solicitados: " + dias);
        System.out.printf("Costo total: Q%.2f%n", total);

        System.out.println(
                "Disponibilidad: "
                        + (vehiculo.isDisponible()
                        ? "Disponible"
                        : "Actualmente alquilado")
        );

        System.out.println(
                "Esta cotizacion NO modifica los ingresos ni la disponibilidad."
        );
    }

    public double obtenerCostoAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null || dias <= 0) {
            return -1;
        }

        return vehiculo.calcularCosto(dias);
    }

    public boolean confirmarAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println("No existe un vehiculo con esa placa.");
            return false;
        }

        if (dias <= 0) {
            System.out.println("Los dias deben ser enteros positivos.");
            return false;
        }

        if (!vehiculo.isDisponible()) {
            System.out.println("El vehiculo ya se encuentra alquilado.");
            return false;
        }

        double total = vehiculo.calcularCosto(dias);

        vehiculo.alquilar();
        ingresosAcumulados += total;

        System.out.println("Alquiler confirmado correctamente.");
        System.out.printf("Monto cobrado: Q%.2f%n", total);

        return true;
    }

    public boolean registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println("No existe un vehiculo con esa placa.");
            return false;
        }

        if (vehiculo.isDisponible()) {
            System.out.println(
                    "El vehiculo ya esta disponible. No se puede devolver."
            );
            return false;
        }

        vehiculo.devolver();

        System.out.println("Devolucion registrada correctamente.");
        System.out.println("El vehiculo vuelve a estar disponible.");

        return true;
    }

    public void mostrarReporte() {
        int disponibles = 0;

        int autosDisponibles = 0;
        int autosAlquilados = 0;

        int motosDisponibles = 0;
        int motosAlquiladas = 0;

        int camionetasDisponibles = 0;
        int camionetasAlquiladas = 0;

        for (Vehiculo vehiculo : vehiculos) {

            if (vehiculo.isDisponible()) {
                disponibles++;
            }

            if (vehiculo instanceof Automovil) {
                if (vehiculo.isDisponible()) {
                    autosDisponibles++;
                } else {
                    autosAlquilados++;
                }

            } else if (vehiculo instanceof Motocicleta) {
                if (vehiculo.isDisponible()) {
                    motosDisponibles++;
                } else {
                    motosAlquiladas++;
                }

            } else if (vehiculo instanceof CamionetaCarga) {
                if (vehiculo.isDisponible()) {
                    camionetasDisponibles++;
                } else {
                    camionetasAlquiladas++;
                }
            }
        }

        int alquilados = vehiculos.size() - disponibles;

        System.out.println("\n========== REPORTE GENERAL ==========");

        System.out.println("Vehiculos registrados: " + vehiculos.size());
        System.out.println("Vehiculos disponibles: " + disponibles);
        System.out.println("Vehiculos alquilados: " + alquilados);

        System.out.println("\n--- Automoviles ---");
        System.out.println("Disponibles: " + autosDisponibles);
        System.out.println("Alquilados: " + autosAlquilados);

        System.out.println("\n--- Motocicletas ---");
        System.out.println("Disponibles: " + motosDisponibles);
        System.out.println("Alquilados: " + motosAlquiladas);

        System.out.println("\n--- Camionetas de carga ---");
        System.out.println("Disponibles: " + camionetasDisponibles);
        System.out.println("Alquilados: " + camionetasAlquiladas);

        System.out.printf(
                "%nIngresos acumulados: Q%.2f%n",
                ingresosAcumulados
        );
    }

    public double getIngresosAcumulados() {
        return ingresosAcumulados;
    }
}