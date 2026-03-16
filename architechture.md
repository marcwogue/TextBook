1- bad
2- 


# 1. Vue globale de l’architecture

L’application est une **application Java Desktop** reposant sur :

* interface graphique locale
* architecture **MVC**
* base de données **PostgreSQL**
* accès BD avec **JDBC**
* distribution sous forme **JAR exécutable**

Architecture générale :

```
+---------------------------+
|      Interface GUI        |
|        (Swing)            |
+------------+--------------+
             |
             v
+---------------------------+
|        Controllers        |
| gestion des interactions  |
+------------+--------------+
             |
             v
+---------------------------+
|        Services           |
| logique métier            |
+------------+--------------+
             |
             v
+---------------------------+
|          DAO              |
| accès base via JDBC       |
+------------+--------------+
             |
             v
+---------------------------+
|       PostgreSQL          |
|      Base de données      |
+---------------------------+
```

---

# 2. Outils technologiques

| Composant           | Technologie     |
| ------------------- | --------------- |
| langage             | Java            |
| interface graphique | Swing           |
| architecture        | MVC             |
| accès base          | JDBC            |
| base de données     | PostgreSQL      |
| build               | Maven ou Gradle |
| packaging           | JAR exécutable  |

Driver utilisé :

```
PostgreSQL JDBC Driver
```

---

# 3. Architecture MVC

Le modèle MVC permet de **séparer les responsabilités**.

## Schéma MVC

```
+-----------+        +--------------+        +-----------+
|   VIEW    | -----> | CONTROLLER   | -----> |  MODEL    |
| interface |        | gestion flux |        | données   |
+-----------+        +--------------+        +-----------+
        ^                                            |
        |____________________________________________|
                     données affichées
```

---

## 3.1 Vue (View)

Responsable de :

* afficher les interfaces
* capturer les actions utilisateur

Exemples d’écrans :

```
MainWindow
UE Management
Teacher Management
Session Report
Session History
```

La vue **ne contient aucune logique métier**.

---

## 3.2 Contrôleur (Controller)

Rôle :

* recevoir les actions de la vue
* appeler les services
* renvoyer les résultats à l’interface

Exemples :

```
UEController
TeacherController
RepportController
```

---

## 3.3 Modèle (Model)

Le modèle représente les **objets métier**.

Exemples :

```
UE
Enseignant
Seance
```

Chaque objet correspond à une **entité de la base de données**.

---

# 4. Architecture interne détaillée

Architecture en couches :

```
Presentation Layer
        │
        ▼
Controller Layer
        │
        ▼
Service Layer
        │
        ▼
Persistence Layer (DAO)
        │
        ▼
Database
```

---

# 5. Organisation logique des modules

```
Application
│
├── Interface Graphique
│
├── Gestion des UE
│
├── Gestion des Enseignants
│
├── Gestion des rapports
│
└── Consultation Historique
```

Chaque module suit **le même schéma MVC**.

---

# 6. Schéma du flux de traitement

Exemple : ajout d’une séance

```
Utilisateur
     │
     ▼
Interface graphique
     │
     ▼
Session Controller
     │
     ▼
Session Service
     │
     ▼
Session DAO
     │
     ▼
PostgreSQL
```

Puis retour des données :

```
PostgreSQL
     │
     ▼
DAO
     │
     ▼
Service
     │
     ▼
Controller
     │
     ▼
Interface graphique
```

---

# 7. Architecture de la base de données

Schéma relationnel :

```
UE
-----------------
code_ue (PK)
intitule

ENSEIGNANT
-----------------
matricule (PK)
nom
prenom

rapports
-----------------
id_seance (PK)
code_ue (FK)
matricule (FK)
date_seance
heure_debut
heure_fin
resume
```

Relations :

```
UE 1 --------- n SEANCE
ENSEIGNANT 1 -- n SEANCE
```

Une séance :

* appartient à une UE
* est dispensée par un enseignant

---

# 8. Initialisation automatique de la base

Au démarrage de l'application :

```
Application Start
       │
       ▼
Database Initializer
       │
       ▼
Check PostgreSQL connection
       │
       ▼
Check if database exists
       │
 ┌─────┴─────┐
 │           │
No           Yes
 │           │
 ▼           ▼
Create DB   Continue
 │
 ▼
Create Tables
```

Cette étape garantit :

* installation simple
* déploiement rapide
* aucune configuration complexe.

---

# 9. Structure logique du projet

Organisation recommandée :

```
application
│
├── main
│
├── model
│
├── view
│
├── controller
│
├── service
│
├── dao
│
└── util
```

Chaque couche possède **une responsabilité unique**.

---

# 10. Cycle de vie de l’application

```
Start Application
      │
      ▼
Initialize Database
      │
      ▼
Load Main Window
      │
      ▼
User Interaction
      │
      ▼
Controller Processing
      │
      ▼
Data Access (DAO)
      │
      ▼
Database
```

---

# 11. Packaging et déploiement

Le projet sera distribué sous forme :

```
textBook_manager.jar
```

Lancement :

```
java -jar textBook_manager.jar
```

Le package contient :

```
Application
JDBC Driver
Resources
```

Pré-requis :

```
Java installé
PostgreSQL installé
```

---

✅ Cette conception met en valeur :

* **architecture MVC**
* **programmation orientée objet**
* **modularité**
* **séparation des responsabilités**
* **gestion propre de la base de données**

---






