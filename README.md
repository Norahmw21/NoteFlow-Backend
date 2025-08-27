
# 🛠️ NoteFlow — Backend (Spring Boot)

**NoteFlow-Backend** powers the NoteFlow desktop app with secure authentication, notes, favorites, to-dos, and profile APIs. It’s built with **Spring Boot 3**, **PostgreSQL**, and **Spring Security (JWT)**.

Frontend repo → **[NoteFlow-Frontend](https://github.com/Norahmw21/NoteFlow-Frontend)**

Project demo → **[Video](https://www.canva.com/design/DAGxK9Peo_E/NWPbT8BwOSOVT2sMx37KWQ/watch?utm_content=DAGxK9Peo_E&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h3287cf69b3)**

---

## ✨ Backend Features

* **🔐 Auth (JWT)**

  * Register, Login, and `GET /auth/me`
  * BCrypt password hashing
  * `Authorization: Bearer <token>` for protected routes

* **🗂 Notes & Folders**

  * CRUD for folders and notes (text & drawing)
  * Mark/Unmark **favorites**

* **✅ To-Do List**

  * CRUD tasks with `startDate`, `endDate`, `status`, and `importance`

* **👤 User Profile**

  * Read & update profile fields (username, email, phone, avatar)

* **🤖 AI**

  *  `/chat` endpoint to forward requests to **Ollama** 
  * Easy key/URL configuration via properties

---

## 🧰 Tech Stack

* **Java 17+**, **Spring Boot 3.x**, **Maven**
* **Spring Web**, **Spring Security (JWT)**, **Spring Data JPA**
* **PostgreSQL**
*  **Ollama**

---

## 🏛 Architecture (High-Level)

* **Controller** → **Service** → **Repository** layers
* Entities :

  * `User`, `Folder`, `Note` (`type=TEXT|DRAWING`), `ToDoTask`, `Favorite`
* CORS configured for the JavaFX frontend

---


## ✅ Prerequisites

* **Java 17+**
* **Maven**
* **PostgreSQL** running locally (or Docker)
*  **Ollama**

---

## ⚙️ Configuration

Create a database and user:

```sql
CREATE DATABASE noteflow_db;
CREATE USER noteflow_user WITH PASSWORD 'strong_password';
GRANT ALL PRIVILEGES ON DATABASE noteflow_db TO noteflow_user;
```

Set your `src/main/resources/application.properties`:

```properties
# --- Server ---
server.port=8080

# --- Datasource ---
spring.datasource.url=jdbc:postgresql://localhost:5432/noteflow_db
spring.datasource.username=noteflow_user
spring.datasource.password=strong_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# --- Security / JWT ---
app.jwt.secret=change_me_to_a_long_random_secret
app.jwt.exp-minutes=60

# Ollama (local)
ollama.baseUrl=http://localhost:11434
# Default model when none provided by client
ollama.model=llama3

```

> 💡 Secrets can be set via environment variables as well. Spring will map
> `APP_JWT_SECRET`, etc., to the corresponding properties if you prefer env-based config.

---

## ▶️ Run Locally

```bash
# From the repo root
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080/api`

---

## 🔒 Security

* **BCrypt** password hashing
* **JWT** stateless auth

---

## 🤝 Contributors

* **[Norah Alwabel](https://github.com/Norahmw21)**
* **[Raghad Alhelal](https://github.com/Raghadlh)**
* **[Rouba Alharbi](https://github.com/Rubabdran)**
* **[Noura Altuwaim](https://github.com/tunourah)**
