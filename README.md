# MédiLabo

MédiLabo est une application permettant de gérer les données de patients et
d'évaluer leur niveau de risque de développer un diabète à partir de leurs
informations personnelles et des notes médicales associées.

L'application repose sur une architecture en microservices composée de :

- `gateway` : point d'entrée de l'application et gestion de l'authentification ;
- `patient-service` : gestion des informations des patients, stockées dans MySQL ;
- `note-service` : gestion des notes médicales, stockées dans MongoDB ;
- `risk-service` : calcul du niveau de risque de diabète à partir des données
  fournies par patient-service et note-service.
- `frontend` : interface React, construite avec Vite et servie par Nginx ;

Le frontend React, les microservices backend et les bases de données sont
orchestrés avec Docker Compose.

Le frontend est construit avec Vite, puis ses fichiers sont servis par Nginx.

## Technologies

### Backend

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Spring Security
- Spring Data JPA
- Spring Data MongoDB
- Maven

### Frontend

- React
- Vite
- Nginx

### Bases de données

- MySQL
- MongoDB

### Conteneurisation

- Docker
- Docker Compose

## Architecture

L'application est composée de plusieurs services indépendants communiquant entre eux via le réseau Docker.

| Composant | Port | Accessibilité |
|---|---:|---|
| Gateway | 8081 | Exposé sur l'hôte |
| Patient Service | 8080 | Réseau Docker uniquement |
| Note Service | 8082 | Réseau Docker uniquement |
| Risk Service | 8083 | Réseau Docker uniquement |
| MySQL | 3306 | Réseau Docker uniquement |
| MongoDB | 27017 | Réseau Docker uniquement |
| Frontend | 5173 → 80 | Interface accessible sur l'hôte |

### Communication entre les services

Le navigateur charge l'interface servie par Nginx à l'adresse
http://localhost:5173.

Le code React exécuté dans le navigateur appelle ensuite directement
la Gateway à l'adresse http://localhost:8081 pour accéder aux API.
Nginx sert les fichiers du frontend ; il ne relaie pas les appels aux API.

La Gateway redirige ensuite les requêtes vers le microservice concerné :

- `/patients/**` → `patient-service`
- `/notes/**` → `note-service`
- `/risk/**` → `risk-service`

Le `risk-service` communique également avec `patient-service` et `note-service`
afin de récupérer les informations nécessaires au calcul du niveau de risque.

Dans l'environnement Docker, les services communiquent entre eux grâce aux noms
définis dans `docker-compose.yml`, par exemple :

`http://patient-service:8080`

Les bases de données sont également accessibles aux microservices par leur nom
de service Docker :

- `mysql:3306`
- `mongodb:27017`

Les données de MySQL et MongoDB sont stockées dans des volumes Docker afin d'être
conservées après l'arrêt ou la recréation des conteneurs.

## Prérequis

Pour lancer l'application, les outils suivants sont nécessaires :

- Docker
- Docker Compose

Docker et Docker Compose suffisent pour lancer l'application complète.

Java, Maven, Node.js, npm, MySQL et MongoDB sont fournis par les images
utilisées pour construire ou exécuter les différents composants.

Java 21 reste nécessaire pour exécuter les tests backend localement.
Node.js et npm sont nécessaires pour développer le frontend hors Docker.

## Installation et lancement

### 1. Cloner le projet

git clone https://github.com/FionaRog/mediLabo.git
cd mediLabo

### 2. Configurer les variables d'environnement

Un fichier `.env.example` est fourni à la racine du projet.

Créez un fichier `.env` à partir de ce modèle :

```env
MYSQL_ROOT_PASSWORD=your_mysql_password

MYSQL_APP_USERNAME=medilabo_app
MYSQL_APP_PASSWORD=your_mysql_app_password

APP_USERNAME=your_app_username
APP_PASSWORD=your_app_password
```

Remplacez les valeurs d'exemple par les valeurs souhaitées.

Le fichier `.env` contient des informations sensibles et n'est pas versionné
dans le dépôt Git.

### 3. Lancer l'application

Depuis la racine du projet, exécutez :

```bash
docker compose up -d --build
```

Cette commande construit les images Docker du frontend et des microservices,
puis démarre l'ensemble de l'application, y compris MySQL et MongoDB.

Le frontend est compilé avec Vite pendant la construction de son image.
Les fichiers générés sont ensuite servis par Nginx dans le conteneur,
sans utiliser le serveur de développement Vite.

Aucune commande npm n'est nécessaire pour ce lancement avec Docker.

Une fois les services démarrés, ouvrez l'application à l'adresse :

http://localhost:5173

La Gateway reste accessible sur http://localhost:8081.

