# Parking App

Fija del examen parcial de HAMP

## Enunciado

AutoPark Peru S.A.C. administra estacionamientos en varias zonas de la ciudad. Cada playa tiene una tarifa por hora y una cantidad limitada de espacios.
Los vehiculos pueden ser autos, camionetas o motos. Las camionetas pagan un recargo, los socios del estacionamiento tienen descuento y ninguna estadia cobra menos de un monto minimo.
Hoy las estadias se anotan en un cuaderno y ha generado problemas: se reciben vehiculos cuando la playa ya esta llena y los cobros se calculan mal porque las reglas son varias. Por eso decidio contar con un sistema propio.

El sistema se llama ParkingApp API y ya existe como proyecto Spring Boot (Java 21). Es una API REST con arquitectura por capas: los controller reciben las solicitudes HTTP, los service aplican las reglas de negocio y los repository acceden a la base de datos PostgreSQL, donde se guardan los datos. Incluye las clases del modelo Vehicle, ParkingLot y Stay (Stay recibe las llaves foraneas de Vehicle y ParkingLot) y los datos iniciales. Falta construir las funcionalidades que se le asignan en este ejercicio.

El administrador usa el sistema a traves de una aplicacion cliente (frontend, por ahora Postman) que envia solicitudes HTTP en formato JSON a la API. El administrador registra las estadias y consulta lo cobrado a cada vehiculo.

## Implementación

### 1. Clases a implementar

Estas son las clases que debe implementar.

- **repository:** StayRepository
- **service:** StayService, ReportService
- **controller:** StayController, ReportController
- **dto:** StayRequestDTO, StayResponseDTO, StaySummaryDTO y VehicleReportResponseDTO

El paquete mapper (MapStruct) ya viene implementado.

### 2. Funcionalidades

Las siguientes funcionalidades deben ser implementadas.

- **Registrar estadia:** recibe vehicleId, lotId y hours. Devuelve la estadia creada con su cobro (este dato no se ingresa, se calcula segun las reglas de negocio).
- **Reporte de un vehiculo:** recibe el id del vehiculo. Devuelve la placa, el total de estadias, las horas totales, el total cobrado, el cobro promedio, el cobro mayor y la lista de sus estadias (playa, horas y cobro, de mayor a menor cobro).
- **DTO:** para conocer los campos de cada DTO, revise los ejemplos de request y response (Ejemplos de request y response, se encuentra en la ultima pagina de este documento).

### 3. Reglas de Negocio

- **Cobro de la estadia (charge):** se calcula con estas 4 reglas, aplicadas en este orden.
  - **Base:** charge = hours x hourlyRate (horas de la estadia por la tarifa por hora de la playa).

  - **Camioneta:** si type es CAMIONETA, charge = charge x 1.20 (recargo del 20%).

  - **Socio:** si member es true, charge = charge x 0.90 (descuento del 10%).

  - **Minimo:** si charge es menor que S/ 5.00, charge = 5.00.

  - **Ejemplo 1:** CAMIONETA, no socio, 10 horas, hourlyRate S/ 5: 10 x 5 = 50; 50 x 1.20 = 60. charge = S/ 60.00.

  - **Ejemplo 2:** AUTO, socio, 2 horas, hourlyRate S/ 2: 2 x 2 = 4; 4 x 0.90 = 3.60; como es menor que 5, charge = S/ 5.00.

- **Espacios de la playa (availableSpaces):** cada estadia ocupa un espacio libre de la playa.
  - **Si availableSpaces es 0:** la estadia no se registra (error 409).

  - **Si no:** la estadia se registra y availableSpaces = availableSpaces - 1.

  - **Ejemplo:** con availableSpaces 3, una estadia se registra y quedan 2; con availableSpaces 0, la estadia no se registra.

