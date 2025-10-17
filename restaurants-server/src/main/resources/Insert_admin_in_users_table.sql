INSERT INTO users (id, login, password, first_name, last_name, role, enabled)
VALUES (
           'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
           'admin',
           '$2a$10$YourEncodedPasswordHere',
           'Admin',
           'User',
           'ADMIN',
           true
       )
ON CONFLICT (login) DO NOTHING;