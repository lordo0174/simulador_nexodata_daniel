package planificador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorProcesos {

    public static List<Proceso> leer(Path fichero) throws IOException {
        List<Proceso> procesos = new ArrayList<>();

        List<String> lineas = Files.readAllLines(fichero);

        int orden = 0;
        int numeroLinea = 0;

        for (String linea : lineas) {
            numeroLinea++;

            linea = linea.trim();

            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            String[] partes = linea.split(";");

            if (partes.length != 3) {
                throw new IllegalArgumentException(
                        "Línea " + numeroLinea
                                + ": formato incorrecto. Se esperaba nombre;llegada;ráfaga"
                );
            }

            String nombre = partes[0].trim();

            if (nombre.isEmpty()) {
                throw new IllegalArgumentException(
                        "Línea " + numeroLinea + ": el nombre no puede estar vacío"
                );
            }

            int llegada;
            int rafaga;

            try {
                llegada = Integer.parseInt(partes[1].trim());
                rafaga = Integer.parseInt(partes[2].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Línea " + numeroLinea
                                + ": llegada y ráfaga deben ser números enteros"
                );
            }

            if (llegada < 0) {
                throw new IllegalArgumentException(
                        "Línea " + numeroLinea
                                + ": la llegada no puede ser negativa"
                );
            }

            if (rafaga <= 0) {
                throw new IllegalArgumentException(
                        "Línea " + numeroLinea
                                + ": la ráfaga debe ser mayor que cero"
                );
            }

            procesos.add(new Proceso(nombre, llegada, rafaga, orden));
            orden++;
        }

        return procesos;
    }
}