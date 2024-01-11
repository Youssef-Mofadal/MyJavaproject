CREATE DATABASE IS NOT EXSIST tpEducation;
use tpEducation

CREATE TABLE Module (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    intitule VARCHAR(255),
    filiere_id BIGINT,
    professeur_id BIGINT
);

CREATE TABLE Departement (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    intitule VARCHAR(255),
    responsable_id BIGINT
);

CREATE TABLE Enseignant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(255),
    grade VARCHAR(50),
    departement_id BIGINT
);

CREATE TABLE Etudiant (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(255),
    apogee INT,
    filiere_id BIGINT
);


CREATE TABLE Filiere (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    intitule VARCHAR(255),
    responsable_id BIGINT,
    departement_id BIGINT
);


CREATE TABLE Note (
    note FLOAT,
    etudiant_id BIGINT,
    filiere_id BIGINT
);