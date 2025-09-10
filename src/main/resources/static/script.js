// script.js

const sender = "Postulante";
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

        messages.forEach(m => {
            const messageDate = new Date(m.timestamp).toLocaleDateString('es-CL', {day:'numeric', month:'long', year:'numeric'});

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
            msgContent.style.overflowWrap = 'break-word'; // Garantiza que los textos largos bajen de línea

            // Mensaje con hora abajo
            const timeStr = new Date(m.timestamp).toLocaleTimeString('es-CL', {hour:'2-digit', minute:'2-digit'});
            msgContent.innerHTML = `${m.content}<br><small class="text-muted d-block text-end">${timeStr}</small>`;

            if (m.sender === sender) {
                msgWrapper.classList.add('justify-content-end');
                msgContent.classList.add('bg-primary', 'text-white');
            } else {
                msgWrapper.classList.add('justify-content-start');
                msgContent.classList.add('bg-light', 'text-dark');
            }

            msgWrapper.appendChild(msgContent);
            chatDiv.appendChild(msgWrapper);
        });

        // Solo hace scroll al final si el usuario estaba pegado abajo
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
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                senderId: 1,          // Id del postulante (ejemplo)
                receiverId: 2,        // Id de la empresa
                conversationId: "conv1",
                senderType: "CLIENTE",
                content: input.value
            })
        });
        input.value = '';
        loadChat();
    } catch (err) {
        console.error('Error enviando mensaje:', err);
    }
}

// Recargar chat automáticamente cada 2 segundos
setInterval(loadChat, 2000);