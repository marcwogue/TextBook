Voici une **reconception claire et cohérente** du module.

---

# Module : Cahier de Texte Digital

## Objectif

Permettre d’enregistrer les rapports des séances de cours, conserver leur résumé et consulter l’historique des enseignements (cahier de texte numerique ).

Le module couvre **tout le processus d’un rapport de séance** :

1. définir les matières
2. définir les enseignants
3. enregistrer une séance avec son résumé
4. consulter l’historique

---

# 1. Gestion des matières (UE)

### But

Créer le référentiel des matières utilisées dans les rapports de séance.

### Données

Table **UE**

| Champ        | Description               |
| ------------ | ------------------------- |
| code_ue (PK) | code unique de la matière |
| intitule     | nom de la matière         |

### Fonctions

* ajouter une matière
* afficher la liste des matières
* supprimer une matière

---

# 2. Gestion des enseignants

### But

Définir les enseignants pouvant apparaître dans les rapports de séance.

### Données

Table **enseignant**

| Champ          | Description         |
| -------------- | ------------------- |
| matricule (PK) | identifiant unique  |
| nom            | nom de l’enseignant |
| prenom         | prénom              |

### Fonctions

* ajouter un enseignant
* afficher les enseignants
* supprimer un enseignant

---

# 3. Gestion des rapports de séance

### But

Enregistrer les informations d’une séance de cours avec son résumé.

Chaque enregistrement correspond à **un rapport de séance**.

### Données

Table **rapprt**

| Champ          | Description         |
| -------------- | ------------------- |
| id_seance (PK) | identifiant auto    |
| code_ue (FK)   | matière             |
| matricule (FK) | enseignant          | 
| date_seance    | date du cours       |
| heure_debut    | début               |
| heure_fin      | fin                 |
| resume         | résumé de la séance |

### Fonctions

* ajouter un rapport de séance
* consulter les rapports
* supprimer un rapport

---

# 4. Consultation de l’historique

Affichage des séances sous forme de tableau.

Exemple :

heure| Date | UE | Enseignant | Résumé |
-----| ---- | -- | ---------- | ------ |

ue-> libelle
enseignant-> nom prenom
heure -> debut - fin

Fonctions :

* tri par date
* affichage chronologique des séances

---

# Modèle relationnel

```
UE
code_ue (PK)
intitule

ENSEIGNANT
matricule (PK)
nom
prenom

rapport
id_seance (PK)
code_ue (FK)
matricule (FK)
date_seance
heure_debut
heure_fin
resume
```

Relations :

* une **séance appartient à une UE**
* une **séance est dispensée par un enseignant**



# Pages de l'application

### 1. Accueil

Menu :

* matières
* enseignants
* nouveau rapport de séance
* historique

---

### 2. Gestion des matières

Fonctions :

* ajouter
* afficher
* supprimer

---

### 3. Gestion des enseignants

Fonctions :

* ajouter
* afficher
* supprimer

---

### 4. Nouveau rapport de séance

Formulaire :

* matière
* enseignant
* date
* heure début
* heure fin
* résumé

---

### 5. Historique des séances

Tableau des rapports enregistrés.

---

# Livrables

### Base de données

Script SQL de création des tables.

### Code

Application contenant :

* gestion des matières
* gestion enseignants
* saisie de séance
* consultation

### Application exécutable

Programme prêt à lancer.

### README

Explique :

* installation
* configuration
* lancement
* test.


