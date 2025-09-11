// script.js

let receiver = "Empresa1";
const chatInput = document.getElementById('chat-input');

chatInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        sendChatMessage();
    }
});

function abrirChat(id) {
    receiver = "Empresa" + id;
    document.getElementById('chat-receiver').textContent = receiver;
    document.getElementById('chat-bar').style.right = '0px';
    loadChat();
}

function cerrarChat() {
    document.getElementById('chat-bar').style.right = '-350px';
}

// Función para cargar mensajes
async function loadChat() {
    try {
        const res = await fetch(`/messages/conversation/conv1`);
        const messages = await res.json();
        const chatDiv = document.getElementById('chat-messages');

        // Detectar si el usuario está cerca del final
        const isAtBottom = chatDiv.scrollHeight - chatDiv.scrollTop - chatDiv.clientHeight < 30;

        chatDiv.innerHTML = '';
        let lastDate = '';

        const currentRole = localStorage.getItem("role"); // CLIENT o COMPANY

        messages.forEach(m => {
            const messageDate = new Date(m.createdAt ?? m.timestamp)
                .toLocaleDateString('es-CL', { day: 'numeric', month: 'long', year: 'numeric' });

            // Mostrar la fecha si es diferente de la última
            if (messageDate !== lastDate) {
                const dateDivider = document.createElement('div');
                dateDivider.className = 'text-center text-muted my-2';
                dateDivider.textContent = `---- ${messageDate} ----`;
                chatDiv.appendChild(dateDivider);
                lastDate = messageDate;
            }

            const msgWrapper = document.createElement('div');
            msgWrapper.className = 'd-flex mb-2';

            const msgContent = document.createElement('div');
            msgContent.className = 'p-2 rounded';
            msgContent.style.maxWidth = '80%';
            msgContent.style.wordWrap = 'break-word';
            msgContent.style.overflowWrap = 'break-word';

            // Mensaje con hora abajo
            const timeStr = new Date(m.createdAt ?? m.timestamp)
                .toLocaleTimeString('es-CL', { hour: '2-digit', minute: '2-digit' });
            msgContent.innerHTML = `${m.content}<br><small class="text-muted d-block text-end">${timeStr}</small>`;

            // 🔹 Decidir colores y orientación según roles
            if (m.role === currentRole) {
                // Mis mensajes → derecha en azul
                msgWrapper.classList.add('justify-content-end');
                msgContent.classList.add('bg-primary', 'text-white');
            } else {
                // Mensajes del otro → izquierda en gris
                msgWrapper.classList.add('justify-content-start');
                msgContent.classList.add('bg-light', 'text-dark');
            }

            msgWrapper.appendChild(msgContent);
            chatDiv.appendChild(msgWrapper);
        });

        // Scroll al final si estaba pegado abajo
        if (isAtBottom) {
            chatDiv.scrollTop = chatDiv.scrollHeight;
        }

    } catch (err) {
        console.error('Error cargando chat:', err);
    }
}

// Enviar mensaje
async function sendChatMessage() {
    const input = document.getElementById('chat-input');
    if (input.value.trim() === '') return;

    try {
        await fetch('/messages', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                senderId: localStorage.getItem("userId"),   // id real si lo guardas en login
                receiverId: 2,                              // id de la empresa o cliente
                conversationId: "conv1",
                role: localStorage.getItem("role"),         // ahora se guarda bien el rol
                content: input.value
            })
        });
        input.value = '';
        loadChat();
    } catch (err) {
        console.error('Error enviando mensaje:', err);
    }
}

// Verificar si el usuario está logueado
const token = localStorage.getItem("token");
const role  = localStorage.getItem("role");

if (!token || !role) {
    document.body.innerHTML = `
    <div class="container text-center my-5">
      <h2>Debes iniciar sesión primero</h2>
      <a href="login.html" class="btn btn-primary mt-3">Ir a Login</a>
    </div>
  `;
} else {
    console.log("Sesión iniciada como:", role);
    // Ajustar botones según el rol
    document.addEventListener("DOMContentLoaded", () => {
        if (role === "CLIENT") {
            document.querySelectorAll("#data-table button").forEach(btn => {
                btn.innerText = "Enviar Mensaje";
            });
        } else if (role === "COMPANY") {
            document.querySelectorAll("#data-table button").forEach(btn => {
                btn.innerText = "Responder Mensaje";
            });
        }
    });
}

// Botón volver = logout
document.getElementById("volver-btn")?.addEventListener("click", () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("email");
    window.location.href = "login.html";
});

// Cambiar texto del navbar según rol
const navbarRole = document.getElementById("navbar-role");
if (navbarRole) {
    if (role === "CLIENT") {
        navbarRole.textContent = "Cliente";
    } else if (role === "COMPANY") {
        navbarRole.textContent = "Empresa";
    }
}

// Recargar chat automáticamente cada 2 segundos
setInterval(loadChat, 2000);
