import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MazeCreator {
    
    private Node maze[][];
    private Node unsolved_maze[][];
    private Node solved_maze[][];
    private FileReader file_reader;
    private Node starting_point;
    private static int ROW_COL_ARRAY_SIZE = 2;
    private int[] row_size_col_size = new int[ROW_COL_ARRAY_SIZE];
    private String global_file_name;
    private String unsolved_maze_string;
    private String solved_maze_string;
    
    public MazeCreator(String file_name) throws FileNotFoundException {
        global_file_name = file_name;
        try {
            file_reader = new FileReader(file_name);
            System.out.print("Constructor: file read: " + file_name + '\n');
        }
        catch (FileNotFoundException ex) {
            ex.getMessage();
            throw new FileNotFoundException();
        }
    }
    
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
            System.out.println("Read and parse: Totals lines counted: " + total_lines + " total chars per line: " + num_characters);
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
            System.out.println("Read Maze:");
            while ((line = character_reader.readLine()) != null) {
                for(int i = 0; i < num_characters; i++) {
                    System.out.print(line.charAt(i));
                    maze[current_row][i] = new Node(line.charAt(i));
                    maze[current_row][i].set_col_index(i);
                    maze[current_row][i].set_row_index(current_row);
                    if (line.charAt(i) == '!') {
                        starting_point = maze[current_row][i];
                    }
                }
                current_row++;
                System.out.println();
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
    
    public Node[][] get_maze() {
        return maze;
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
    
    public void mark_solution_path(ArrayList<Node> solution_path) {
        for(int i = 0; i < solution_path.size(); i++) {
            solution_path.get(i).toggle_solution();
        }
        
        solved_maze_string = this.to_string();
        reset_maze();
    }
    
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
    
    private void restart_file_reader() {
        try {
            file_reader = new FileReader(global_file_name);
            System.out.print("Constructor: file read: " + global_file_name + '\n');
        }
        catch (FileNotFoundException ex) {
            ex.getMessage();
        }
    }
    
    public String print_unsolved_maze() {
        return unsolved_maze_string;
    }
    
    public String print_solved_maze() {
        return solved_maze_string;
    }
    
    public void update_maze(boolean solved) {
        if(solved) {
            solved_maze_string = this.to_string();
            return;
        }
        unsolved_maze_string = this.to_string();
    }
    
}
