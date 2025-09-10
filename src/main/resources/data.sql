-- Insertar un cliente
INSERT INTO users (email, password_hash, role, created_at)
VALUES ('cliente@test.com', 'hashCliente123', 'CLIENT', CURRENT_TIMESTAMP);

-- Insertar una empresa
INSERT INTO users (email, password_hash, role, created_at)
VALUES ('empresa@test.com', 'hashEmpresa123', 'COMPANY', CURRENT_TIMESTAMP);
