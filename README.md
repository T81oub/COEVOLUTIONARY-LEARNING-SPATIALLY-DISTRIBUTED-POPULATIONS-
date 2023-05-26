# Evolving Cellular Automata

This project implements a coevolutionary approach where Cellular Automata (CAs) and Initial Conditions (ICs) compete to solve density classification tasks. The goal is to evolve a CA that can accurately classify ICs based on their density.

## Coevolution Strategy

The program follows a coevolution "default" strategy, treating CAs as the host population and ICs as the parasite population. Both populations are represented as a 20x20 grid of (CA, IC) pairs, utilizing periodic boundary conditions.

## Genetic Algorithm

A genetic algorithm is employed to coevolve a population of 1-dimensional CAs. The algorithm aims to find a CA capable of solving the density classification task where the IC contains a majority of 1s or 0s.

## Density Classification Task

The CA correctly classifies an Initial Condition (IC) by returning a state vector of all 1s if the IC has a majority of 1s (high density) or a vector of all 0s if the IC has a majority of 0s.

## Project Presentation

You can find the project presentation [here](https://www.canva.com/design/DAFTtKLfBmQ/AJS9xRT-HTYpvcJaZ3hmhA/edit?utm_content=DAFTtKLfBmQ&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton). It provides detailed information, visualizations, and additional insights about the project.