- **Horas válidas (hours):** las horas de una estadía deben estar entre 1 y 24
  - **Si hours es menor que 1 o mayor que 24:** la estadia no se registra (error 400).

  - **Ejemplo:** hours 30 o hours 0 no se registran; hours 24 si se registra

- **Reporte de un vehiculo (VehicleReportResponseDTO):** se calcula con las estadias registradas del vehiculo.
  - **Total de estadias (totalStays):** cantidad de estadias del vehiculo.

  - **Horas totales (totalHours):** suma de hours de las estadias del vehiculo.

  - **Total cobrado (totalCharged):** suma de charge de las estadias del vehiculo.

  - **Cobro promedio (averageCharge):** totalCharged dividido entre totalStays, redondeado a 2 decimales.

  - **Cobro mayor (highestCharge):** el charge mas alto de las estadias del vehiculo.

  - **Listado de estadias (List<StaySummaryDTO> stays):** por cada estadia debe tener el nombre de la playa (lotName), las horas (hours) y el cobro (charge), de mayor a menor cobro.

  - **Ejemplo:** estadia 1 de 2 horas con charge S/ 12.00 y estadia 2 de 4 horas con charge S/31.00: totalStays 2, totalHours 6, totalCharged S/ 43.00, averageCharge S/ 21.50 y highestCharge S/ 31.00; en la lista va primero la de S/ 31.00.

#### 3.1. Respuestas de Error

- `400`: hours menor que 1 o mayor que 24.
- `409`: la playa no tiene espacios libres ("La playa no tiene espacios libres"). Es el caso alternativo del registro de estadia (el reporte solo tiene el caso exitoso).

### 4. Endpoints de Postman

Ejemplos de request y response

#### a) Registrar Estadía `POST /stays`

##### Registrar Estadía - caso 1 (auto)

- Request (body)

  ```json
  {
    "vehicleId": 1,
    "lotId": 1,
    "hours": 5
  }
  ```

- Response Esperado: `201 Created`

  ```json
  {
    "id": 2,
    "plate": "ABC-123",
    "lotName": "Playa Centro",
    "hours": 5,
    "charge": 25.0
  }
  ```

##### Registrar Estadía - caso 2 (camioneta)

- Request (body)

  ```json
  {
    "vehicleId": 2,
    "lotId": 1,
    "hours": 5
  }
  ```

- Response Esperado: `201 Created`

  ```json
  {
    "id": 3,
    "plate": "XYZ-789",
    "lotName": "Playa Centro",
    "hours": 5,
    "charge": 30.0
  }
  ```

##### Registrar Estadía - caso 3 (socio, cobro mínimo)

- Request (body)

  ```json
  {
    "vehicleId": 3,
    "lotId": 1,
    "hours": 1
  }
  ```

- Response Esperado: `201 Created`

  ```json
  {
    "id": 4,
    "plate": "MOT-456",
    "lotName": "Playa Centro",
    "hours": 1,
    "charge": 5.0
  }
  ```

##### Registrar Estadía - caso alternativo (409)

- Request (body)

  ```json
  {
    "vehicleId": 1,
    "lotId": 2,
    "hours": 2
  }
  ```

- Response Esperado: `409 Conflict`

  ```json
  {
    "status": 409,
    "message": "La playa no tiene espacios libres"
  }
  ```

#### b) Reporte de un vehiculo `GET /reports/vehicles/{vehicleId}/summary`

##### Reporte de un Vehiculo - caso exitoso

- Request: `GET /reports/vehicles/1/summary`

- Response Esperado: `200 OK`

  ```json
  {
    "plate": "ABC-123",
    "totalStays": 2,
    "totalHours": 8,
    "totalCharged": 40.0,
    "averageCharge": 20.0,
    "highestCharge": 25.0,
    "stays": [
      { "lotName": "Playa Centro", "hours": 5, "charge": 25.0 },
      { "lotName": "Playa Centro", "hours": 3, "charge": 15.0 }
    ]
  }
  ```
