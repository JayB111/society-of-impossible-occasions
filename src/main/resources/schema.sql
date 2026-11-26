CREATE DATABASE IF NOT EXISTS society_events;

USE society_events;
CREATE TABLE IF NOT EXISTS participants (
                                            id INT PRIMARY KEY AUTO_INCREMENT,
                                            name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    participant_type VARCHAR(20) NOT NULL,
    organisation_name VARCHAR(150),

    CHECK (participant_type IN ('INDIVIDUAL', 'ORGANISATION'))
    );
CREATE TABLE IF NOT EXISTS events (
                                      id INT PRIMARY KEY AUTO_INCREMENT,
                                      title VARCHAR(150) NOT NULL,
    description TEXT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    location VARCHAR(200) NOT NULL,
    capacity INT NOT NULL,

    CHECK (capacity > 0),
    CHECK (end_time > start_time)
    );
CREATE TABLE IF NOT EXISTS invitations (
                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                           participant_id INT NOT NULL,
                                           event_id INT NOT NULL,
                                           status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

    FOREIGN KEY (participant_id) REFERENCES participants(id),
    FOREIGN KEY (event_id) REFERENCES events(id),

    UNIQUE (participant_id, event_id),

    CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED'))
    );