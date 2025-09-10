// --- Manejo de autenticación y roles ---

// Función login → llamada desde login.html
async function login(event) {
  event.preventDefault(); // evita recargar la página

  // 1. Obtener usuario y contraseña desde el formulario
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  try {
    // 2. Hacer petición al backend /login
    const response = await fetch("/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    if (!response.ok) {
      // si credenciales son inválidas
      document.getElementById("error").innerText = "Credenciales inválidas";
      return;
    }

    // 3. Guardar token y rol en localStorage
    const data = await response.json();
    localStorage.setItem("token", data.token); // 🔑 token
    localStorage.setItem("role", data.role);   // 🔑 rol

    // 4. Redirigir según rol
    if (data.role === "CLIENTE") {
      window.location.href = "cliente.html";
    } else if (data.role === "EMPRESA") {
      window.location.href = "empresa.html";
    } else {
      document.getElementById("error").innerText = "Rol desconocido";
    }
  } catch (err) {
    document.getElementById("error").innerText = "Error de conexión con el servidor";
  }
}

// Verificar acceso a páginas restringidas
function checkAccess(requiredRole) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  // Si no hay token o el rol no coincide → vuelve a login
  if (!token || role !== requiredRole) {
    window.location.href = "login.html";
  }
}

// Función logout → limpia sesión y vuelve a login
function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  window.location.href = "login.html";
}
