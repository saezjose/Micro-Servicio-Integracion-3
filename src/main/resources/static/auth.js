// --- Manejo de autenticación y roles ---

// Función login → llamada desde login.html
async function login(event) {
  event.preventDefault(); // evita recargar la página

  // 1. Obtener email y contraseña desde el formulario
  const email = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  try {
    // 2. Hacer petición al backend /auth/login
    const response = await fetch("/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
      document.getElementById("error").innerText = "Credenciales inválidas";
      return;
    }

    // 3. Guardar token y rol en localStorage
    const data = await response.json();
    localStorage.setItem("token", data.token);
    localStorage.setItem("role", data.role);
    localStorage.setItem("email", data.email);

    window.location.href = "index.html";
  } catch (err) {
    document.getElementById("error").innerText = "Error de conexión con el servidor";
  }
}

// Verificar acceso a páginas restringidas
function checkAccess(requiredRole) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if (!token || role !== requiredRole) {
    window.location.href = "login.html";
  }
}

// Función logout
function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  localStorage.removeItem("email");
  window.location.href = "login.html";
}
