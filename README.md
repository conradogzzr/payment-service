# Payment Service (Prueba técnica Java)

Servicio REST para administración de pagos (alta, consulta de estatus y actualización de estatus).  
Persistencia en **MongoDB** y publicación de evento a **RabbitMQ** al cambiar el estatus (pensado para múltiples consumers).

## Consideraciones de seguridad
En caso de requerirse, se integraría Spring Security (JWT/OAuth2) sin afectar el dominio gracias a la separación por capas (api/application/domain/infrastructure).
## Stack
- Java 17
- Spring Boot 3.5.x
- MongoDB
- RabbitMQ
- Maven Wrapper

## Arquitectura
Enfoque **Hexagonal / Ports & Adapters**:
- **domain**: modelo y reglas de negocio (`Payment`, `PaymentStatus`, policy de transiciones).
- **application**: casos de uso/orquestación (`PaymentApplicationService`) y puertos (`PaymentRepositoryPort`, `PaymentEventPublisherPort`).
- **infrastructure**: adapters concretos para Mongo (`MongoPaymentRepositoryAdapter`) y Rabbit (`RabbitPaymentEventPublisherAdapter` + `RabbitConfig`).
- **api**: controllers, DTOs y manejo de errores.

### Patrones y SOLID (resumen)
- **Ports & Adapters** (Hexagonal): puertos en `application.port.out` y adapters en `infrastructure`.
- **Dependency Injection**: inversión de control con Spring.
- **Factory Method**: `Payment.create(...)` controla creación e invariantes.
- **Policy**: `PaymentStatusTransitionPolicy` centraliza transiciones válidas.
- **Event-driven**: `PaymentStatusChangedEvent` publicado a RabbitMQ.
- **SOLID**: SRP por capas, DIP con puertos, ISP con interfaces pequeñas, OCP con policy, LSP al sustituir adapters.

## Requisitos
- Docker Desktop (con docker compose)
- JDK 17 (Temurin recomendado)

## Levantar dependencias (Mongo + RabbitMQ)
Desde `C:\dev\payment-service\docker`:

```bash
docker compose up -d
docker compose ps