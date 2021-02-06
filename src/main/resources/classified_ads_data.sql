INSERT INTO users (username, password_hash, phone_number, email_address) VALUES
    ('admin', '$2y$10$ylvbwPly0hb9E1DTENI6Qe3/NcTasAL66mG.KpfgbIfjrugC5dqiq', '+41 76 777 07 07', 'admin@example.com'),
    ('boss', '$2y$10$D.IdbWY0LC6jiVpWL9d57uqT.AxAMx7mpybhm4xtdkYHf33X7Pbju', '+41 76 333 03 03', 'boss@example.com'),
    ('greg', '$2y$10$coBmsD7zmWQxjI69q0QDeODXxUbkkCueYTvUEYKN5riJFgXTHh5zm', '+41 76 222 02 02', 'greg@example.com'),
    ('mike', '$2y$10$nT7AKqZ1vW6aFUayVly6.esIY8wmzCPOi9nAufJs0aHid9sTRKGi2', '+41 76 111 01 01', 'mike@example.com'),
    ('bob', '$2y$10$tPnH5J5LQ/94.sVtPUdDz.VCGFci5Q6F3RgSxo02e4/HmG7tGeHya', '+41 76 444 04 04', 'bob@example.com');

INSERT INTO roles (role) VALUES
     ('USER'),
     ('ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES
     (1, 1),
     (1, 2),
     (2, 1),
     (2, 2),
     (3, 1),
     (4, 1),
     (5, 1);

INSERT INTO categories (name) VALUES
    ('Véhicules'),
    ('Biens immobiliers'),
    ('Jeux pour enfants');

INSERT INTO ads (title, description, asked_price, seller_id, category_id) VALUES
    ('Vends BMW serie 3', 'Je vends ma voiture chérie de la marque à l\'hélice.', 32000, 3, 1),
    ('Vends Alfa Giulia', 'Je vends mon pure sang italien aux 500 chevaux.', 58000, 4, 1),
    ('Vends duplex vue lac Léman', 'Je vends mon appartement en duplex avec vue spectaculaire.', 1450000, 4, 2),
    ('Vends chalet de montagne', 'Je vends mon chalet familial situé au pied des pistes.', 825000, 5, 2);
