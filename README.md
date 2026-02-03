# Project README: University Domain Modeling with JPA

This README provides an overview of the technical work and documentation compiled for **Anastasiia Kravchenko**, specifically regarding the University Domain Modeling project using Java Persistence API (JPA) and the comprehensive management of a professional cybersecurity profile.

---

## 🛠 Project Overview

The primary technical component involves modeling a university domain using **JPA Relationships**. The project focuses on creating a robust backend structure to manage student data, course enrollments, and academic records within a relational database.

### Core Technical Work:

* **Entity Modeling**: Defined JPA entities such as `Address`, `Student`, and `Course` with appropriate primary keys and generated values.
* **Relationship Mapping**: Implemented complex database relationships including enums for `EnrollmentStatus` and logic for student-course associations.
* **Infrastructure**: Configured the project using **Gradle (Kotlin DSL)** and `persistence.xml` for database connectivity and ORM management.
* **Testing**: Developed a `Test` class to verify table creation and object mapping within the `pjUniTest` factory.

---

## 📂 Repository Contents

### 1. Technical Task Documentation

* `2526W_wis_UTP_task13.pdf`: Detailed requirements for modeling the University Domain with JPA.

---

## 🚀 How to Use

### For the JPA Project:

1. **Preparation**: Ensure you have **Gradle** installed. Create a new project and use the `build.gradle.kts` configuration provided in the assignment.
2. **Domain Logic**: Place JPA entity classes in the `src/main/java/logic` package.
3. **Database Config**: Update `src/main/resources/META-INF/persistence.xml` with your specific database credentials and entity class mappings.
4. **Verification**: Run the `Test.main()` method to trigger the `EntityManagerFactory`. Check your database console to verify that tables like `addresses` and `enrollments` were created successfully.
