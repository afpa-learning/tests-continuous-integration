# Mise en place d'un ORM

L'objectif de ce projet est de développer une Rest API.

## Compétences abordées
- implémentation d'entités
- configuration d'un projet Spring pour la communication avec une BDD PostgreSQL

## Lancer le projet

1. Démarrer le SGBD en utilisant Docker :
```bash
docker compose up
```

2. Lancer le serveur Tomcat local qui héberge Spring avec le "goal" maven suivant (rendu possible grace au plugin maven spring boot)
```bash
mvn spring-boot:run
```

## Marche à suivre

Suivez les directive du document "rest-api-orm.pdf" situé dans le sous-dossier "todo" et complétez les "TODO".

Bon courage !
