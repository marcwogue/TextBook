#!/bin/bash
# Script de vérification pour TextBook

PROJECT_DIR="TextBook"
LIB_DIR="$PROJECT_DIR/lib"
SRC_DIR="$PROJECT_DIR/src"
BIN_DIR="$PROJECT_DIR/bin"
JDBC_JAR="$LIB_DIR/postgresql-42.7.3.jar"

mkdir -p $BIN_DIR

echo "--- Compilation ---"
javac -cp "$JDBC_JAR" -d "$BIN_DIR" $(find "$SRC_DIR" -name "*.java")

if [ $? -eq 0 ]; then
    echo "Compilation réussie."
    echo "--- Test d'initialisation de la base de données ---"
    # On lance seulement le Main, qui va appeler DatabaseInitializer
    # Note: On a besoin d'un serveur X pour Swing, mais ici on veut surtout voir les logs console.
    java -cp "$BIN_DIR:$JDBC_JAR" textbook.app.Main
else
    echo "Échec de la compilation."
    exit 1
fi
