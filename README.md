# 📊 Matomo API Client para Java

**Una librería Java ligera y con tipos para interactuar fácilmente con la API de Matomo (Analytics).**

[Read in English](readme/README.en.md)

-----

## 📚 Tabla de Contenidos

* [✨ Descripción](#-descripción)
* [⭐ Características Clave](#-características-clave)
* [🛠️ Instalación](#️-instalación)
* [⚙️ Configuración](#️-configuración)
* [▶️ Uso](#️-uso)
* [🤝 Contribución](#-contribución)
* [📄 Licencia](#-licencia)
* [👤 Autores](#-autores)

-----

## ✨ Descripción

Esta librería permite a las aplicaciones **Java** comunicarse de manera sencilla con **Matomo**, facilitando la realización de solicitudes a su API de forma **tipada** y robusta. Es la herramienta ideal para integrar estadísticas de visitas, acciones y otras métricas analíticas directamente en tus proyectos Java.

-----

## ⭐ Características Clave

* **Compatibilidad:** Soporte para Matomo **v4** y **v5**.
* **Seguridad:** Autenticación segura mediante el parámetro `token_auth`.
* **Tipificación:** Uso de **tipos específicos** para la respuesta de módulos clave (como `VisitsSummary`), mejorando la seguridad y legibilidad del código.
* **Manejo de Errores:** Gestión de éxito y error mediante el **patrón *Result***, lo que obliga a manejar ambos escenarios de forma clara.
* **Integración Sencilla:** Fácil de incluir en proyectos Java/Kotlin a través de **Gradle** o **Maven**.

-----

## 🛠️ Instalación

Puedes añadir esta librería a tu proyecto usando Gradle o Maven.

### Con Gradle

```gradle
dependencies {
    implementation 'com.javi:matomo-api-client:1.0.0'
}
```

### Con Maven

```xml
<dependency>
    <groupId>com.javi</groupId>
    <artifactId>matomo-api-client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

-----

## ⚙️ Configuración

Para comenzar, inicializa el cliente con la **URL base** de tu instancia Matomo, el `token_auth` y la versión de Matomo que estés utilizando.

```java
 MatomoClient matomoClient = new MatomoClient.Builder()
        .baseUrl("https://demo.matomo.cloud") // Tu URL de Matomo
        .tokenAuth("anonymous") // Tu token_auth (o 'anonymous' para APIs públicas)
        .matomoVersion(4) // Versión de tu Matomo (ej. 4 o 5)
        .build();
```

-----

## ▶️ Uso

A continuación, se muestra un ejemplo completo para obtener el número total de visitas (`GetVisits`) para el día de hoy utilizando el módulo `VisitsSummary`:

```java
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicialización del cliente
        MatomoClient matomoClient = new MatomoClient.Builder()
                .baseUrl("https://demo.matomo.cloud")
                .tokenAuth("anonymous")
                .matomoVersion(4)
                .build();

        LocalDate today = LocalDate.now();

        // 2. Realizar la llamada a la API
        Result<VisitSummary.GetVisits, String> getVisits = matomoClient
                .visitsSummaryModule()
                .getVisits(
                    1, // idSite
                    PeriodEnum.DAY, // Periodo (DAY, WEEK, MONTH, YEAR, RANGE)
                    List.of(today), // Fechas
                    null // Segmento opcional
                );

        // 3. Manejar el resultado (patrón Result)
        if (getVisits.isSuccess()) {
            // El resultado de la API fue exitoso
            System.out.println("Visitas de hoy: " + getVisits.getSuccessValue().getNb_visits());
        }
        if (getVisits.isFailure()) {
            // Hubo un error en la comunicación o la API
            System.err.println("Error al obtener visitas: " + getVisits.getError());
        }
    }
}
```

> **Nota:** El tipo `Result<VisitSummary.GetVisits, String>` es clave para manejar de forma explícita y segura tanto el éxito (valor tipado) como el error (mensaje de *String*).

## 📅 Razón para Usar `List<LocalDate>`

El diseño de utilizar una lista de fechas se implementó por las siguientes razones clave:

### 1\. Soporte Directo para Rangos de Fechas (`RANGE`)

El caso más complejo es cuando el periodo es **`RANGE`**. La API de Matomo requiere que el parámetro `date` se envíe como un par de fechas separadas por una coma (`YYYY-MM-DD,YYYY-MM-DD`).

* **Implementación:** Al recibir una lista de fechas (`dates`), el constructor puede:
    1.  Ordenar la lista (`sortedDates`).
    2.  Tomar la **fecha más antigua** como inicio del rango.
    3.  Tomar la **fecha más reciente** como fin del rango.
    4.  Construir la cadena `dateString` con el formato `start,end` necesario para el *range* de Matomo.

### 2\. Consistencia y Unificación del Constructor

Si se usaran parámetros separados (por ejemplo, `LocalDate startDate, LocalDate endDate`), el constructor sería confuso para los periodos simples (`DAY`, `WEEK`, `MONTH`, `YEAR`), donde solo se necesita una única fecha.

* **Diseño Unificado:** Al exigir siempre una `List<LocalDate>`, se logra una **interfaz de llamada única** para el constructor, independientemente del `PeriodEnum` usado:
    * **DAY/WEEK/MONTH/YEAR:** Se pasa una lista con una sola fecha, y el código toma solo la primera.
    * **RANGE:** Se pasa una lista con al menos dos fechas, y el código gestiona la creación del rango `start,end`.

### 3\. Flexibilidad y Comprobación de Errores

El uso de la lista permite incluir validaciones robustas y lógicas claras dentro del constructor:

* **Validación de Rango:** El código puede verificar si se pasa el periodo `RANGE`, lanzar una `IllegalArgumentException` si la lista tiene menos de dos fechas, garantizando que la solicitud a la API será válida.
  ```java
  if (period == PeriodEnum.RANGE && dates.size() < 2) {
      throw new IllegalArgumentException("Range period requires at least two dates");
  }
  ```
* **Manejo de Orden:** La lista se ordena automáticamente (`sortedDates.sort(Comparator.naturalOrder())`), asegurando que las fechas `start` y `end` del rango siempre sean la más antigua y la más reciente, respectivamente, incluso si el usuario las pasa en orden inverso.
-----

## 🤝 Contribución

¡Las contribuciones son bienvenidas\! Si deseas mejorar la librería, añadir nuevos módulos de la API o corregir *bugs*, por favor sigue estos pasos:

1.  Realiza un **fork** del proyecto.
2.  Crea una nueva rama para tu *feature* o corrección (ej. `feature/nuevo-endpoint`).
3.  Asegúrate de que tus cambios incluyen **tests unitarios** y la **documentación** necesaria.
4.  Crea un **Pull Request**.

-----

## 📄 Licencia


Este proyecto está bajo la licencia MIT. Consulta [LICENSE](LICENSE) para más detalles.

-----

## 👤 Autores

* **Javier A. Roque Sañudo** – Desarrollador principal.
    * GitHub: [@javicaib](https://github.com/javicaib)
