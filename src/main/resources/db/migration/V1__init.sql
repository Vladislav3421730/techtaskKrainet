create table  users(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(255),
    name VARCHAR(50),
    surname VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE
);

create table user_role(
    user_id INTEGER,
    role VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table project(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description TEXT,
    created_at DATE DEFAULT CURRENT_DATE,
    cost decimal(10,2)
);


create table record(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    project_id INTEGER,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP +'1 day',
    FOREIGN KEY (user_id) REFERENCES users(id)  ON UPDATE CASCADE ON DELETE SET NULL ,
    FOREIGN KEY (project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE SET NULL
);

INSERT INTO users (username, password, name, surname) VALUES
            ('UserAndAdmin','$2a$10$Rjjt2alpVfFARVQ1kQKQkeAZfttiUkvwYns7GA71GnZNfAS3ofZF6','Владислав','Панасик'),
            ('Admin','$2a$10$Rjjt2alpVfFARVQ1kQKQkeAZfttiUkvwYns7GA71GnZNfAS3ofZF6','Елизавета','Полещук'),
            ('User','$2a$10$Rjjt2alpVfFARVQ1kQKQkeAZfttiUkvwYns7GA71GnZNfAS3ofZF6','Михаил','Курников');
INSERT INTO user_role VALUES
            (1,'ROLE_ADMIN'),
            (1,'ROLE_USER'),
            (2,'ROLE_ADMIN'),
            (3,'ROLE_USER');
INSERT INTO project (name, description, cost) VALUES
            ('project1','Description1',1500.34),
            ('project2','Description2',155.23),
            ('project3','Description3',1400.23);
INSERT INTO record (user_id, project_id) VALUES
            (1,1),
            (3,1),
            (1,2),
            (3,2),
            (3,3);