Les requêtes adressées aux API doivent passer par la Gateway et nécessitent
l'authentification configurée avec les variables `APP_USERNAME` et
`APP_PASSWORD`.


### 4. Vérifier les conteneurs

Pour vérifier que les différents conteneurs sont démarrés :

```bash
docker compose ps
```

Les bases de données doivent notamment apparaître comme `healthy` avant que
les microservices qui en dépendent puissent démarrer.


### 5. Arrêter l'application

Pour arrêter et supprimer les conteneurs :

```bash
docker compose down
```

Les volumes Docker ne sont pas supprimés par cette commande. Les données
MySQL et MongoDB sont donc conservées pour le prochain démarrage.

## Développement du frontend hors Docker — optionnel

Pour bénéficier du rechargement automatique pendant le développement,
le frontend peut être lancé avec Vite.
Cette option nécessite Node.js et npm installés sur votre machine.

Arrêtez d'abord le conteneur frontend pour libérer le port 5173 :

```bash
docker compose stop frontend
```

Depuis le dossier frontend, installez les dépendances :

```bash
npm ci
```

Créez un fichier `.env` dans ce dossier contenant :

```env
VITE_API_URL=http://localhost:8081
```

Puis démarrez Vite :

```bash
npm run dev
```

Les services backend doivent rester démarrés.

Pour revenir au frontend Docker, arrêtez Vite avec Ctrl + C, puis exécutez
depuis la racine du projet :

```bash
docker compose up -d --build frontend
```

Dans l'image Docker, l'URL de l'API est fournie à Vite pendant la construction
par l'argument `VITE_API_URL`, dont la valeur par défaut est
`http://localhost:8081`. Le fichier `.env` local du frontend est exclu
du contexte de construction.

Après une modification du code frontend ou de cette URL, reconstruisez
l'image avec la commande ci-dessus.

## Authentification et accès aux API

L'accès aux API est sécurisé au niveau de la Gateway avec Spring Security.

Les identifiants utilisés pour l'authentification sont définis dans le fichier
`.env` à la racine du projet :

```env
APP_USERNAME=your_app_username
APP_PASSWORD=your_app_password
```

L'authentification utilise HTTP Basic.

Après authentification, le frontend récupère un jeton CSRF avec
`GET /auth/csrf`. Le navigateur conserve le cookie de session associé
et le transmet avec les requêtes configurées avec `credentials: 'include'`.

Les appels aux API continuent d'utiliser l'authentification HTTP Basic.
Pour les requêtes POST et PUT, le frontend transmet également le jeton
CSRF dans l'en-tête indiqué par la réponse de `/auth/csrf`.

La déconnexion utilise POST /auth/logout, également protégé par CSRF.
Elle invalide la session côté serveur. Après succès, le frontend efface
les identifiants et le jeton conservés en mémoire.

L'environnement local utilise HTTP.

Les requêtes vers les différents microservices doivent passer par la Gateway,
accessible sur le port `8081`.

Les principales routes sont :

- `http://localhost:8081/patients/**` pour la gestion des patients ;
- `http://localhost:8081/notes/**` pour la gestion des notes médicales ;
- `http://localhost:8081/risk/**` pour l'évaluation du niveau de risque.

Les services patient-service, note-service et risk-service ainsi que les bases
de données ne publient aucun port sur l'hôte. Ils communiquent sur le réseau
Docker interne.

Le navigateur accède au frontend sur le port 5173 et appelle les API
via la Gateway sur le port 8081.

## Tests

Les microservices disposent de tests permettant de vérifier le comportement
des différentes couches de l'application.

Les tests d'un microservice peuvent être exécutés depuis son répertoire avec :

```bash
mvn test
```

Ils peuvent également être exécutés avec le Maven Wrapper :

```bash
sh mvnw test
```

Sous Windows :

```powershell
.\mvnw.cmd test
```

Lors de la construction des images Docker, les tests ne sont pas exécutés,
afin d'éviter de les relancer à chaque construction de l'image. Ils doivent
donc être exécutés séparément avant la construction ou le déploiement de
l'application.

### Configuration des tests

L'exécution des tests depuis un terminal nécessite Java 21.
Le Maven Wrapper permet de les lancer sans installer Maven séparément.

Les configurations placées dans `src/test/resources/application.properties`
sont réservées aux tests et ne sont pas intégrées au JAR applicatif.
Elles ne modifient pas la configuration utilisée par Docker Compose.

#### patient-service

Les tests utilisent une base H2 en mémoire, configurée dans
`patient-service/src/test/resources/application.properties`.

Cette base est distincte de MySQL. Son schéma est créé puis supprimé
lors de la fermeture du contexte de test. Le chargement de `data.sql`
est désactivé pour ces tests.

