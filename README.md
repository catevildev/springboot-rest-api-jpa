# API REST - Spring Boot com JPA

Este projeto implementa uma **API REST completa** utilizando **Spring Boot** e **JPA/Hibernate**.  
A API gerencia **usuários**, **produtos** e **pedidos**, oferecendo operações CRUD e rotas adicionais específicas para cada entidade.

---

## 📑 Sumário
- [Configuração](#configuração)  
- [Estrutura do Projeto](#estrutura-do-projeto)  
- [Endpoints da API](#endpoints-da-api)  
  - [Usuários](#1-usuariocontroller-apiusuarios)  
  - [Produtos](#2-produtocontroller-apiprodutos)  
  - [Pedidos](#3-pedidocontroller-apipedidos)  
- [Como Executar](#como-executar)  
- [Exemplos de Uso](#exemplos-de-uso)  
- [Status dos Pedidos](#status-dos-pedidos)  
- [Dependências](#dependências)   

---

## ⚙️ Configuração

- **Spring Boot**: `3.5.6`  
- **Java**: `17`  
- **Banco de Dados**: `H2 (em memória)`  
- **JPA/Hibernate**: Persistência de dados  

---

## 📂 Estrutura do Projeto

```
src/main/java/com/catevildev/atividade/
├── entity/           # Entidades JPA
│   ├── Usuario.java
│   ├── Produto.java
│   └── Pedido.java
├── repository/       # Repositories JPA
│   ├── UsuarioRepository.java
│   ├── ProdutoRepository.java
│   └── PedidoRepository.java
├── service/          # Services (lógica de negócio)
│   ├── UsuarioService.java
│   ├── ProdutoService.java
│   └── PedidoService.java
├── controller/       # Controllers REST
│   ├── UsuarioController.java
│   ├── ProdutoController.java
│   └── PedidoController.java
└── AtividadeApplication.java
```

---

## 📌 Endpoints da API

### 1. **UsuarioController** (`/api/usuarios`)

**Rotas CRUD:**
- `POST /api/usuarios` → Criar novo usuário  
- `GET /api/usuarios` → Buscar todos os usuários  
- `GET /api/usuarios/{id}` → Buscar usuário por ID  
- `PUT /api/usuarios/{id}` → Atualizar usuário  
- `DELETE /api/usuarios/{id}` → Deletar usuário  

**Rotas adicionais:**
- `GET /api/usuarios/ativos` → Buscar usuários ativos  
- `GET /api/usuarios/buscar?nome={nome}` → Buscar usuários por nome  
- `PATCH /api/usuarios/{id}/desativar` → Desativar usuário  

---

### 2. **ProdutoController** (`/api/produtos`)

**Rotas CRUD:**
- `POST /api/produtos` → Criar novo produto  
- `GET /api/produtos` → Buscar todos os produtos  
- `GET /api/produtos/{id}` → Buscar produto por ID  
- `PUT /api/produtos/{id}` → Atualizar produto  
- `DELETE /api/produtos/{id}` → Deletar produto  

**Rotas adicionais:**
- `GET /api/produtos/ativos` → Buscar produtos ativos  
- `GET /api/produtos/categoria/{categoria}` → Buscar por categoria  
- `GET /api/produtos/preco?precoMin={min}&precoMax={max}` → Buscar por faixa de preço  
- `PATCH /api/produtos/{id}/estoque?quantidade={qtd}` → Atualizar estoque  
- `GET /api/produtos/estoque-baixo?limite={limite}` → Buscar produtos com estoque baixo  

---

### 3. **PedidoController** (`/api/pedidos`)

**Rotas CRUD:**
- `POST /api/pedidos` → Criar novo pedido  
- `GET /api/pedidos` → Buscar todos os pedidos  
- `GET /api/pedidos/{id}` → Buscar pedido por ID  
- `PUT /api/pedidos/{id}` → Atualizar pedido  
- `DELETE /api/pedidos/{id}` → Deletar pedido  

**Rotas adicionais:**
- `GET /api/pedidos/usuario/{usuarioId}` → Buscar pedidos por usuário  
- `GET /api/pedidos/status/{status}` → Buscar pedidos por status  
- `PATCH /api/pedidos/{id}/status?status={status}` → Atualizar status  
- `PATCH /api/pedidos/{id}/cancelar` → Cancelar pedido  
- `GET /api/pedidos/periodo?dataInicio={data}&dataFim={data}` → Buscar por período  
- `GET /api/pedidos/usuario/{usuarioId}/valor-total` → Calcular valor total por usuário  

---

## 🚀 Como Executar

1. Clone o repositório:  
   ```bash
   git clone https://github.com/catevildev/springboot-rest-api-jpa.git
   cd springboot-rest-api-jpa
   ```
2. Compile e execute o projeto com Maven ou sua IDE:  
   ```bash
   mvn spring-boot:run
   ```
3. Acesse o console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
   - **Usuário:** `sa`  
   - **Senha:** `password`  

4. Teste os endpoints usando **Postman**, **Insomnia** ou **curl**.  

---

## 📖 Exemplos

### Criar um Usuário
```bash
curl -X POST http://localhost:8080/api/usuarios   -H "Content-Type: application/json"   -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "telefone": "11999999999"
  }'
```

### Criar um Produto
```bash
curl -X POST http://localhost:8080/api/produtos   -H "Content-Type: application/json"   -d '{
    "nome": "Notebook",
    "descricao": "Notebook para desenvolvimento",
    "preco": 2500.00,
    "quantidadeEstoque": 10,
    "categoria": "Eletrônicos"
  }'
```

### Criar um Pedido
```bash
curl -X POST http://localhost:8080/api/pedidos   -H "Content-Type: application/json"   -d '{
    "usuario": {"id": 1},
    "valorTotal": 2500.00,
    "observacoes": "Pedido urgente"
  }'
```

---

## 📦 Status dos Pedidos

- `PENDENTE` → Pedido criado, aguardando processamento  
- `PROCESSANDO` → Pedido sendo processado  
- `ENVIADO` → Pedido enviado  
- `ENTREGUE` → Pedido entregue  
- `CANCELADO` → Pedido cancelado  

---

## 📚 Dependências

- Spring Boot Starter Web  
- Spring Boot Starter Data JPA  
- Banco de Dados H2
