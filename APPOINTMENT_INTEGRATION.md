# Fluxo de Integração Appointment ↔ Cal.com Booking

## Arquitetura do Sistema

Este documento descreve como funciona a integração entre os agendamentos locais (Appointment) e o sistema Cal.com através de webhooks.

## Fluxos de Operação

### 1. Criação de Agendamentos
```
Frontend (Cal.com Component) → Cal.com API → Webhook → AppointmentEntity (Banco)
```

**Processo:**
1. **Frontend**: Usa o componente de agendamento do Cal.com
2. **Cal.com**: Processa o agendamento e o salva
3. **Webhook**: Cal.com envia notificação para `/cal` endpoint
4. **WebhookService**: Persiste o agendamento no banco local como `AppointmentEntity`

**Importante**: O endpoint `POST /api/appointments` retorna status 409 orientando uso do componente Cal.com.

### 2. Reagendamento
```
Frontend → PATCH /api/appointments/{id}/reschedule → AppointmentController → BookingService → Cal.com API → Webhook → AppointmentEntity (Update)
```

**Processo:**
1. **Frontend**: Chama endpoint de reagendamento com novos horários
2. **AppointmentController**: Valida agendamento e chama BookingService diretamente
3. **BookingService**: Envia requisição de reagendamento para Cal.com
4. **Cal.com**: Processa reagendamento
5. **Webhook**: Cal.com notifica sobre a alteração
6. **WebhookService**: Atualiza o agendamento local

**IMPORTANTE**: AppointmentService NÃO faz alterações no banco - apenas consultas e validações.

### 3. Cancelamento
```
Frontend → POST /api/appointments/{id}/cancel → AppointmentController → BookingService → Cal.com API → Webhook → AppointmentEntity (Status=CANCELLED)
```

**Processo:**
1. **Frontend**: Chama endpoint de cancelamento
2. **AppointmentController**: Valida agendamento e chama BookingService diretamente
3. **BookingService**: Envia requisição de cancelamento para Cal.com
4. **Cal.com**: Cancela o booking
5. **Webhook**: Cal.com notifica sobre cancelamento
6. **WebhookService**: Atualiza status do agendamento para CANCELLED

**IMPORTANTE**: AppointmentService NÃO faz alterações no banco - apenas consultas e validações.

### 4. Consultas
```
Frontend → GET /api/appointments/* → AppointmentController → AppointmentService → AppointmentRepository → AppointmentEntity
```

**Processo:**
- Todas as consultas são feitas diretamente no banco local
- Não há comunicação com Cal.com para consultas
- Dados são sempre consistentes devido aos webhooks
- AppointmentService apenas faz consultas (read-only)

## Endpoints Disponíveis

### Consultas (GET)
- `GET /api/appointments` - Lista todos os agendamentos
- `GET /api/appointments/{id}` - Busca agendamento por ID
- `GET /api/appointments/search` - Busca com filtros avançados (Specification)
- `GET /api/appointments/client/{clientId}` - Agendamentos por cliente
- `GET /api/appointments/agent/{agentId}` - Agendamentos por corretor
- `GET /api/appointments/estate/{estateId}` - Agendamentos por imóvel

### Operações (Redirecionam para Cal.com)
- ✅ `PATCH /api/appointments/{id}/reschedule` - Reagenda via Cal.com
- ✅ `POST /api/appointments/{id}/cancel` - Cancela via Cal.com

### Operações Bloqueadas (Devem ser feitas via Cal.com)
- 🚫 `POST /api/appointments/{id}/confirm` - Retorna 405 (confirme via Cal.com)
- 🚫 `POST /api/appointments/{id}/complete` - Retorna 405 (finalize via Cal.com)

### Utilitários
- `GET /api/appointments/{appointmentId}/booking` - Busca booking Cal.com associado
- `GET /api/appointments/{appointmentId}/cal-status` - Status da integração

## Filtros Avançados (Specification)

O endpoint `/search` suporta os seguintes query parameters:

### Filtros Básicos
- `clientId` - ID do cliente
- `estateAgentId` - ID do corretor
- `estateId` - ID do imóvel
- `status` - Status do agendamento (PENDING, CONFIRMED, CANCELLED, COMPLETED)

### Filtros de Data
- `startDate` - Data/hora início (ISO format)
- `endDate` - Data/hora fim (ISO format)

### Filtros de Texto
- `clientEmail` - Email do cliente (busca parcial)
- `agentEmail` - Email do corretor (busca parcial)
- `estateTitle` - Título do imóvel (busca parcial)

### Filtros Especiais
- `hasCalBooking` - true/false - Se possui integração Cal.com
- `onlyActive` - true/false - Apenas agendamentos não cancelados

### Paginação e Ordenação
- `page` - Número da página (padrão: 0)
- `size` - Tamanho da página (padrão: 10)
- `sortBy` - Campo para ordenação (padrão: startDateTime)
- `sortDir` - Direção da ordenação: asc/desc (padrão: desc)

## Exemplo de Uso

### Buscar agendamentos de um cliente em um período
```
GET /api/appointments/search?clientEmail=joao@email.com&startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59&page=0&size=20
```

### Buscar agendamentos confirmados de um imóvel
```
GET /api/appointments/search?estateId=123&status=CONFIRMED&hasCalBooking=true
```

### Reagendar um agendamento
```
PATCH /api/appointments/456/reschedule
Content-Type: application/json

{
  "startDateTime": "2024-02-15T14:00:00",
  "endDateTime": "2024-02-15T15:00:00"
}
```

## Vantagens desta Arquitetura

1. **Consistência**: Webhooks garantem dados sempre atualizados
2. **Performance**: Consultas são rápidas (banco local)
3. **Confiabilidade**: Cal.com é a fonte da verdade para operações
4. **Flexibilidade**: Filtros avançados com Specification
5. **Separação de Responsabilidades**: Cada sistema faz o que sabe fazer melhor

## Considerações Importantes

- **Cal.com ID**: Todo agendamento integrado possui `calBookingId`
- **Estados**: Agendamentos podem existir sem Cal.com (casos especiais)
- **Webhooks**: São essenciais para manter consistência
- **Erro de Integração**: Se Cal.com falha, o agendamento local não é alterado
- **Auditoria**: Logs detalhados em todos os pontos de integração
- **🚨 REGRA FUNDAMENTAL**: AppointmentService NUNCA altera dados no banco - apenas consultas
- **🚨 ALTERAÇÕES**: Apenas via webhook ou redirecionamento direto para BookingService no Controller
- **🚨 OPERAÇÕES**: Controller chama BookingService diretamente para reagendamento/cancelamento