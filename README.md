# COEVOLUTIONARY-LEARNING-SPATIALLY-DISTRIBUTED-POPULATIONS-
Evolving Cellular Automata
*The program uses a coevolution "default" strategy, where the CAs are treated as the host population ,and the ICs are treated as a parasite population that competes with CAs to give them challenging problems. 

*The populations are represented as 20x20 grid of (CA, IC) pairs. The grid uses periodic boundary conditions, so the top row, is connected to the bottom row, and the left-most column is connected to the right-most column. 

*A genetic algorithm is used to coevolve a population of 1-dimensional Cellular Automata (CAs) to solve the density > 0.5 task and perform the “density classification” task. 

*The goal was to find a CA that decides whether or not the IC contains a majority of 1s (has high density) or 0s.
 
*The CA correctly classifies an Initial Condition (IC) if it returns a state vector of all 1s ,if the IC had a majority 1s and a vector of all 0s if the IC had a majority 0s.

the link for the project presentation
https://www.canva.com/design/DAFTtKLfBmQ/AJS9xRT-HTYpvcJaZ3hmhA/edit?utm_content=DAFTtKLfBmQ&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton
