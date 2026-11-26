# G.U.N. Ops API 🦔💨

API REST desenvolvida em Java com Spring Boot para gerenciamento de agentes, missões e Esmeraldas do Caos da Guardian Units of Nations (G.U.N.).

## Tecnologias Utilizadas
- Java 21 & Spring Boot 3
- Spring Data JPA & Banco H2
- Bean Validation (`@Valid`)
- JUnit 5 & Mockito
- GitHub Actions (CI)

## Endpoints Principais
- `POST /api/agents` - Cadastra um novo agente
- `GET /api/agents` - Lista todos os agentes
- `POST /api/missions` - Cria uma nova missão
- `POST /api/missions/{missionId}/assign/{agentId}` - Aloca um agente disponível na missão
- `PATCH /api/missions/{missionId}/complete` - Conclui uma missão (exige mínimo de 2 agentes para nível `OMEGA`)
- `POST /api/emeralds` - Registra uma Esmeralda do Caos
- `PATCH /api/emeralds/{emeraldId}/assign/{agentId}` - Vincula uma esmeralda a um agente
