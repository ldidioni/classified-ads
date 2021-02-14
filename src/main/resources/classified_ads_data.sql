INSERT INTO users (id, username, password_hash, phone_number, email) VALUES
    (1, 'admin', '$2y$10$ylvbwPly0hb9E1DTENI6Qe3/NcTasAL66mG.KpfgbIfjrugC5dqiq', '+41 76 777 07 07', 'admin@example.com'),
    (2, 'boss', '$2y$10$D.IdbWY0LC6jiVpWL9d57uqT.AxAMx7mpybhm4xtdkYHf33X7Pbju', '+41 76 333 03 03', 'boss@example.com'),
    (3, 'greg', '$2y$10$coBmsD7zmWQxjI69q0QDeODXxUbkkCueYTvUEYKN5riJFgXTHh5zm', '+41 76 222 02 02', 'greg@example.com'),
    (4, 'mike', '$2y$10$nT7AKqZ1vW6aFUayVly6.esIY8wmzCPOi9nAufJs0aHid9sTRKGi2', '+41 76 111 01 01', 'mike@example.com'),
    (5, 'bob', '$2y$10$tPnH5J5LQ/94.sVtPUdDz.VCGFci5Q6F3RgSxo02e4/HmG7tGeHya', '+41 76 444 04 04', 'bob@example.com');

INSERT INTO roles (id, name) VALUES
     (1, 'ROLE_USER'),
     (2, 'ROLE_ADMIN');

INSERT INTO users_roles (id, user_id, role_id) VALUES
     (1, 1, 1),
     (2, 1, 2),
     (3, 2, 1),
     (4, 2, 2),
     (5, 3, 1),
     (6, 4, 1),
     (7, 5, 1);

INSERT INTO categories (id, name) VALUES
    (1, 'Automobile'),
    (2, 'Immobilier'),
    (3, 'Vêtements'),
    (4, 'Equipements sportifs');

INSERT INTO ads (id, title, description, asked_price, seller_id, category_id) VALUES
    (1, 'Vends BMW serie 3', 'Je vends ma voiture chérie de la marque à l\'hélice.', 32000, 1, 1),
    (2, 'Vends Alfa Giulia', 'Je vends mon pure sang italien aux 500 chevaux.', 58000, 4, 1),
    (3, 'Vends duplex vue lac Léman', 'Je vends mon appartement en duplex avec vue spectaculaire.', 1450000, 2, 2),
    (4, 'Vends maison de ville', 'Je vends ma maison située à deux pas de la gare de Neuchâtel.', 160000, 3, 2),
    (5, 'Vends chalet de montagne', 'Je vends mon chalet familial situé au pied des pistes.', 825000, 4, 2),
    (6, 'Vends slip de bain', 'Je vends un slip de bain en parfait état.', 400, 5, 3),
    (7, 'Vends vélo de course', 'Je vends mon vélo en carbone avec seulement 2000 km au compteur.', 750, 1, 4),
    (8, 'Vends banc de musculation', 'Je vends mon équipement de musculation, idéal pour rester en forme durant l\'hiver.', 200, 4, 4);

INSERT INTO tags (id, name) VALUES
    (1, 'Neuf'),
    (2, 'Occasion'),
    (3, 'Rare'),
    (4, 'Urgent');

INSERT INTO photos (id, ad_id, image_url) VALUES
    (1, 1, 'https://www.asphalte.ch/news/wp-content/webp-express/webp-images/doc-root/news/wp-content/uploads/2018/07/BMW-M5-200-100.jpg'),
    (2, 1, 'https://www.asphalte.ch/news/wp-content/webp-express/webp-images/doc-root/news/wp-content/uploads/2018/07/BMW-M5-F90-18-750x422.jpg'),
    (3, 1, 'https://www.asphalte.ch/news/wp-content/uploads/2018/07/BMW-M5-F90-01.jpg'),
    (4, 2, 'https://www.asphalte.ch/news/wp-content/webp-express/webp-images/doc-root/news/wp-content/uploads/2016/10/Alfa-Romeo-Giulia-Quadrifoglio-03-750.jpg'),
    (5, 2, 'https://www.asphalte.ch/news/wp-content/webp-express/webp-images/doc-root/news/wp-content/uploads/2016/10/Alfa-Romeo-Giulia-Quadrifoglio-24.jpg'),
    (6, 3, 'https://images.pexels.com/photos/1571460/pexels-photo-1571460.jpeg'),
    (7, 4, 'https://images.pexels.com/photos/186077/pexels-photo-186077.jpeg'),
    (8, 4, 'https://images.pexels.com/photos/106399/pexels-photo-106399.jpeg'),
    (9, 5, 'https://images.pexels.com/photos/774281/pexels-photo-774281.jpeg'),
    (10, 5, 'https://images.pexels.com/photos/3185484/pexels-photo-3185484.jpeg'),
    (11, 6, 'https://cdn.laredoute.com/products/680by680/0/6/2/062ce69d52bf23a862b6b7a041941f29.jpg'),
    (12, 7, 'https://www.datocms-assets.com/19016/1590662834-209.jpg'),
    (13, 7, 'https://www.datocms-assets.com/19016/1590662908-213.jpg'),
    (14, 7, 'https://www.datocms-assets.com/19016/1590669830-948.jpg'),
    (15, 8, 'https://static.digitecgalaxus.ch/Files/4/1/2/0/0/6/1/3/5200-hantelbank-hammer-force-2.0-geraet-01_2.jpg');

INSERT INTO ads_tags (id, ad_id, tag_id) VALUES
    (1, 1, 2),
    (2, 1, 4),
    (3, 2, 2),
    (4, 2, 3),
    (5, 3, 3),
    (6, 5, 4),
    (7, 6, 1),
    (8, 7, 2),
    (9, 7, 3);