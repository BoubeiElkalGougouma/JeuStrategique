# Jeu Stratégique – TP POO ISIL 2025/2026

## Description
Projet de jeu stratégique développé en Java dans le cadre du TP de Programmation Orientée Objet.

## Fonctionnalités
- Gestion des unités
- Logique de combat
- Interface console
  

## Structure du projet
src/
├── app/
│   └── Main.java (Point d'entrée)
│
├── interfaces/
│   └── Producible.java
│
├── model/
│   ├── TerrainType.java (enum)
│   ├── ResourceType.java (enum)
│   ├── Cell.java
│   ├── GameMap.java
│   ├── Unit.java (abstract)
│   ├── Soldier.java
│   ├── Archer.java
│   ├── Cavalry.java
│   ├── Building.java (abstract)
│   ├── CommandCenter.java
│   ├── TrainingCamp.java
│   ├── Mine.java
│   ├── Player.java
│   ├── CombatResult.java
│   └── MoveResult.java
│
├── moteur/
|   ├── BuildingManager.java
│   ├── CombatSystem.java
│   ├── TurnManager.java
|   ├── UnitManager.java
│   └── Game.java
│
└── ui/
    └── ConsoleUI.java

## Auteur
Boubei Elkal Gougouma
Dusabe Landry
Naimi Meriem
Niyibogora Jean Claude
