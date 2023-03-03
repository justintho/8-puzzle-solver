# 8 Puzzle Solver

Overview: This project is an 8 Puzzle Solver which uses artificial intelligence concepts to effectively solve the puzzles using two different methods. An 8 Puzzle is a puzzle where there are 9 tiles, where one tile is a blank space. The goal is to slide and rearrange the tiles until each tile is in the correct position. In this project, I used two different heuristic functions, the number of misplaced tiles and the Manhattan distance, and compare the time and cost for using each to solve random or manually inputted puzzles.

<h2>Sample Output</h2>

```
CS 4200 Project 1: 8-Puzzle

Select Input Type:
[1] Random Input
[2] Manual Input
[3] Exit
Input: 1

Enter Solution Depth (2-20):
Input: 8

Puzzle:
4 3 2
1 7 5
6 8 0

Total Inversions: 8
The puzzle is solvable.

Select H Function:
[1] H1
[2] H2
Input: 1
-------------------------------------------------------
Puzzle:
4 3 2
1 7 5
6 8 0

Step 1:
4 3 2
1 7 5
6 0 8

Step 2:
4 3 2
1 0 5
6 7 8

Step 3:
4 3 2
0 1 5
6 7 8

Step 4:
0 3 2
4 1 5
6 7 8

Step 5:
3 0 2
4 1 5
6 7 8

Step 6:
3 1 2
4 0 5
6 7 8
Step 7:
3 1 2
0 4 5
6 7 8

Step 8:
0 1 2
3 4 5
6 7 8

Puzzle Solved!
-------------------------------------------------------
Time: 0.2263 ms
Cost: 35
