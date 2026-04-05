# 📘 Alterações Realizadas na API de Usuários

Este documento lista as modificações realizadas nos principais arquivos do projeto Spring Boot responsável pelo gerenciamento de usuários.

---

## 📁 Arquivos Modificados

### 🔹 `UserController.java`
**Local:** `penelope.corretagem.penelopeapirest.controller`

#### ✅ Alterações:
- Adição de novos endpoints REST:
    - `POST /users` – Criação de usuário
    - `GET /users` – Listagem de todos os usuários
    - `PATCH /users/{id}` – Atualização parcial de usuário
    - `DELETE /users/{id}` – Exclusão de usuário
- Integração com `UserService` para delegação da lógica de negócios.
- Remoção da validação `@Valid` (estava comentada ou não aplicada nos endpoints, como no `@RequestBody`).

---

### 🔹 `UserRequest.java`
**Local:** `penelope.corretagem.penelopeapirest.dto`

#### ✅ Alterações:
- Anotações de validação adicionadas (comentadas):
    - `@NotBlank`, `@NotNull`, `@CPF`, `@Email`, `@Size`
- Inclusão da anotação:
    - `@JsonInclude(JsonInclude.Include.NON_NULL)` – Ignora campos `null` no JSON
- Define o formato de entrada (DTO) para criação/atualização de usuários.

---

### 🔹 `UserResponse.java`
**Local:** `penelope.corretagem.penelopeapirest.dto`

#### ✅ Alterações:
- Criação da classe `UserResponse`, responsável por representar os dados de saída do usuário.
- Campos incluídos:
    - `nome`, `cpf`, `email`, `dtNascimento`, `rendaMensal`

---

### 🔹 `UserService.java`
**Local:** `penelope.corretagem.penelopeapirest.service`

#### ✅ Alterações:
- Criação dos métodos de serviço:
    - `addUser(UserRequest)`
    - `showAllUser()`
    - `updateUser(Long id, UserRequest)`
    - `deleteUser(Long id)`
- Implementação de logs com SLF4J (`log.info`, `log.warn`) para rastreabilidade.
- Tratamento de "usuário não encontrado" com lançamento de exceção e log apropriado.
- Criação do método auxiliar `applyUserUpdates()` para aplicar atualizações parciais nos campos da entidade.

---

### 🔹 `UserEntity.java`
**Local:** `penelope.corretagem.penelopeapirest.entity`

#### ✅ Alterações:
- Criação da entidade JPA `UserEntity`.
- Campos:
    - `id` (autogerado)
    - `nome`
    - `cpf`
    - `email`
    - `dtNascimento`
    - `rendaMensal`
- Anotações do JPA utilizadas:
    - `@Entity`, `@Id`, `@GeneratedValue`

---

### 🔹 `UserMapper.java`
**Local:** `penelope.corretagem.penelopeapirest.mapper`

#### ✅ Alterações:
- Uso do **MapStruct** para mapear entre DTOs e entidade.
- Métodos declarados:
    - `toUserEntity(UserRequest userRequest)`
    - `toUserResponse(UserJpaEntity user)`
- Anotação:
    - `@Mapper(componentModel = "spring")` – Permite injeção automática via Spring

---

### 🔹 `UserRepository.java`
**Local:** `penelope.corretagem.penelopeapirest.repository`

#### ✅ Alterações:
- Criação do repositório que estende `JpaRepository<UserEntity, Long>`.
- Permite operações de persistência padrão (CRUD) com Spring Data JPA.

---
