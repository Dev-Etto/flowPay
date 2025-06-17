# FlowPay - Sistema de Processamento de Pagamentos

## Sobre o Projeto

FlowPay é um motor transacional assíncrono para processamento de pagamentos que permite a criação de contas bancárias e a realização de transações financeiras de forma eficiente e segura. O sistema utiliza uma arquitetura baseada em mensageria para garantir alta disponibilidade e resiliência mesmo em cenários de alta demanda.

## Funcionalidades Principais

- Criação e gerenciamento de contas bancárias
- Processamento assíncrono de transações financeiras
- Consulta de status de transações
- Tratamento robusto de exceções

## Tecnologias

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Data JPA**: Persistência de dados
- **Spring Security**: Autenticação e autorização
- **Spring AMQP**: Integração com RabbitMQ
- **Jakarta Validation**: Validação de dados
- **PostgreSQL**: Banco de dados relacional
- **RabbitMQ**: Sistema de mensageria
- **Lombok**: Redução de código boilerplate
- **SpringDoc OpenAPI**: Documentação automática da API
- **Docker**: Containerização de serviços

## Requisitos

- JDK 17
- Maven
- Docker e Docker Compose

## Instalação e Execução

### 1. Clone o repositório

```bash
git clone [url-do-repositorio]
cd flowpay
```

### 2. Inicie os serviços com Docker Compose

```bash
docker-compose up -d
```

Este comando iniciará:
- PostgreSQL na porta 5432
- RabbitMQ na porta 5672 (console de gerenciamento na porta 15672)

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

## Documentação da API

A documentação interativa da API está disponível através do Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## Endpoints da API

### Contas

- **POST** `/api/v1/accounts` - Cria uma nova conta bancária

### Transações

- **POST** `/api/v1/transactions` - Inicia uma nova transação financeira
- **GET** `/api/v1/transactions/{id}` - Consulta o status de uma transação

## Arquitetura

O projeto segue uma arquitetura em camadas:

- **Controller**: Responsável por receber as requisições HTTP e retornar respostas
- **Service**: Contém a lógica de negócio
- **Repository**: Gerencia o acesso aos dados
- **Model**: Define as entidades do domínio
- **Consumer**: Processa mensagens da fila RabbitMQ
- **Exception**: Trata exceções de forma centralizada

## Fluxo de Transação

1. Cliente envia solicitação de transação via API REST
2. Sistema valida os dados e envia para processamento assíncrono via RabbitMQ
3. Consumidor processa a transação e atualiza seu status
4. Cliente pode consultar o status da transação a qualquer momento

### Representação Visual do Fluxo

```
           [Requisição: POST /api/v1/transactions]
[CLIENTE] ---------------------------------- --> [1. Controller]
    ^                                                    |
    |                                                    | [chama o serviço]
    | [7. Resposta Imediata: 202 Accepted]               v
    |                                              [2. Service] -+------> [3. Banco de Dados (Postgres)]
    |                                          |   - Valida    |          (Salva Tx com status PENDING)
    +------------------------------------------+   - Responde  |
                                                               |               |
                                                               +-------------> [4. RabbitMQ]
                                                                 (Envia ID)      (Fila: "transactions.queue")
                                                                                             |
                                                                                             | [5. Mensagem é entregue]
                                                                                             |
                                                                                             v
                                                                                       [6. Consumer]
                                                                                       |   - Ouve a fila
                                                                                       |   - Debita/Credita
                                                                                       |   - Atualiza status para COMPLETED
                                                                                       v
                                                                                [7. Banco de Dados (Postgres)]
                                                                                    (Salva os novos saldos e o status da Tx)
```

## Configurações

### Banco de Dados (PostgreSQL)

- **Host**: localhost
- **Porta**: 5432
- **Banco**: flowpay-db
- **Usuário**: user
- **Senha**: password

### RabbitMQ

- **Host**: localhost
- **Porta**: 5672
- **Console**: http://localhost:15672
- **Usuário**: guest
- **Senha**: guest

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).

## Autor

João Neto - Dev-Etto
