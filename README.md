# Maze Solver 2

Project Type: Independent

# Motivation 

When I first learned about data structures during my sophomore year of college, 
the data structures seemed so abstract and pointless. Everything felt like a small variation
 of arrays, so why couldn't I implement this with an array? It was not until the professor 
introduced the Maze Solver assignment that I realized the real world application of the data
 structures we were learning in class. Thus for my independent project, I decided to redo 
and expand the assignment. I tried my best to follow clean coding practices and added critical
 nodes to my application.

The inspiration for adding critical nodes came from this video:
(Maze Solving - Computerphile) : 
https://www.youtube.com/watch?v=rop0W4QDOUI 

# Summary

This application performs Depth-First-Search (DFS) and Breath-First-Search (BFS) on 
a maze inputted by the user. It outputs the number of nodes each search went through to find 
an exit (if there exists one in the maze). Next, it will analyze the maze and mark the critical 
nodes. Then, it will run the searches again using only the critical nodes found through the analysis. 

# How to use

The maze to input into the program will be a .txt (text file) and the location of 
the .txt file will be passed in as a command line argument for the program. There will be 4 symbols 
representing different tiles one can commonly find in mazes:

	'#' (the hashtag character) represents a wall.
	' ' (the space character) represents a path.
	'!' (the exclamation mark character) represents the starting point.
	'$' (the dollar sign character) represents the exit.

Note: Do not include single quotes when inputting the characters into the .txt file. Critical nodes 
will be marked by the program with a ‘%’ (the modulo character).

# Limitations

- The current version does not support mazes with multiple starting points and exits. The 
search will end as soon as it finds one exit.

- The current version does not support irregularly shaped mazes. All mazes should be a rectangle
or square shape. Knowing this, please do not include a newline character after the end of the 
maze inside the .txt file. 

