import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MazeCreator {
    
    private Node maze[][];
    private FileReader file_reader;
    private Node starting_point;
    private static int ROW_COL_ARRAY_SIZE = 2;
    private int[] row_size_col_size = new int[ROW_COL_ARRAY_SIZE];
    
    public MazeCreator(String file_name) throws FileNotFoundException {
        try {
            file_reader = new FileReader(file_name);
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
            
            line_counter.close();
        }
        catch (IOException ex) {
            ex.getMessage();
        }
        
        //start again from top of the file to actually parse the characters
        BufferedReader character_reader = new BufferedReader(file_reader);
        
        //Because we counted the lines and characters, we can define the maze size
        this.maze = new Node[total_lines][num_characters];
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
}
