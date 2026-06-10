GHADS – Gaza Humanitarian Aid Distribution System
-- System Name and Purpose
GHADS is a system for managing humanitarian aid distribution.
Its purpose is to organize the process of searching for vulnerable families, recording aid distributions, and preventing duplication within a specific time period.

 --Problem it Solves
In many cases, aid distributions are repeated for the same family within a short time,
which creates unfairness. The system solves this problem by checking the last distribution before allowing a new one,
while also providing a simple interface for searching and managing data.

 --Technologies Used
JavaFX for the user interface

JPA (EclipseLink) for database handling

NetBeans IDE for project development

--Architecture Pattern Followed
The system is built using the MVC (Model – View – Controller) pattern:

Models: Entities such as Family, Organization, AidDistribution

DAO Layer: Data access and queries

Controllers: Logic for validation and interaction with views

View (FXML): User interfaces 
<img width="429" height="334" alt="image" src="https://github.com/user-attachments/assets/78dda478-99c9-459e-a160-5cfe85f91fba" />
<img width="585" height="434" alt="image" src="https://github.com/user-attachments/assets/94a73139-bd4c-420a-85b2-2dd315a3ac85" />
<img width="831" height="544" alt="image" src="https://github.com/user-attachments/assets/925f5249-0de3-4ebb-ba65-fa63b6612b27" />



