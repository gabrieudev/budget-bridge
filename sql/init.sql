CREATE DATABASE IF NOT EXISTS BudgetBridge;

USE BudgetBridge;

CREATE TABLE IF NOT EXISTS category(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO category (name) VALUES
('design'),
('infrastructure'),
('development'),
('planning');

CREATE TABLE IF NOT EXISTS project(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    categoryId INT NOT NULL,
    name       VARCHAR(100) NOT NULL,
    budget     DECIMAL(10,2) NOT NULL,
    cost       DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_Project_categoryId
        FOREIGN KEY (categoryId) REFERENCES Category(id)
);

CREATE INDEX ix_Project_categoryId
ON Project(categoryId);

CREATE TABLE IF NOT EXISTS service(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    projectId   INT NOT NULL,
    name        VARCHAR(100) NOT NULL,
    cost        DECIMAL(10,2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    CONSTRAINT  fk_Service_projectId
        FOREIGN KEY (projectId) REFERENCES Project(id)
);

CREATE INDEX ix_Service_projectId
ON Service(projectId);
