import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver2 {
    private ArrayList<Node> solution_path = new ArrayList<>();
    private MazeCreator initialized_maze;
    
    public MazeSolver2(String file_name) throws FileNotFoundException {
        initialized_maze = new MazeCreator(file_name);
    }
    
    public ArrayList<Node> get_solution_path(){
        return solution_path;
    }
    
    private void BFS_solve_maze() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(initialized_maze.get_starting_point());
        Node current_node;
        
        while(!queue.isEmpty()) {
            current_node = queue.remove();
            current_node.toggle_traversed();
            
            if(current_node.get_type() == Node.node_type.END) {
                break;
            }
            
            ArrayList<Node> current_node_neighbors = get_traversable_neighbors(current_node);
            
            for(int i = 0; i < current_node_neighbors.size(); i++) {
                queue.add(current_node_neighbors.get(i));
            }
            
        }
    }
    
    private void DFS_solve_maze() {
    
    }
    
    private ArrayList<Node> get_traversable_neighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int[] row_col_size = initialized_maze.get_row_col_size();
        
        //Western Neighbor
        if(node.get_col_index() - 1 >= 0) {
            if(!initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1).get_traversed()) {
                neighbors.add(initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1));
            }
        }
        
        //Northern Neighbor
        if(node.get_row_index() + 1 < row_col_size[0]) {
            if(!initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index()).get_traversed()) {
                neighbors.add(initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index());
            }
        }
        
        //Eastern Neighbor
        if(node.get_col_index() + 1 < row_col_size[1]) {
            if(!initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1).get_traversed()) {
                neighbors.add(initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1));
            }
        }
        //Southern Neighbor
        if(node.get_row_index() - 1 >= 0) {
            if(!initialized_maze.get_node_at(node.get_row_index() - 1, node.get_col_index()).get_traversed()) {
                neighbors.add(initialized_maze.get_node_at(node.get_row_index() - 1, node.get_col_index()));
            }
        }
        
        return neighbors;
    }
}
