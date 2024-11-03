-- Insert example data for clients
INSERT INTO customers (id, name, number_of_projects, is_active)
VALUES (1, 'Client A', 1, 1),
       (2, 'Client B', 1, 1),
       (3, 'Client C', 1, 1),
       (4, 'Client D', 0, 1);

-- Insert example data for projects
INSERT INTO projects (id, name, cost, customer_id, is_active)
VALUES (1, 'Project Alpha', 15000.00, 1, 1),
       (2, 'Project Beta', 20000.00, 2, 1),
       (3, 'Project Gamma', 30000.00, 3, 1);

-- Insert example data for contacts
INSERT INTO contacts (id, first_name, last_name, phone, email, is_active)
VALUES (1, 'John', 'Doe', '123-456-7890', 'john.doe@example.com', 1),
       (2, 'Jane', 'Smith', '098-765-4321', 'jane.smith@example.com', 1),
       (3, 'Emily', 'Johnson', '555-555-5555', 'emily.johnson@example.com', 1);

-- Insert example data for evaluations (sites)
INSERT INTO sites (id, name, address, config_cost, other_cost, project_id, is_active)
VALUES (1, 'Evaluation Area 1', '123 Main St, City A', 12000.00, 3000.00, 1, 1),
       (2, 'Evaluation Area 2', '456 Oak St, City B', 18000.00, 5000.00, 2, 1),
       (3, 'Evaluation Area 3', '789 Pine St, City C', 25000.00, 7000.00, 3, 1);

-- Assuming contacts_projects is a join table between contacts and projects
INSERT INTO contacts_projects (contacts_id, projects_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);
