# Alice and Bob

## Paramétrage
### Alice
* Balls: 10%
* Connerie: 30-40%
* Connaissances:
  * Position initiale
  * Présence d'un Wumpus
  * Présence de puits
  * Présence d'un lingot d'or

## Agent
### Direction
* Bas: 0
* Gauche: 1
* Haut: 2
* Droite: 3

## Poids
### Liste
* Lingot d'or: 9
* Case visitée: de 1 à 4
* Case actuelle: 1
* Safe: 0
* Défaut: -1
* Puit éventuel: -2
* Wumpus éventuel: -3
* Puit: -4
* Puit ou Wumpus éventuel: -5
* Wumpus: -6
* Mur: -9

### Explications
#### Safe
Les cases autour de l'agent sont à 0 dans le cas où il n'y a pas d'odeur ni de brise
sur la case actuelle

Quand une case est safe, elle ne peut plus être négative

#### Case actuelle
Elle passe à 1 car une case à proximité a été visitée

### Case visitée
Une case visitée est une case qu'on vient de quitter.

Son compteur s'incrémente de 1 si la case actuelle est <= 0

Le compteur peut aller jusqu'à 4, ce qui signifie que les 4 cases autour sont visitées

### Brise
Quand on est sur une case de brise, on set les cases autour à -2 si elles ne sont pas set

### Odeur
Quand on est sur une case d'odeur, on set les cases autour à -3 si elles ne sont pas set

### Brise et odeur
Quand on est sur une case avec une odeur et une brise, on passe les cases non set à -5