# Project Description

## Educational Management System

The goal of this project is to develop a system for managing the educational journey of students. The system is designed to handle departments, programs, teachers, modules, students, and grades while adhering to the following specifications:

1. A department can encompass one or more programs and must be overseen by a teacher with a teaching profile.
2. A program must include at least 12 modules and is managed by a teacher with a teaching profile.
3. A teacher can instruct one or more modules and must be associated with only one department.
4. A student must be enrolled in a single program and must receive a final grade for each module.

The implementation of this project is divided into 4 phases:

1. **Console Program Development**: Creation of a console-based program to fulfill specifications, utilizing collections for data storage.
2. **Database Integration**: Evolution from data storage in collections to an SQL database.

## Overall Objectives

1. Development of a program that is extensible, flexible, and scalable.
2. Proper code structuring.
3. Learning the process of development, testing, and deployment.
4. Understanding different types of relationships between classes (One To One / One To Many / Many To One / Many To Many).

## Class Descriptions

- **Department** (`Title`: text, `Manager`: Teacher)
- **Teacher** (`Last Name`: text, `First Name`: text, `Email`: text, `Rank`: text, `Department`: Department)
- **Program** (`Title`: text, `Manager`: Teacher, `Department`: Department)
- **Module** (`Title`: text, `Program`: Program, `Instructor`: Teacher)
- **Student** (`Last Name`: text, `First Name`: text, `Email`: text, `ID`: int, `Program`: Program)

## Project Phases

1. **Console Program Development**: Creation of a console-based program to fulfill specifications, utilizing collections for data storage.
2. **Database Integration**: Evolution from data storage in collections to an SQL database.

## Execution

Below are some screenshots of the executable code:

### Departement Controller:

![WhatsApp Image 2024-01-09 at 01 26 47](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/7fa6e442-9ef8-4d4a-bcf1-4e9666dc0c2c)

![WhatsApp Image 2024-01-09 at 01 26 48](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/127f3e7f-6dd6-4b16-9ccf-5154d2e46750)

![WhatsApp Image 2024-01-09 at 01 26 49](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/2aff3dbf-2495-425e-be42-5331a96f36e1)

### Enseignant Controller:

![WhatsApp Image 2024-01-09 at 01 26 56](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/62b0ed45-c114-4eb1-b259-f852b90d2edf)

![WhatsApp Image 2024-01-09 at 01 26 54](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/66d78221-7b40-49dd-aab4-c84f27edea9f)

![WhatsApp Image 2024-01-09 at 01 27 01](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/82dd6d12-fc3b-41eb-abe8-56c5fa362411)

![WhatsApp Image 2024-01-09 at 01 26 50](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/64637e35-5da0-4299-a6f3-eab5ba804b2d)

![WhatsApp Image 2024-01-09 at 01 26 51](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/2827fefe-bcc1-4b6a-abb3-4369f23c84d2)

### Etudiant Controller:

![WhatsApp Image 2024-01-09 at 01 27 03](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/dd70c5d6-61e5-43d0-b3f9-ecf544c30457)

![WhatsApp Image 2024-01-09 at 01 27 05](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/88c63558-bf5d-4e9f-a698-8659c91d5b4e)

![WhatsApp Image 2024-01-09 at 01 27 05 (1)](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/652d0562-fb5d-474d-8110-d47bfea8e465)

![WhatsApp Image 2024-01-09 at 01 27 05 (2)](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/d4240b19-1886-4166-85b7-206998cf6876)

![WhatsApp Image 2024-01-09 at 01 27 05 (3)](https://github.com/Youssef-Mofadal/MyJavaproject/assets/153006674/5cb9b7a3-ce7e-4f37-9635-b942f0bafe17)



