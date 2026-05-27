# GimnasioBerserkerReal
# Gimnasio Berserker

Sistema de gestión para gimnasios desarrollado con arquitectura de microservicios en Spring Boot. Permite administrar socios, empleados, rutinas, inventario, facturación y ventas comerciales desde una API centralizada.

---

## Descripción del proyecto

Gimnasio Berserker es una aplicación backend compuesta por 7 microservicios independientes que se comunican entre sí mediante Feign Client y se exponen al exterior a través de un API Gateway.

| Microservicio | Puerto | Base de datos       | Función principal                                      |
|---------------|--------|---------------------|--------------------------------------------------------|
| api-gateway   | 8090   | —                   | Enruta todas las peticiones hacia los servicios        |
| Socios        | 8082   | `socios_db`         | Gestión de socios, membresías y asignación de rutinas  |
| Empleados     | 8081   | `empleados_db`      | Gestión de empleados con autenticación básica          |
| Facturación   | 8084   | `facturacion_db`    | Emisión de facturas y registro de pagos                |
| Comercial     | 8083   | `comercial_db`      | Venta de productos y control de stock                  |
| Inventario    | 8085   | `inventario_db`     | Gestión del inventario del gimnasio                    |
| Rutina        | 8086   | `rutina_db`         | Creación de rutinas y ejercicios                       |

---

## Requisitos previos

- Java 21
- Maven 3.9+
- MySQL 8+ (o XAMPP con MySQL activo)
- IntelliJ IDEA (recomendado) u otro IDE compatible con Spring Boot

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/GimnasioBerserkerReal.git
cd GimnasioBerserkerReal
```

### 2. Crear las bases de datos en MySQL

Abre tu cliente MySQL (MySQL Workbench, XAMPP, terminal, etc.) y ejecuta:

```sql
CREATE DATABASE socios_db;
CREATE DATABASE empleados_db;
CREATE DATABASE facturacion_db;
CREATE DATABASE comercial_db;
CREATE DATABASE inventario_db;
CREATE DATABASE rutina_db;
```

### 3. Configurar credenciales de base de datos

En cada microservicio, edita el archivo `src/main/resources/application.properties` y ajusta usuario y contraseña según tu entorno local:

```properties
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

Los microservicios **Socios**, **Facturación** y **Rutina** usan Flyway para crear y poblar las tablas automáticamente al iniciar. Los demás usan `ddl-auto=update`.

---

## Ejecución

Cada microservicio es independiente y debe iniciarse por separado. Se recomienda arrancar primero el **API Gateway**.

### Opción A — Desde IntelliJ IDEA

1. Abre el proyecto raíz en IntelliJ.
2. Ejecuta cada módulo desde su clase principal (`*Application.java`) con el botón **Run**.
3. Orden sugerido de arranque:
   - `api-gateway`
   - `Rutina`
   - `Socios`
   - `Empleados`
   - `Facturacion`
   - `Comercial`
   - `Inventario`

### Opción B — Desde la terminal con Maven

Abre una terminal por cada microservicio y ejecuta:

```bash
# Ejemplo para el microservicio Socios
cd Socios
./mvnw spring-boot:run
```

Repite el comando desde cada carpeta (`api-gateway`, `Empleados`, `Facturacion`, `Comercial`, `Inventario`, `Rutina`).

---

## Uso de la API

Una vez en ejecución, todas las peticiones se envían al API Gateway en `http://localhost:8090`.

| Recurso       | Ruta base                          |
|---------------|------------------------------------|
| Socios        | `http://localhost:8090/api/socios` |
| Empleados     | `http://localhost:8090/api/empleados` |
| Facturación   | `http://localhost:8090/api/facturacion` |
| Comercial     | `http://localhost:8090/api/comercial` |
| Inventario    | `http://localhost:8090/api/inventario` |
| Rutinas       | `http://localhost:8090/api/rutinas` |

Los servicios **Empleados** e **Inventario** requieren autenticación HTTP Basic:
- Usuario: `admin`
- Contraseña: `admin12345`

---

## Tecnologías utilizadas

- **Spring Boot 4.0** — Framework principal
- **Spring Cloud OpenFeign** — Comunicación entre microservicios
- **Spring Cloud Gateway** — API Gateway
- **Spring Security** — Autenticación básica (Empleados e Inventario)
- **Spring Data JPA + Hibernate** — Persistencia de datos
- **Flyway** — Migraciones de base de datos (Socios, Facturación, Rutina)
- **MySQL** — Motor de base de datos
- **Lombok** — Reducción de código boilerplate
- **Maven** — Gestión de dependencias y build
