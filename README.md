# TP3 CAR - Akka

## Auteurs
Lucas Moura de Oliveira

Eliott Bricout

26/03/2017

## Exécution

L'application est découpée en deux parties, et donc deux jars exécutables indépendamment.

### Client

Akka-Client.jar permet de lancer le client de l'application. A partir de ce client on peut créer/supprimer des noeuds, créer/supprimer des liaisons entre ces noeuds, et envoyer des messages.
	
Le jar se lance avec la commande suivante : 
	
	java -jar Akka-Client.jar [config]
	
[config] est un paramètre optionnel permettant de spécifier le chemin d'accès à un fichier de configuration. Si ce paramètre est renseigné, le programme charge le fichier de configuration, qui doit contenir le schéma d'un graphe (format expliqué ci-dessous), et crée ce graphe sur le ou les systèmes Akka spécifiés. 
	
Dans le cas ou le paramètre [config] n'est pas renseigné, aucun noeud ni aucune liaison ne sont créés.
	
### Serveur

Akka-Serveur.jar permet de lancer un serveur (système d'acteurs Akka). Les noeuds créés par la partie client de l'application sont créés dans ce (ou ces) système(s).

Le jar se lance avec la commande suivante : 
	
	java -jar Akka-Server.jar <port> <serverName>
	
<port> est un paramètre obligatoire qui prend une valeur numérique (de préférence entre 1025 et 65536) qui permet de déterminer sur quel port lancer le serveur.

<serverName> est un paramètre obligatoire qui prend une valeur textuelle, non vide, qui permet de déterminer le nom apparent du système d'acteur.

### Configuration de graphe

## Fonctionnalités

Les fonctionnalités de l'applications sont toutes à exécuter depuis le client (mis à part le lancement des serveurs). Chaque fonctionnalité est associée à une commande exécutable depuis le terminal où le client a été lancé.

###  Liste des fonctionnalités

	create <serverName> <address> <port> <actorName> : créé un acteur sur un serveur distant.
	
	delete <actorName> : supprime un acteur distant. Tous les liens de voisinages de l'acteur sont supprimés.
	
	link <actorName1> <actorName2> : crée un lien de voisinage entre deux acteurs.
	
	unlink <actorName1> <actorName2> : supprime un lien de voisinage entre deux acteurs.
	
	send <actorName> <message> : envoie un message à un acteur, qui le transmet à tous ses voisins.
	
	quit : arrête l'actorSystem du client et quitte l'application.
	
Par commodité, dans la plupart des commandes on passe seulement le nom de l'acteur et non pas son chemin d'accès complet. La conséquence de ce choix est que deux acteurs, même sur des serveurs différents, ne peuvent pas avoir le même nom. Lorsqu'un utilisateur tente de créer un acteur dont le nom est déjà référencé par le client, la création échoue et un message d'erreur lui est renvoyé.

### Logs

Lorsqu'un acteur reçoit un message, celui-ci apparaitra dans la console depuis laquelle son actorSystem a été lancé. 
Les messages sont de la forme : [sender -> receiver] message



