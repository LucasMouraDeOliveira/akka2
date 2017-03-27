# TP3 CAR - Akka

## Auteurs
Lucas Moura de Oliveira

Eliott Bricout

26/03/2017

## Compilation

Les jars �tant trop gros pour �tre d�pos�s tous les deux sur prof (max 20Mo), il faudra les compiler avec la commande suivante : 

	mvn package
	
Les deux jars utilis�s, Akka-Client.jar et Akka-Server.jar, se situent dans le dossier target � la racine du projet. Ils sont �galement disponibles en ligne � l'adresse suivante : 

	https://github.com/LucasMouraDeOliveira/akka2 (dossier target)

## Ex�cution


L'application est d�coup�e en deux parties, et donc deux jars ex�cutables ind�pendamment.

### Client

Akka-Client.jar permet de lancer le client de l'application. A partir de ce client on peut cr�er/supprimer des noeuds, cr�er/supprimer des liaisons entre ces noeuds, et envoyer des messages.
	
Le jar se lance avec la commande suivante : 
	
	java -jar Akka-Client.jar [config]
	
[config] est un param�tre optionnel permettant de sp�cifier le chemin d'acc�s � un fichier de configuration. Si ce param�tre est renseign�, le programme charge le fichier de configuration, qui doit contenir le sch�ma d'un graphe (format expliqu� ci-dessous), et cr�e ce graphe sur le ou les syst�mes Akka sp�cifi�s. 
	
Dans le cas ou le param�tre [config] n'est pas renseign�, aucun noeud ni aucune liaison ne sont cr��s.
	
### Serveur

Akka-Serveur.jar permet de lancer un serveur (syst�me d'acteurs Akka). Les noeuds cr��s par la partie client de l'application sont cr��s dans ce (ou ces) syst�me(s).

Le jar se lance avec la commande suivante : 
	
	java -jar Akka-Server.jar <port> <serverName>
	
<port> est un param�tre obligatoire qui prend une valeur num�rique (de pr�f�rence entre 1025 et 65536) qui permet de d�terminer sur quel port lancer le serveur.

<serverName> est un param�tre obligatoire qui prend une valeur textuelle, non vide, qui permet de d�terminer le nom apparent du syst�me d'acteur.

### Configuration de graphe

Voir le fichier graph.properties qui fournit un exemple de construction de graphe.
Les lignes au dessus de "---Actor---" permettent de cr�er des acteurs en sp�cifiant : 

	- le nom du systemActor
	- l'adresse du systemActor
	- le port
	- une liste d'acteurs

Chaque param�tre �tant s�par� par un espace

En dessous de la ligne "---Actor---" sont d�finis les liens entre les acteurs.

## Fonctionnalit�s

Les fonctionnalit�s de l'applications sont toutes � ex�cuter depuis le client (mis � part le lancement des serveurs). Chaque fonctionnalit� est associ�e � une commande ex�cutable depuis le terminal o� le client a �t� lanc�.

###  Liste des fonctionnalit�s

	create <serverName> <address> <port> <actorName> : cr�� un acteur sur un serveur distant.
	
	delete <actorName> : supprime un acteur distant. Tous les liens de voisinages de l'acteur sont supprim�s.
	
	link <actorName1> <actorName2> : cr�e un lien de voisinage entre deux acteurs.
	
	unlink <actorName1> <actorName2> : supprime un lien de voisinage entre deux acteurs.
	
	send <actorName> <message> : envoie un message � un acteur, qui le transmet � tous ses voisins.
	
	quit : arr�te l'actorSystem du client et quitte l'application.
	
Par commodit�, dans la plupart des commandes on passe seulement le nom de l'acteur et non pas son chemin d'acc�s complet. La cons�quence de ce choix est que deux acteurs, m�me sur des serveurs diff�rents, ne peuvent pas avoir le m�me nom. Lorsqu'un utilisateur tente de cr�er un acteur dont le nom est d�j� r�f�renc� par le client, la cr�ation �choue et un message d'erreur lui est renvoy�.

### Logs

Lorsqu'un acteur re�oit un message, celui-ci apparaitra dans la console depuis laquelle son actorSystem a �t� lanc�. 
Les messages sont de la forme : [sender -> receiver] message


