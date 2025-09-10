# 💬 Chat Microservice

Este microservicio permite enviar y recibir mensajes entre usuarios utilizando **Spring Boot**. Incluye **endpoints REST**, se puede probar con **Postman** y cuenta con **documentación interactiva** mediante **Swagger UI**.

---

## 🚀 Setup

Sigue estos pasos para ejecutar el proyecto localmente:

### 1. Clonar el repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd <NOMBRE_DEL_PROYECTO>
``` 
2. Compilar con Maven
```bash
mvn clean install
```
3. Ejecutar el microservicio
```bash
mvn spring-boot:run
```
4. Acceder a Swagger UI
Abre tu navegador y visita:

```bash
http://localhost:8080/swagger-ui/index.html
```
Aquí podrás ver todos los endpoints documentados y probarlos directamente desde el navegador.

📌 Endpoints

1️⃣ Obtener mensajes de un usuario
Método: GET

URL: /messages/{receiver}

Ejemplo:

```bash
GET http://localhost:8080/messages/Alice
Respuesta esperada (JSON):
```
```json
[
  { "sender": "Bob", "receiver": "Alice", "content": "Hola Alice!" },
  { "sender": "Charlie", "receiver": "Alice", "content": "Qué tal Alice?" }
]
```
Uso en Swagger UI:

Haz clic en el endpoint GET /messages/{receiver}.

Presiona Try it out.

Ingresa el valor de receiver (ej. Alice).

Haz clic en Execute para ver la respuesta.

Uso en Postman:

Selecciona método GET.

URL: http://localhost:8080/messages/Alice

Presiona Send y revisa el JSON que retorna.

2️⃣ Enviar un mensaje
Método: POST

URL: /messages

Body (JSON):

```json
{
  "sender": "David",
  "receiver": "Alice",
  "content": "Hola Alice desde Postman!"
}
```
Respuesta esperada: 200 OK (mensaje agregado a la lista)

Uso en Swagger UI:

Haz clic en el endpoint POST /messages.

Presiona Try it out.

Rellena el JSON con los campos sender, receiver y content.

Haz clic en Execute.

Uso en Postman:

Selecciona método POST.

URL: http://localhost:8080/messages

En Body, selecciona raw → JSON y pega el JSON de ejemplo.

Presiona Send y revisa que la respuesta sea 200 OK.

⚡ Pruebas rápidas
Después de un POST, siempre realiza un GET para el mismo receiver y verifica que aparezca el nuevo mensaje.

Puedes alternar entre Postman y Swagger UI para validar los endpoints.

🛠 Tecnologías utilizadas
Java 17

Spring Boot

Maven

Swagger UI

Postman
