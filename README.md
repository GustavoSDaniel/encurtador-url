# 🔗 Encurtador de URL

API para encurtamento de links, criada com **Java 17 + Spring Boot 3**, usando **PostgreSQL**, **Redis** (cache), **Flyway**, **Swagger**, **Docker**, e contendo **testes unitários com JUnit + Mockito**.

Este projeto recebe uma URL longa e retorna uma versão curta, gerando códigos únicos com **Hashids**.
Também armazena os links no banco, permite consultar todos, buscar o original e deletar.

---

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3.5**
* **Spring Web**
* **Spring Data JPA**
* **Spring Cache + Redis**
* **PostgreSQL**
* **Flyway**
* **Hashids**
* **Docker + Docker Compose**
* **RedisInsight** (GUI do Redis)
* **PgAdmin 4** (GUI do Postgres)
* **Swagger OpenAPI**
* **JUnit 5 + Mockito**

---

# 🏗 Arquitetura do Projeto

```
src/main/java/com.gsuatavosdaniel.encurtador_url
│
├── config/                # Configurações (Redis, Cache, JPA, OpenAPI)
├── exception/             # Tratamento global de exceções
├── links/                 # Controller, Service, Mapper, Repository
│   ├── LinkController
│   ├── LinkService / Impl
│   ├── LinkMapper
│   ├── LinkRequest / LinkResponse
│   ├── Links (entidade)
│
└── EncurtadorUrlApplication.java
```

---

# ▶️ Como rodar o projeto (modo DEV sem Docker)

### Requisitos:

* Java 17
* Maven
* PostgreSQL rodando
* Redis rodando

### 1️⃣ Configurar suas variáveis no `application.yaml`

Ajuste host, user e senha do banco.

### 2️⃣ Rodar o projeto

Via IntelliJ: **Run ▶️ EncurtadorUrlApplication**

Ou via terminal:

```sh
mvn spring-boot:run
```

Aplicação sobe em:

```
http://localhost:5050
```

Swagger:

```
http://localhost:5050/swagger-ui.html
```

---

# 🐳 Como rodar com Docker (recomendado)

Tudo é automatizado pelo docker-compose → API + Postgres + Redis + PgAdmin + RedisInsight.

### 1️⃣ Criar o arquivo `.env` na raiz:

```env
POSTGRES_DB=encurtador
POSTGRES_USER=admin
POSTGRES_PASSWORD=admin
MY_SALT=123456
DOMINIO_APP=http://localhost:5050
PGADMIN_EMAIL=admin@admin.com
PGADMIN_PASSWORD=admin
```

### 2️⃣ Subir tudo:

```sh
docker compose up -d --build
```

### Serviços expostos:

| Serviço      | Porta |
| ------------ | ----- |
| API          | 5050  |
| PostgreSQL   | 5051  |
| PgAdmin      | 5052  |
| Redis        | 5053  |
| RedisInsight | 5054  |

---

# 🖥 Interfaces Gráficas

### 🟦 PgAdmin — PostgreSQL GUI

```
http://localhost:5052
```

Conexão:

* Host: postgres
* User: ${POSTGRES_USER}
* Password: ${POSTGRES_PASSWORD}

### 🔴 RedisInsight — Redis GUI (igual ao pgAdmin)

```
http://localhost:5054
```

Conexão:

* Host: redis
* Port: 6379

---

# 📌 Endpoints da API

## ➕ POST http://localhost:5050

Encurta uma URL longa.

```json
{
  "longUrl": "https://www.linkedin.com/in/gustavo-silva-daniel-a778b7331/"
}
```

Retorno:

```json
{
  "urlCurta": "http://localhost:5050/AbC123Xy"
}
```

## 🔁 GET /{codigo}

http://localhost:5050/{urlencurtada}

Retorna a URL original e redireciona.

## 📄 GET /{allLinks}

http://localhost:5050/allLinks

Lista todas as URLs cadastradas.

## ❌ DELETE

http://localhost:5050/{id}

Deleta um link pelo ID.

---

# 🧪 Testes Unitários

Localizados em:

```
src/test/java/com.gsuatavosdaniel.encurtador_url/links
```

Rodar testes:

```sh
mvn test
```

Inclui testes para:

* salvar link
* buscar original
* listar
* deletar
* mock Hashids
* cobertura service + mapper

---

# 🐋 Dockerfile da aplicação

```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/encurtador-url-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

# 🧠 Fluxo Interno da API

1. Recebe URL longa
2. Verifica se já existe
3. Salva
4. Hashids gera código único
5. Armazena no Redis
6. Retorna URL curta
7. Consulta usa Redis antes do banco
8. Redireciona para URL original

---

# 📚 Swagger

```
http://localhost:5050/swagger-ui.html
```

---

# 👨‍💻 Autor

**Gustavo Silva Daniel**  
Desenvolvedor Java / Backend

- GitHub: [https://github.com/GustavoSDaniel](https://github.com/GustavoSDaniel)
- LinkedIn: [https://www.linkedin.com/in/gustavo-silva-daniel-a778b7331/](https://www.linkedin.com/in/gustavo-silva-daniel-a778b7331/)

