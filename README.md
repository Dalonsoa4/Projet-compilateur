Projet compilateur du cours LINFO2132 à l'UCL. 

David Alonso 2025-2026

Ce projet se divise en 5 parties : 

- Lexer
- Parser
- Analyse sémantique
- Génération de code
- Extra features

Partie 1 : Lexer
-

NB: Pour cette partie, j'ai repris le code de mon Lexer que j'ai soumis pour le projet l'année passée \
Le but du Lexer est de lire le code en input et de renvoyer une suite de Token 

Partie 2 : Parser
-
Objectifs : Transformer les token produits par le Lexer et les transformer en un arbre qui représente la structure\
du programme. 

Obligations :

- Cet arbre doit être un AST(Abstract Syntax Tree). 
- L'AST doit respecter la syntaxe du langage
- Il faut implémenter un "recursive-descent parsing"
- Il faut reporter une erreur lorsque la syntaxe n'est pas respectée
- Il ne faut pas reporter les erreurs de sémantique

Expliquations :
- Un AST c'est une représentation du code sous forme d'arbre qui nous donne l'ordre logique des opérations
- "recursive-descent parsing" est un Parser où chaque règle de la grammaire devient une fonction , ces fonctions s'appellent récursivement jusqu'à parcourir tout l'arbre
- Si certaines parties de la grammaire n'est pas LL(1), on peut écrire des fonctions speciales pour la parser
- une grammaire est LL(1) si elle n'a besoin que de un token pour pouvoir choisir la bonne règle

L'énoncé du projet mentionne que note


