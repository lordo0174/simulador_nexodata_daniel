# Simulador de planificación · NexoData

> PSP · Tema 2 · Procesos · 2.º A DAM · **Dorin Daniel Radulescu Toea**

## 1. Qué hace

El programa permite simular tres algoritmos de planificación de CPU:

FCFS (First Come, First Served)
SJF (Shortest Job First)
Round Robin (RR) con quantum configurable

El simulador permite obtener un diagrama de Gantt, métricas de planificación, cambios de contexto y una traza opcional de la ejecución.

## 2. Cómo compilar y ejecutar

**Desde la terminal**, en la carpeta del proyecto:

```bash
javac -encoding UTF-8 -d out src/planificador/*.java (ppara compilar)
java -cp out planificador.Main datos/ejemplo_clase.csv rr 2 --traza (para ejecutar)
```

**Desde IntelliJ IDEA:**
Para ejecutar el proyecto desde IntelliJ IDEA se han seguido estos pasos:

1. **Configuración del JDK**
   - Se ha utilizado **JDK 17 o superior**.
   - En IntelliJ IDEA se ha seleccionado el JDK desde:
     `File → Project Structure → Project → SDK`.

2. **Configuración de la ejecución**
   - Se ha creado una configuración de tipo **Application**.
   - Como clase principal se ha seleccionado:
     `Planificador.Main`

3. **Argumentos del programa**
   - Se han indicado los argumentos necesarios según la ejecución que se quiera realizar.
   - Por ejemplo, para ejecutar todos los algoritmos con un quantum de 2:

   ```text
   datos/ejemplo_clase.csv todos 2

<img width="1366" height="728" alt="image" src="https://github.com/user-attachments/assets/25707461-f424-4f5e-902a-896a96a588de" />


## 3. Diseño

## 3. Diseño

El proyecto está organizado en varias clases, cada una con una responsabilidad concreta. De esta forma, el código queda separado y es más fácil de entender y modificar.

### Clases principales

- **Main:** es el punto de entrada del programa. Lee los argumentos introducidos por el usuario, carga los procesos y ejecuta el algoritmo seleccionado.

- **Proceso:** representa un proceso del sistema. Guarda información como el nombre, tiempo de llegada, ráfaga, tiempo restante, estado y las métricas obtenidas durante la simulación.

- **EstadoProceso:** es un `enum` que define los diferentes estados de un proceso: `NUEVO`, `LISTO`, `EJECUCION`, `BLOQUEADO` y `TERMINADO`.

- **LectorProcesos:** se encarga de leer los ficheros CSV y convertir cada línea en un objeto `Proceso`. También comprueba que los datos sean correctos.

- **Algoritmo:** es una interfaz común para los diferentes algoritmos de planificación. Define las operaciones necesarias para seleccionar el siguiente proceso.

- **FCFS:** implementa el algoritmo First Come, First Served y selecciona los procesos siguiendo el orden de llegada.

- **SJF:** implementa Shortest Job First y selecciona el proceso preparado con la ráfaga más corta.

- **RoundRobin:** implementa Round Robin y utiliza un quantum configurable para repartir el tiempo de CPU entre los procesos.

- **Simulador:** contiene la lógica principal de la simulación. Controla el tiempo, las llegadas de procesos, la cola de procesos preparados, la ejecución, el quantum, el diagrama de Gantt y los cambios de contexto.

- **Resultado:** almacena los resultados obtenidos después de ejecutar un algoritmo, incluyendo los procesos, el Gantt y el número de cambios de contexto.

### Organización

Las clases se han separado para que cada una tenga una función concreta. `Main` se encarga de iniciar el programa, `LectorProcesos` de leer los datos, las clases de algoritmos de decidir qué proceso ejecutar y `Simulador` de controlar la ejecución.

Esta organización permite añadir o modificar algoritmos de planificación sin tener que cambiar toda la lógica del programa.

## 4. Verificación (tarea 4)

### 4.1 verificacion.csv resuelto a mano

El fichero `verificacion.csv` contiene los siguientes procesos:

| Proceso | Llegada | Ráfaga |
|---|---:|---:|
| P1 | 0 | 6 |
| P2 | 1 | 4 |
| P3 | 2 | 2 |
| P4 | 4 | 5 |
| P5 | 6 | 1 |

---

#### FCFS

FCFS ejecuta los procesos según su orden de llegada.

**Diagrama de Gantt:**

```text
| P1 | P1 | P1 | P1 | P1 | P1 | P2 | P2 | P2 | P2 | P3 | P3 | P4 | P4 | P4 | P4 | P4 | P5 |
0    1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18

### 4.2 Comparación con el programa

La resolución manual coincide con los resultados obtenidos por el simulador.

Se han comprobado el diagrama de Gantt, el orden de los procesos, las métricas de finalización, retorno, espera y respuesta, así como los cambios de contexto.

No fue necesario realizar ninguna corrección en los resultados de `verificacion.csv`.

### 4.3 hueco.csv

El fichero `hueco.csv` contiene los siguientes procesos:

| Proceso | Llegada | Ráfaga |
|---|---:|---:|
| X | 0 | 2 |
| Y | 5 | 3 |
| Z | 5 | 1 |
| W | 6 | 2 |

El proceso **X** termina en el instante 2. Los procesos **Y** y **Z** no llegan hasta el instante 5, por lo que entre los instantes **2 y 5 no hay ningún proceso listo para ejecutar**.

Por este motivo, la CPU permanece inactiva durante esos tres instantes:

```text
t=2 → IDLE
t=3 → IDLE
t=4 → IDLE

## 5. Análisis y recomendación a NexoData (tarea 5)

| nocturno.csv | Retorno medio | Espera media | Respuesta media | Cambios de contexto |
|---|---|---|---|---|
| FCFS | | | | |
| SJF | | | | |
| RR q=1 | | | | |
| RR q=2 | | | | |
| RR q=4 | | | | |

1. [Respuesta 1]
2. [Respuesta 2]
3. [Respuesta 3]
4. [Respuesta 4]
5. [Respuesta 5]
6. [Respuesta 6, con la captura comentada de los estados de tu sistema]

### Recomendación

Para NexoData recomiendo utilizar **Round Robin con un quantum de 2 unidades**.  
Aunque SJF consigue los mejores valores de espera y retorno, Round Robin ofrece una respuesta mucho más rápida para los procesos que llegan al sistema.  
Con `q=1` se consigue una respuesta todavía mejor, pero se producen 22 cambios de contexto, aumentando la sobrecarga.  
Con `q=4` disminuyen los cambios de contexto, pero la respuesta empeora demasiado.  
El quantum de 2 ofrece un equilibrio adecuado entre capacidad de respuesta y número de cambios de contexto.  
Además, permite repartir la CPU entre procesos largos y cortos sin que un proceso monopolice la CPU durante demasiado tiempo.  
Por estas razones, **RR con q=2 es la opción más equilibrada para NexoData**.
