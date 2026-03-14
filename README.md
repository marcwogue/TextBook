# TextBook - Cahier de Texte Digital

TextBook est une application Swing moderne permettant de gérer des Unités d'Enseignement (UE), des enseignants et des rapports de séances (cahier de texte).

---

## 🚀 Guide d'installation

### 1. Prérequis
Assurez-vous d'avoir installé :
- **Java JDK 17** ou supérieur.
- **PostgreSQL** (version 14+ recommandée).
- Un IDE Java (IntelliJ IDEA, Eclipse, VS Code) ou un terminal.

### 2. Configuration de la Base de Données
Par défaut, l'application est configurée pour se connecter avec :
- **Hôte** : `localhost`
- **Port** : `5432`
- **Utilisateur** : `postgres`
- **Mot de passe** : `admin`

> [!TIP]
> Si vos identifiants diffèrent, modifiez le fichier [DatabaseConfig.java](file:///home/sharker/T%C3%A9l%C3%A9chargements/web/projct/textBook/TextBook/src/textbook/util/DatabaseConfig.java).

L'application **créera automatiquement** la base de données `textbook` et toutes les tables nécessaires lors du premier lancement grâce au `DatabaseInitializer`.

### 3. Compilation et Lancement

#### Via le script fourni (Linux/macOS)
Un script de vérification et de lancement est disponible à la racine :
```bash
chmod +x verify_setup.sh
./verify_setup.sh
```

#### Manuellement (Ligne de commande)
1. Téléchargez le pilote JDBC PostgreSQL (`postgresql-42.x.x.jar`).
2. Compilez le projet :
   ```bash
   javac -d bin -cp "lib/*" src/textbook/**/*.java
   ```
3. Lancez l'application :
   ```bash
   java -cp "bin:lib/*" textbook.Main
   ```

---

## 🎨 Design & Fonctionnalités
L'application utilise un style **Flat Design** moderne avec les fonctionnalités suivantes :
- **Dashboard** : Vue d'ensemble des rapports dès l'ouverture.
- **Gestion UE & Enseignants** : CRUD complet (Ajout, Liste, Modification, Suppression).
- **Rapports de Séances** : Saisie de rapports détaillés avec éditeur de texte extensible.

---

## 📁 Structure du projet
- `src/textbook/model` : Classes métier (POJOs).
- `src/textbook/dao` : Accès aux données via JDBC.
- `src/textbook/service` : Logique métier et validation.
- `src/textbook/controller` : Coordination entre Vue et Service.
- `src/textbook/view` : Interfaces graphiques Swing (Flat Design).
- `src/textbook/util` : Utilitaires (Base de données, Styles).
# TextBook