#### note-service

Le fichier `note-service/src/test/resources/application.properties`
définit une URI MongoDB locale dédiée aux tests, sans identifiants.

Cette configuration permet de charger le contexte Spring sans définir
la variable d'environnement `MONGODB_URI`. Elle ne démarre pas MongoDB.

Les tests unitaires du service utilisent un repository simulé.
Le test `contextLoads` vérifie le chargement du contexte, mais ne garantit
pas la disponibilité de MongoDB ni le fonctionnement des opérations en base.

#### gateway

Le fichier `gateway/src/test/resources/application.properties`
définit des identifiants fictifs :

- `app.security.username=testuser`
- `app.security.password=testpassword`

Les tests peuvent ainsi démarrer sans les variables d'environnement
`APP_USERNAME` et `APP_PASSWORD` utilisées par l'application.

Ces valeurs sont uniquement destinées aux tests et peuvent être versionnées.

#### risk-service

Aucun fichier de configuration supplémentaire n'est nécessaire actuellement.
Les URL des services possèdent des valeurs locales par défaut.

Les tests unitaires du calcul du risque utilisent des clients HTTP simulés :
ils n'appellent pas patient-service ou note-service.
Le chargement du contexte seul ne vérifie pas la disponibilité de ces services.

## Green Code et pistes d'amélioration

Le Green Code consiste à limiter les ressources nécessaires au fonctionnement
d’un logiciel : calculs, mémoire, stockage et échanges réseau, tout en
répondant aux besoins des utilisateurs.

### Mesures déjà mises en œuvre

- **Construction Docker en plusieurs étapes** : utilisation de Dockerfiles multi-stage 
  afin de ne conserver dans les images finales que les éléments nécessaires 
  à l'exécution des microservices.
- **Utilisation d’un JRE pour les services Java** : les images d’exécution
  n’embarquent pas le JDK complet.
- **Réutilisation des couches Docker** : lorsque les dépendances ne changent
  pas, les couches correspondantes peuvent être réutilisées lors des builds.
- **Utilisation de fichiers `.dockerignore`** : les fichiers envoyés
  au moteur Docker sont limités lors de la construction des images.

### Pistes d'amélioration

Afin de poursuivre la démarche Green Code, plusieurs actions pourraient être
étudiées :

**Mesurer les performances**

Lighthouse, accessible depuis les outils de développement de Chrome,
permettrait d’analyser les performances de l’interface et d’identifier
des optimisations de chargement.

Les mesures seraient comparées avant et après modification, dans des
conditions similaires. Lighthouse ne mesure toutefois pas directement
la consommation énergétique : une amélioration de performance ne suffit
pas à quantifier un gain environnemental.

**Réduire la taille des images Docker**

Des variantes plus légères des images Java, comme celles d’Eclipse Temurin
basées sur Alpine, pourraient être étudiées après vérification de leur
compatibilité avec l’application.

L’objectif serait de réduire le stockage et les volumes téléchargés lors
des déploiements. 

**Mettre en place un cache dans les services**

Un cache pourrait éviter des requêtes répétées en base pour des données
fréquemment consultées, comme les informations d’un patient ou ses notes.

Sa taille et sa durée de conservation devraient être limitées. Les entrées
concernées devraient être actualisées ou invalidées après une modification
de patient ou l’ajout d’une note, afin de préserver la fraîcheur des données
affichées et utilisées pour le calcul du risque.

**Réduire les appels réseau redondants**

Actuellement, `risk-service` appelle `patient-service` et `note-service`
pour obtenir les données nécessaires à l’évaluation du risque.

Une évolution serait de séparer la récupération des données du calcul,
afin que `assessRisk` puisse recevoir directement les informations nécessaires.
Cela permettrait d’étudier la réutilisation de données déjà chargées
par le frontend, lorsqu’elles sont disponibles.

Déplacer les appels dans le contrôleur ne réduirait pas leur nombre :
le bénéfice dépendrait des appels réellement évités et du volume total
échangé. Cette évolution nécessiterait également de garantir la validité et
la fiabilité des données utilisées.

**Optimiser le frontend** 

Évaluer les dépendances et les ressources JavaScript, CSS et graphiques
permettrait d'identifier celles qui peuvent être supprimées ou allégées.

L'intérêt de l'arrière-plan animé pourrait également être comparé à celui
d'un fond statique, afin de limiter les traitements purement décoratifs.

**Suivre l'évolution de l'application** 

Mesurer les effets des optimisations mises en place sera nécessaire afin de vérifier 
qu'elles apportent réellement une réduction des ressources utilisées.