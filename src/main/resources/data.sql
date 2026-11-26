USE society_events;

INSERT INTO events
(title, description, start_time, end_time, location, capacity)
VALUES
    (
        'A Funeral for Your Bad Decisions',
        'Some things deserve a beautiful ending.',
        '2026-12-19 18:00:00',
        '2026-12-19 21:00:00',
        'The Rose Memorial Hall',
        50
    ),
    (
        'Funeral for Terrible Haircuts',
        'A solemn farewell to questionable hairstyles.',
        '2027-01-09 17:00:00',
        '2027-01-09 20:00:00',
        'The Velvet Lounge',
        30
    ),
    (
        'Burial of Questionable Romances',
        'An elegant evening dedicated to moving on.',
        '2027-02-14 18:00:00',
        '2027-02-14 22:00:00',
        'The Black Rose Ballroom',
        40
    ),
    (
        'Memorial for Impulse Purchases',
        'In loving memory of money unnecessarily spent.',
        '2027-02-27 16:00:00',
        '2027-02-27 19:00:00',
        'The Ivory Room',
        20
    ),
    (
        'Grand Burial of Common Sense',
        'A private ceremony for something long forgotten.',
        '2027-03-06 19:00:00',
        '2027-03-06 21:00:00',
        'The Secret Garden',
        2
    );
INSERT INTO participants
(name, email, participant_type, organisation_name)
VALUES
    ('Anna Rose', 'anna@example.com', 'INDIVIDUAL', NULL),
    ('James Black', 'james@example.com', 'INDIVIDUAL', NULL),
    ('Charlotte Bloom', 'charlotte@example.com', 'INDIVIDUAL', NULL),
    ('Oliver Graves', 'oliver@example.com', 'INDIVIDUAL', NULL),
    ('Victoria Thorn', 'victoria@example.com', 'ORGANISATION', 'Black Rose Events'),
    ('Henry Valentine', 'henry@example.com', 'ORGANISATION', 'The Broken Hearts Club');

INSERT INTO invitations
(participant_id, event_id, status)
VALUES
    (1, 1, 'ACCEPTED'),
    (2, 1, 'PENDING'),
    (3, 1, 'DECLINED'),
    (4, 2, 'ACCEPTED'),
    (5, 3, 'PENDING'),
    (6, 4, 'ACCEPTED'),
    (1, 5, 'ACCEPTED'),
    (2, 5, 'ACCEPTED'),
    (3, 5, 'PENDING');