# meeting-planner-back-end
Le projet Meeting Planner Backend est une application de planification de réunions qui permet aux aider l'utilisateurs a  réserver. L'application utilise une base de données relationnelle pour stocker les données et utilise le mode "create" pour réinitialiser la base de données à chaque exécution, garantissant ainsi un environnement cohérent pour les tests.
L'architecture du projet repose sur le framework Spring, en utilisant plusieurs fonctionnalités et concepts offerts par Spring. L'une des fonctionnalités clés utilisées est l'aspect Spring AOP (Aspect-Oriented Programming).Cette approche favorise une conception modulaire et facilite la maintenance du code.
Le projet est structuré en différentes couches, dont notamment :

Couche de contrôle : Cette couche contient les contrôleurs REST qui exposent les API pour les opérations de gestion des salles de réunion et des réservations. Les contrôleurs sont testés à l'aide de l'annotation @WebMvcTest pour s'assurer de leur bon fonctionnement.
Couche de service : Cette couche contient les services métier qui encapsulent la logique de traitement des salles de réunion et des réservations. Les services sont testés à l'aide de la classe CommandLineRunner pour vérifier la sauvegarde des objets métier dans la base de données,et avec @springboottest pour teste la methode qui return la bonne salle .
