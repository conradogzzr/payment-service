\# RabbitMQ Messaging Definition



\## Purpose

When a payment status changes, the service publishes an event to RabbitMQ to support 2+ consumers.



\## Topology

\- Exchange (topic, durable): `payments.exchange`

\- Queue (durable): `payments.status-changed.queue`

\- Routing Key: `payments.status-changed`



\## Binding

`payments.status-changed.queue` is bound to `payments.exchange` using routing key `payments.status-changed`.



\## Message Format

\- Content-Type: `application/json`

\- Headers:

&nbsp; - `\_\_TypeId\_\_`: `com.conrado.payment\_service.domain.event.PaymentStatusChangedEvent`



\### Payload fields

\- `paymentId` (string): Payment identifier

\- `oldStatus` (string enum): Previous status

\- `newStatus` (string enum): New status

\- `occurredAt` (timestamp): Event occurrence time (Instant).  

&nbsp; > Note: current implementation may serialize as epoch numeric value.



\## Example Payload

```json

{

&nbsp; "paymentId": "9cd8be24-6400-4153-856c-6ed1eb7ad4e8",

&nbsp; "oldStatus": "PENDING",

&nbsp; "newStatus": "PROCESSING",

&nbsp; "occurredAt": 1771849218.7850818

}



\## Verification (RabbitMQ UI)

1\. Open RabbitMQ Management: `http://localhost:15672` (guest/guest)

2\. Go to \*\*Queues and Streams\*\* → `payments.status-changed.queue`

3\. Use \*\*Get messages\*\* to inspect the published event payload.



\## Implementation reference

\- Topology is declared in: `infrastructure.rabbit.RabbitConfig`

&nbsp; - Exchange: `RabbitConfig.EXCHANGE`

&nbsp; - Queue: `RabbitConfig.QUEUE\_STATUS\_CHANGED`

&nbsp; - Routing Key: `RabbitConfig.ROUTING\_KEY\_STATUS\_CHANGED`

\- Publishing is performed by: `infrastructure.rabbit.RabbitPaymentEventPublisherAdapter`

&nbsp; - Uses `RabbitTemplate.convertAndSend(exchange, routingKey, event)`

