/*
* Author: Yuneng Jiang
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MazeCreator {
    
    //Variable for all MazeCreator instances.
    private Node maze[][];
    private FileReader file_reader;
    private Node starting_point;
    private static int ROW_COL_ARRAY_SIZE = 2;
    private int[] row_size_col_size = new int[ROW_COL_ARRAY_SIZE];
    private String global_file_name;
    private String unsolved_maze_string;
    private String solved_maze_string;
    
    /* The constructor for MazeCreator instances. It creates an instance of a FileReader that
    *  reads the .txt file passed in by the user.
    *  @Params: String file_name - the name of the .txt file containing the text version of the maze
    *  @Returns: None.
     */
    public MazeCreator(String file_name) throws FileNotFoundException {
        global_file_name = file_name;
        try {
            file_reader = new FileReader(file_name);
        }
        catch (FileNotFoundException ex) {
            ex.getMessage();
            throw new FileNotFoundException();
        }
    }
    
    /* This method uses the FileReader initialized by the MazeCreator constructor and parses the maze
    *  in order to obtain an internal representation of the maze. It first must count the number of
    *  lines the entire maze has, and then it restarts the reader in order to count the number of characters
    *  per line. If the maze is not in a rectangle or square shape, an error message will be printed out.
    *  @Params: None.
    *  @Returns: None.
     */
    public void read_and_parse_maze(){
        BufferedReader line_counter = new BufferedReader(file_reader);
        String line;
        int total_lines = 0;
        int num_characters = 0;
        
        //to obtain the number of lines there are, we must count it sequentially
        try {
            while ((line = line_counter.readLine()) != null) {
                num_characters = line.length();
                total_lines++;
            }
            line_counter.close();
        }
        catch (IOException ex) {
            ex.getMessage();
        }
        
        //start again from top of the file to actually parse the characters
        restart_file_reader();
        BufferedReader character_reader = new BufferedReader(file_reader);
        
        //Because we counted the lines and characters, we can define the maze size
        maze = new Node[total_lines][num_characters];
        row_size_col_size[0] = total_lines;
        row_size_col_size[1] = num_characters;
        
        int current_row = 0;
        try {
            while ((line = character_reader.readLine()) != null) {
                for(int i = 0; i < num_characters; i++) {
                    maze[current_row][i] = new Node(line.charAt(i));
                    maze[current_row][i].set_col_index(i);
                    maze[current_row][i].set_row_index(current_row);
                    if (line.charAt(i) == '!') {
                        starting_point = maze[current_row][i];
                    }
                }
                current_row++;
            }
            character_reader.close();
        }
        catch (IOException ex) {
            ex.getMessage();
        }
        catch(IllegalArgumentException ex) {
            ex.getMessage();
        }
        
        unsolved_maze_string = this.to_string();
    }
    
    /* This method is used to mark the solution path when a valid solution path has been found.
    *  @Params: ArrayList<Node> solution_path - an ArrayList containing all the Nodes that are part
    *           of the solution path.
    *  @Returns: None.
     */
    public void mark_solution_path(ArrayList<Node> solution_path) {
        for(int i = 0; i < solution_path.size(); i++) {
            solution_path.get(i).toggle_solution();
        }
        
        solved_maze_string = this.to_string();
        reset_maze();
    }
    
    /* This method resets the maze in the following manner:
    *  traversed - if a node has the traversed flag set to true, this will set it to false.
    *  solution - if a node has the solution flag set to true, this will set it to false.
    *  @Params: None.
    *  @Returns: None.
     */
    public void reset_maze() {
        for(int i = 0; i < row_size_col_size[0]; i++ ) {
            for(int j = 0; j < row_size_col_size[1]; j++) {
                if(maze[i][j].get_traversed()) {
                    maze[i][j].toggle_traversed();
                }
                if(maze[i][j].get_type() == Node.node_type.SOLUTION) {
                    maze[i][j].toggle_solution();
                }
            }
        }
        
    }
    
    /* This method returns the string representation of the entire maze. It calls the toString method
    *  of each of the Nodes inside of this maze and concatenates them into one string and then returns
    *  the string.
    *  @Params: None.
    *  @Returns: The string representation of the maze.
     */
    private String to_string() {
        String string_representation = "";
        
        for(int i = 0; i < row_size_col_size[0]; i++) {
            for(int j = 0; j < row_size_col_size[1]; j++) {
                string_representation = string_representation.concat(maze[i][j].to_string());
            }
            
            string_representation = string_representation.concat("\n");
        }
        
        return string_representation;
    }
    
    /* Helper method used in the method read_and_parse_maze to restart the FileReader
    *  @Params: None.
    *  @Returns: None.
     */
    private void restart_file_reader() {
        try {
            file_reader = new FileReader(global_file_name);
        }
        catch (FileNotFoundException ex) {
            ex.getMessage();
        }
    }
    
    /* Helper method to refresh the string representation of the mazes. Since the MazeCreator
    *  holds a string representation of both the unsolved and solved maze, it takes in a boolean to
    *  help differentiate between which to refresh.
    *  @Params: boolean solved - The boolean to decide which string representation to refresh.
    *           If this is true, it refreshes the solved maze's string representation.
    *           If this is false, it refreshes the unsolved maze's string representation.
    *  @Returns: None.
     */
    public void update_maze(boolean solved) {
        if(solved) {
            solved_maze_string = this.to_string();
            return;
        }
        unsolved_maze_string = this.to_string();
    }
    
    /* Getters and Setters */
    public String print_unsolved_maze() {
        return unsolved_maze_string;
    }
    
    public String print_solved_maze() {
        return solved_maze_string;
    }

    
    public Node get_starting_point() {
        return starting_point;
    }
    
    public Node get_node_at(int row_index, int col_index) {
        return maze[row_index][col_index];
    }
    
    public int[] get_row_col_size() {
        return row_size_col_size;
    }
    
    public Node[][] get_maze() {
        return maze;
    }
    
}
