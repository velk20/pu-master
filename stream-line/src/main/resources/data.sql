INSERT INTO user_roles(id, user_role, description)
values ('e58ed763-928c-4155-bee9-fdbaaadc','ADMIN', 'Admin Permissions'),
       ('e58ed763-928c-4155-bee9-fdbaaad','USER', 'Simple User Permissions');


INSERT INTO users (id, email, first_name, is_active, last_name, password, username, phone, age, created_at, last_modified, user_role_id)
VALUES
    ('128ed763-928c-4155-bee9-fdbaaadc', 'admin@admin.com', 'Admin', 1, 'Adminov', '$2a$10$uLYimymH.0qx1cFGMLdqau8FGkAv3zxiRYGp/skhrqHLCPhdgN37G', 'admin', '0888288320', 18, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('348ed763-928c-4155-bee9-fdbaaadc', 'user@user.com', 'User', 1, 'Userov', '$2a$10$uLYimymH.0qx1cFGMLdqau8FGkAv3zxiRYGp/skhrqHLCPhdgN37G', 'user', '0888288321', 20, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('528ed763-928c-4155-bee9-fdbaaadc', 'johndoe@example.com', 'John', 1, 'Doe', '$2a$10$gHJXZC1QFeNFSN2GHO0x1O4OiFGjMdKNlB8kxV2lH7VWwqqg0i.wW', 'johndoe', '0888288322', 25, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('628ed763-928c-4155-bee9-fdbaaadc', 'janesmith@example.com', 'Jane', 1, 'Smith', '$2a$10$J0ABCsldTKhftw77Ol5aXuqRVmLIXAxKvABhGzphIILnGs.v8OS2W', 'janesmith', '0888288323', 30, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('728ed763-928c-4155-bee9-fdbaaadc', 'emilybrown@example.com', 'Emily', 1, 'Brown', '$2a$10$Rjd.X6SzYkkXB/vJvCLw6.f1c2Lf6j7DkKkFXYHtEjkhXbx1A5/FK', 'emilyb', '0888288324', 28, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('828ed763-928c-4155-bee9-fdbaaadc', 'davidwilson@example.com', 'David', 1, 'Wilson', '$2a$10$Uuq/XVPu78P7ozx1s4BMeOLYhrsdMgBxnXvc/Tj0YCVTxTOWCxDWu', 'davidw', '0888288325', 35, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('928ed763-928c-4155-bee9-fdbaaadc', 'sarahjones@example.com', 'Sarah', 1, 'Jones', '$2a$10$XH7Bb/SXPO5OlfQfZMb/9uiAezs2GHKmEjHG.Y1pmfTwPP2kO.lTu', 'sarahj', '0888288326', 22, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('a28ed763-928c-4155-bee9-fdbaaadc', 'michaelclark@example.com', 'Michael', 1, 'Clark', '$2a$10$YUZF1hRIcn4ll6m1ZtPON.Dh6RLoNGnW6p6/o6U26P2sA6hQCfF6e', 'michaelc', '0888288327', 33, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('b28ed763-928c-4155-bee9-fdbaaadc', 'olivialee@example.com', 'Olivia', 1, 'Lee', '$2a$10$LaPkkAZ.8I9PjZwQsBZ.mjFluvShYZkJwU7PJlQm1C8R71XsHTcDy', 'olivial', '0888288328', 27, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc'),
    ('c28ed763-928c-4155-bee9-fdbaaadc', 'williamking@example.com', 'William', 1, 'King', '$2a$10$BdMtVs23kIRWQyBkL3p/lyCxSfYjmjNZ/XjUxzFfSoxvhOfVHRdC.', 'williamk', '0888288329', 29, '2023-08-25T15:14:40.733400800', '2023-08-25T15:14:40.733400800', 'e58ed763-928c-4155-bee9-fdbaaadc');

INSERT INTO channels (id, name, owner_id, created_at, deleted)
VALUES
    ('c58ed763-928c-4155-bee9-fdbaaadc', 'General', '128ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('d68ed763-928c-4155-bee9-fdbaaadc', 'Random', '348ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('e78ed763-928c-4155-bee9-fdbaaadc', 'Tech Talk', '528ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('f88ed763-928c-4155-bee9-fdbaaadc', 'Music Lovers', '628ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('g98ed763-928c-4155-bee9-fdbaaadc', 'Fitness and Health', '728ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('h09ed763-928c-4155-bee9-fdbaaadc', 'Book Club', '828ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('i19ed763-928c-4155-bee9-fdbaaadc', 'Travel Enthusiasts', '928ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('j29ed763-928c-4155-bee9-fdbaaadc', 'Cooking Corner', 'a28ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('k39ed763-928c-4155-bee9-fdbaaadc', 'Gaming Zone', 'b28ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('l49ed763-928c-4155-bee9-fdbaaadc', 'Photography Club', 'c28ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false);

INSERT INTO channel_memberships (id, channel_id, user_id, role)
VALUES
    ('m58ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '128ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('n68ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '348ed763-928c-4155-bee9-fdbaaadc', 'GUEST'),
    ('o78ed763-928c-4155-bee9-fdbaaadc', 'd68ed763-928c-4155-bee9-fdbaaadc', '348ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('p88ed763-928c-4155-bee9-fdbaaadc', 'e78ed763-928c-4155-bee9-fdbaaadc', '528ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('q98ed763-928c-4155-bee9-fdbaaadc', 'f88ed763-928c-4155-bee9-fdbaaadc', '628ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('r09ed763-928c-4155-bee9-fdbaaadc', 'g98ed763-928c-4155-bee9-fdbaaadc', '728ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('s19ed763-928c-4155-bee9-fdbaaadc', 'h09ed763-928c-4155-bee9-fdbaaadc', '828ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('t29ed763-928c-4155-bee9-fdbaaadc', 'i19ed763-928c-4155-bee9-fdbaaadc', '928ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('u39ed763-928c-4155-bee9-fdbaaadc', 'j29ed763-928c-4155-bee9-fdbaaadc', 'a28ed763-928c-4155-bee9-fdbaaadc', 'OWNER'),
    ('v49ed763-928c-4155-bee9-fdbaaadc', 'k39ed763-928c-4155-bee9-fdbaaadc', 'b28ed763-928c-4155-bee9-fdbaaadc', 'OWNER');

INSERT INTO messages (id, content, author_id, channel_id, timestamp, deleted)
VALUES
    ('e58ed763-928c-4155-bee9-fdbaaadc', 'Welcome to the General channel!', '128ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:14:40.733400800', false),
    ('f68ed763-928c-4155-bee9-fdbaaadc', 'Hello everyone!', '348ed763-928c-4155-bee9-fdbaaadc', 'c58ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:15:40.733400800', false),
    ('g78ed763-928c-4155-bee9-fdbaaadc', 'This is a Random channel message!', '348ed763-928c-4155-bee9-fdbaaadc', 'd68ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:16:40.733400800', false),
    ('h88ed763-928c-4155-bee9-fdbaaadc', 'Discussing the latest tech trends.', '528ed763-928c-4155-bee9-fdbaaadc', 'e78ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:17:40.733400800', false),
    ('i98ed763-928c-4155-bee9-fdbaaadc', 'Sharing my favorite song!', '628ed763-928c-4155-bee9-fdbaaadc', 'f88ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:18:40.733400800', false),
    ('j09ed763-928c-4155-bee9-fdbaaadc', 'Let’s talk about our fitness goals.', '728ed763-928c-4155-bee9-fdbaaadc', 'g98ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:19:40.733400800', false),
    ('k19ed763-928c-4155-bee9-fdbaaadc', 'Reviewing our book of the month.', '828ed763-928c-4155-bee9-fdbaaadc', 'h09ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:20:40.733400800', false),
    ('l29ed763-928c-4155-bee9-fdbaaadc', 'Dream destinations and travel tips!', '928ed763-928c-4155-bee9-fdbaaadc', 'i19ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:21:40.733400800', false),
    ('m39ed763-928c-4155-bee9-fdbaaadc', 'Cooking hacks for busy people.', 'a28ed763-928c-4155-bee9-fdbaaadc', 'j29ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:22:40.733400800', false),
    ('n49ed763-928c-4155-bee9-fdbaaadc', 'Captured an amazing sunset shot!', 'c28ed763-928c-4155-bee9-fdbaaadc', 'k39ed763-928c-4155-bee9-fdbaaadc', '2023-08-25T15:23:40.733400800', false);

INSERT INTO friends (user_id, friend_id)
VALUES
    ('128ed763-928c-4155-bee9-fdbaaadc', '348ed763-928c-4155-bee9-fdbaaadc'),
    ('348ed763-928c-4155-bee9-fdbaaadc', '128ed763-928c-4155-bee9-fdbaaadc'),
    ('528ed763-928c-4155-bee9-fdbaaadc', '628ed763-928c-4155-bee9-fdbaaadc'),
    ('628ed763-928c-4155-bee9-fdbaaadc', '528ed763-928c-4155-bee9-fdbaaadc'),
    ('728ed763-928c-4155-bee9-fdbaaadc', '828ed763-928c-4155-bee9-fdbaaadc'),
    ('828ed763-928c-4155-bee9-fdbaaadc', '728ed763-928c-4155-bee9-fdbaaadc'),
    ('928ed763-928c-4155-bee9-fdbaaadc', 'a28ed763-928c-4155-bee9-fdbaaadc'),
    ('a28ed763-928c-4155-bee9-fdbaaadc', '928ed763-928c-4155-bee9-fdbaaadc'),
    ('b28ed763-928c-4155-bee9-fdbaaadc', 'c28ed763-928c-4155-bee9-fdbaaadc');

