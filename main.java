import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static RentaMovil empresa = new RentaMovil();

    public static void main(String[] args) {

        cargarVehiculosIniciales();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;

                case 2:
                    empresa.mostrarFlota();
                    break;

                case 3:
                    cotizarVehiculo();
                    break;

                case 4:
                    alquilarVehiculo();
                    break;

                case 5:
                    devolverVehiculo();
                    break;

                case 6:
                    empresa.mostrarReporte();
                    break;

                case 0:
                    System.out.println("\nGracias por utilizar RentaMovil.");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void cargarVehiculosIniciales() {

        empresa.registrarVehiculo(
                new Automovil(
                        "AUT001",
                        "Toyota",
                        "Corolla",
                        250,
                        5,
                        true
                )
        );

        empresa.registrarVehiculo(
                new Automovil(
                        "AUT002",
                        "Honda",
                        "Civic",
                        225,
                        5,
                        false
                )
        );

        empresa.registrarVehiculo(
                new Motocicleta(
                        "MOT001",
                        "Yamaha",
                        "FZ",
                        100,
                        150
                )
        );

        empresa.registrarVehiculo(
                new Motocicleta(
                        "MOT002",
                        "Kawasaki",
                        "Ninja",
                        175,
                        300
                )
        );

        empresa.registrarVehiculo(
                new CamionetaCarga(
                        "CAM001",
                        "Toyota",
                        "Hilux",
                        200,
                        1.5
                )
        );

        empresa.registrarVehiculo(
                new CamionetaCarga(
                        "CAM002",
                        "Ford",
                        "Ranger",
                        250,
                        2.0
                )
        );
    }

    private static void mostrarMenu() {
        System.out.println("\n================================");
        System.out.println("          RENTAMOVIL");
        System.out.println("================================");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar alquiler");
        System.out.println("4. Confirmar alquiler");
        System.out.println("5. Registrar devolucion");
        System.out.println("6. Reporte general");
        System.out.println("0. Salir");
        System.out.println("================================");
    }

    private static void registrarVehiculo() {

        System.out.println("\n===== REGISTRAR VEHICULO =====");

        System.out.println("1. Automovil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de carga");

        int tipo = leerEntero("Seleccione el tipo: ");

        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de vehiculo invalido.");
            return;
        }

        System.out.print("Placa: ");
        String placa = scanner.nextLine().trim();

        if (placa.isEmpty()) {
            System.out.println("La placa no puede estar vacia.");
            return;
        }

        if (empresa.buscarVehiculo(placa) != null) {
            System.out.println("Ya existe un vehiculo con esa placa.");
            return;
        }

        System.out.print("Marca: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();

        double tarifa = leerDoublePositivo(
                "Tarifa diaria: Q"
        );

        try {

            Vehiculo nuevoVehiculo;

            switch (tipo) {

                case 1:
                    int pasajeros = leerEnteroPositivo(
                            "Cantidad de pasajeros: "
                    );

                    boolean automatico = leerSiNo(
                            "¿Es automatico? (s/n): "
                    );

                    nuevoVehiculo = new Automovil(
                            placa,
                            marca,
                            modelo,
                            tarifa,
                            pasajeros,
                            automatico
                    );

                    break;

                case 2:
                    int cilindraje = leerEnteroPositivo(
                            "Cilindraje (cc): "
                    );

                    nuevoVehiculo = new Motocicleta(
                            placa,
                            marca,
                            modelo,
                            tarifa,
                            cilindraje
                    );

                    break;

                case 3:
                    double capacidad = leerDoublePositivo(
                            "Capacidad maxima en toneladas: "
                    );

                    nuevoVehiculo = new CamionetaCarga(
                            placa,
                            marca,
                            modelo,
                            tarifa,
                            capacidad
                    );

                    break;

                default:
                    return;
            }

            if (empresa.registrarVehiculo(nuevoVehiculo)) {
                System.out.println(
                        "Vehiculo registrado correctamente."
                );
            } else {
                System.out.println(
                        "No fue posible registrar el vehiculo."
                );
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cotizarVehiculo() {

        System.out.println("\n===== COTIZAR ALQUILER =====");

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine();

        int dias = leerEnteroPositivo(
                "Cantidad de dias: "
        );

        empresa.cotizar(placa, dias);
    }

    private static void alquilarVehiculo() {

        System.out.println("\n===== CONFIRMAR ALQUILER =====");

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine();

        Vehiculo vehiculo = empresa.buscarVehiculo(placa);

        if (vehiculo == null) {
            System.out.println(
                    "No existe un vehiculo con esa placa."
            );
            return;
        }

        if (!vehiculo.isDisponible()) {
            System.out.println(
                    "El vehiculo ya se encuentra alquilado."
            );
            return;
        }

        int dias = leerEnteroPositivo(
                "Cantidad de dias: "
        );

        double total = empresa.obtenerCostoAlquiler(
                placa,
                dias
        );

        System.out.println("\nVehiculo:");
        System.out.println(vehiculo);

        System.out.printf(
                "Total del alquiler: Q%.2f%n",
                total
        );

        boolean confirmar = leerSiNo(
                "¿Desea confirmar el alquiler? (s/n): "
        );

        if (confirmar) {
            empresa.confirmarAlquiler(placa, dias);
        } else {
            System.out.println(
                    "Alquiler cancelado. No se realizo ningun cobro."
            );
        }
    }

    private static void devolverVehiculo() {

        System.out.println("\n===== DEVOLVER VEHICULO =====");

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine();

        empresa.registrarDevolucion(placa);
    }

    private static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada invalida. Debe ingresar un numero entero."
                );
            }
        }
    }

    private static int leerEnteroPositivo(String mensaje) {

        while (true) {

            int numero = leerEntero(mensaje);

            if (numero > 0) {
                return numero;
            }

            System.out.println(
                    "El valor debe ser mayor que cero."
            );
        }
    }

    private static double leerDoublePositivo(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String entrada = scanner.nextLine();

            try {

                double numero = Double.parseDouble(entrada);

                if (numero > 0) {
                    return numero;
                }

                System.out.println(
                        "El valor debe ser mayor que cero."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Entrada invalida. Debe ingresar un numero."
                );
            }
        }
    }

    private static boolean leerSiNo(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String respuesta =
                    scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")
                    || respuesta.equals("si")) {

                return true;
            }

            if (respuesta.equals("n")
                    || respuesta.equals("no")) {

                return false;
            }

            System.out.println(
                    "Ingrese 's' para si o 'n' para no."
            );
        }
    }
}