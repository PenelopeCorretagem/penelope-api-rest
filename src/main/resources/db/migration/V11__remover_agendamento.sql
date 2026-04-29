-- ============================================================
-- V6: Remover tabela agendamento do monolito
-- O gerenciamento de agendamentos foi delegado ao cal-service.
-- ============================================================

DROP TABLE IF EXISTS agendamento;
