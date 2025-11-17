# Integração Cal.com - Fluxos Implementados

Este documento descreve a implementação completa da integração com Cal.com conforme especificado no documento `resposta-integração.md`.

## ✅ Implementações Realizadas

### 1. DTOs (Data Transfer Objects)

#### Event Types
- `EventTypeRequest` - Para criação/atualização de Event Types
- `EventTypeResponse` - Resposta do Cal.com para Event Types

#### Bookings
- `BookingResponse` - Detalhes de um agendamento
- `BookingAttendee` - Informações do participante
- `BookingOrganizer` - Informações do organizador  
- `BookingUpdateRequest` - Reagendamento de booking
- `BookingCancelRequest` - Cancelamento de booking
- `BookingListResponse` - Lista paginada de bookings
- `BookingPagination` - Informações de paginação

### 2. Cliente HTTP (CalClient)

Implementado cliente reativo usando WebClient com os seguintes métodos:

#### Event Types
- `createEventType()` - `POST /v2/event-types`
- `listEventTypes()` - `GET /v2/event-types`
- `getEventType()` - `GET /v2/event-types/{id}`
- `updateEventType()` - `PATCH /v2/event-types/{id}`
- `deleteEventType()` - `DELETE /v2/event-types/{id}`

#### Bookings
- `listBookings()` - `GET /v2/bookings` (com filtros)
- `getBooking()` - `GET /v2/bookings/{id}`
- `updateBooking()` - `PATCH /v2/bookings/{id}`
- `cancelBooking()` - `POST /v2/bookings/{id}/cancel`

### 3. Services

#### EventTypeService
Gerencia a sincronização entre imóveis (EstateEntity) e Event Types do Cal.com:
- **Criação automática**: Quando um imóvel é criado, um Event Type é criado no Cal.com
- **Atualização**: Sincroniza alterações nos dados do imóvel
- **Listagem e consulta**: Para painéis administrativos
- **Remoção**: Quando um imóvel é desativado/removido

#### BookingService  
Gerencia operações com agendamentos:
- **Listagem por imóvel**: Filtrar agendamentos de uma localização específica
- **Listagem por usuário**: Agendamentos de um cliente específico
- **Reagendamento**: Atualizar horários via Cal.com API
- **Cancelamento**: Cancelar agendamentos via Cal.com API

### 4. Controllers REST

#### EventTypeController (`/api/event-types`)
- `POST /estate/{estateId}` - Criar Event Type para imóvel
- `PUT /estate/{estateId}` - Atualizar Event Type do imóvel  
- `GET /` - Listar todos os Event Types
- `GET /{eventTypeId}` - Buscar Event Type específico
- `DELETE /estate/{estateId}` - Remover Event Type do imóvel

#### BookingController (`/api/bookings`)
- `GET /estate/{estateId}` - Listar agendamentos por imóvel
- `GET /user/{userId}` - Listar agendamentos por usuário
- `GET /` - Listar todos os agendamentos (com filtros)
- `GET /{bookingId}` - Buscar agendamento específico
- `PATCH /{appointmentId}/reschedule` - Reagendar agendamento
- `POST /{appointmentId}/cancel` - Cancelar agendamento

### 5. Webhook Expandido

O `WebhookService` foi expandido para processar 3 tipos de eventos:

#### BOOKING_CREATED (já existia)
- Cria novo agendamento no banco local
- Armazena `calBookingId` para referência futura

#### BOOKING_RESCHEDULED (novo)
- Busca agendamento por `calBookingId` ou por cliente/horário
- Atualiza dados de horário e duração
- Mantém sincronia com Cal.com

#### BOOKING_CANCELLED (novo)  
- Busca agendamento por `calBookingId` ou por cliente/horário
- Marca status como `CANCELLED`
- Mantém histórico do agendamento

### 6. Mudanças no Banco de Dados

#### EstateEntity
```java
@Column(name = "cal_event_type_id")
private Long calEventTypeId;
```

#### AppointmentEntity
```java
@Column(name = "cal_booking_id") 
private Long calBookingId;
```

#### AppointmentRepository
```java
Optional<AppointmentEntity> findByCalBookingId(Long calBookingId);
List<AppointmentEntity> findByClientAndStatus(UserEntity client, Status status);
Optional<AppointmentEntity> findByClientAndStartDateTime(UserEntity client, LocalDateTime startDateTime);
```

## 🔄 Fluxos de Integração

### Fluxo de Criação de Event Type
1. Imóvel é criado/atualizado no sistema
2. `EventTypeService.createEventTypeForEstate()` é chamado
3. Event Type é criado no Cal.com via API
4. `calEventTypeId` é salvo no `EstateEntity`

### Fluxo de Agendamento (Existente)
1. Cliente agenda via componente React do Cal.com
2. Cal.com dispara webhook `BOOKING_CREATED`  
3. `WebhookService` processa e salva no banco local
4. `calBookingId` é armazenado para futuras operações

### Fluxo de Reagendamento
1. Sistema chama `BookingService.rescheduleBooking()`
2. Faz `PATCH /bookings/{id}` no Cal.com  
3. Cal.com dispara webhook `BOOKING_RESCHEDULED`
4. `WebhookService` atualiza dados no banco local

### Fluxo de Cancelamento
1. Sistema chama `BookingService.cancelBooking()`
2. Faz `POST /bookings/{id}/cancel` no Cal.com
3. Cal.com dispara webhook `BOOKING_CANCELLED`
4. `WebhookService` marca como cancelado no banco local

## ⚙️ Configuração Necessária

### application.yml
```yaml
calcom:
  api:
    base-url: https://api.cal.com
    key: ${CALCOM_API_KEY}

cal:
  webhook:
    secret: ${CAL_WEBHOOK_SECRET}
```

### Variáveis de Ambiente
```
CALCOM_API_KEY=cal_live_xxxxxxxxxxxx
CAL_WEBHOOK_SECRET=your-webhook-secret
```

## 🎯 Próximos Passos

1. **Configurar webhooks no Cal.com** para os eventos:
   - `BOOKING_CREATED` (já configurado)  
   - `BOOKING_RESCHEDULED` (novo)
   - `BOOKING_CANCELLED` (novo)

2. **Migração do banco** para adicionar as colunas:
   - `empreendimento.cal_event_type_id`
   - `visita.cal_booking_id`

3. **Integrar com EstateService** para chamar automaticamente:
   - `EventTypeService.createEventTypeForEstate()` na criação
   - `EventTypeService.updateEventTypeForEstate()` na atualização  
   - `EventTypeService.deleteEventTypeForEstate()` na remoção

4. **Testes** de integração com ambiente de desenvolvimento do Cal.com

## 📚 Documentação da API

A implementação segue as especificações da Cal.com API v2:
- Event Types: https://cal.com/docs/platform/api-reference/event-types
- Bookings: https://cal.com/docs/platform/api-reference/bookings
- Webhooks: https://cal.com/docs/platform/webhooks

Todos os endpoints estão implementados de forma reativa usando WebClient para melhor performance e escalabilidade.