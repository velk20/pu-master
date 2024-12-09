INSERT INTO user_roles(id, user_role, description)
values ('e58ed763-928c-4155-bee9-fdbaaadc','ADMIN', 'Admin Permissions'),

       ('e58ed763-928c-4155-bee9-fdbaaad','USER', 'Simple User Permissions');


INSERT into users (id, email, first_name, is_active, last_name, password, username,phone,age, created_at, last_modified, user_role_id)
VALUES ('128ed763-928c-4155-bee9-fdbaaadc','admin@admin.com','Admin',1,'Adminov',
        '$2a$10$uLYimymH.0qx1cFGMLdqau8FGkAv3zxiRYGp/skhrqHLCPhdgN37G'
           ,'admin','0888288320',18,'2023-08-25T15:14:40.733400800','2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),

       ('348ed763-928c-4155-bee9-fdbaaadc','user@user.com','User',1,'Userov',
        '$2a$10$8qDc5UdoxnKw90aniMpoqOHzMZ5aep.vvPdGMLCMG6VRxNjb.p7rS'
           ,'user','0888288321',20,'2023-08-25T15:14:40.733400800','2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaad');


INSERT INTO channels (id, name, owner_id, created_at, deleted)
VALUES
    ('c58ed763-928c-4155-bee9-fdbaaadc', 'General', '128ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('d68ed763-928c-4155-bee9-fdbaaadc', 'Random', '348ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false);

INSERT INTO channel_memberships (id, channel_id, user_id, role)
VALUES
    ('m58ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '128ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('n68ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '348ed763-928c-4155-bee9-fdbaaadc', 'GUEST'),
    ('o78ed763-928c-4155-bee9-fdbaaadc', 'd68ed763-928c-4155-bee9-fdbaaadc', '348ed763-928c-4155-bee9-fdbaaadc', 'OWNER');

INSERT INTO messages (id, content, author_id, channel_id, timestamp, deleted)
VALUES
    ('e58ed763-928c-4155-bee9-fdbaaadc', 'Welcome to the General channel!', '128ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('f68ed763-928c-4155-bee9-fdbaaadc', 'Hello everyone!', '348ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:15:40.733400800', false),
    ('g78ed763-928c-4155-bee9-fdbaaadc', 'This is a Random channel message!', '348ed763-928c-4155-bee9-fdbaaadc', 'd68ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:16:40.733400800', false);

INSERT INTO friends (user_id, friend_id)
VALUES
    ('128ed763-928c-4155-bee9-fdbaaadc', '348ed763-928c-4155-bee9-fdbaaadc'), -- Admin is friends with User
    ('348ed763-928c-4155-bee9-fdbaaadc', '128ed763-928c-4155-bee9-fdbaaadc'); -- User is friends with Admin
