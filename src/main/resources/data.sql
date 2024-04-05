-- Insert Users
INSERT INTO users(first_name, last_name, email) VALUES
('Test1', 'User', 'test1@gmail.com'),
('Test2', 'User', 'test2@gmail.com'),
('Test3', 'User', 'test3@gmail.com'),
('Valar', 'User', 'valar@gmail.com');

-- Insert Trains
INSERT INTO trains(name, max_seat_per_section) VALUES
('Eurostar #1', 10),
('Eurostar #2', 10),
('Eurostar #3', 10),
('Eurail #1', 20),
('Eurail #2', 20),
('Eurail #3', 20),
('Eurostar #4', 10),
('Eurostar #5', 10),
('Eurostar #6', 10),
('Eurail #4', 20),
('Eurail #5', 20),
('Eurail #6', 20);

-- Insert Schedules
INSERT INTO schedules(from_station, to_station, departure_time, arrival_time, train_id) VALUES
('London', 'France', '10:00:00', '12:40:00', 1),
('London', 'Belgium', '10:00:00', '14:59:00', 2),
('London', 'Germany', '09:00:00', '15:30:00', 3),
('London', 'France', '11:00:00', '13:40:00', 4),
('London', 'Belgium', '11:00:00', '15:59:00', 5),
('London', 'Germany', '10:00:00', '16:30:00', 6),
('France', 'London', '10:00:00', '12:40:00', 7),
('Belgium', 'London', '10:00:00', '14:59:00', 8),
('Germany', 'London', '09:00:00', '15:30:00', 9),
('France', 'London', '11:00:00', '13:40:00', 10),
('Belgium', 'London', '11:00:00', '15:59:00', 11),
('Germany', 'London', '10:00:00', '16:30:00', 12);

-- Insert Reservations