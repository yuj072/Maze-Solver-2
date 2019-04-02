import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver2 {
    private ArrayList<Node> solution_path = new ArrayList<>();
    private MazeCreator initialized_maze;
    private int DFS_nodes_added;
    private int BFS_nodes_added;
    private static int NUM_NEIGHBORS = 4;
    private static int WEST_INDEX = 0;
    private static int NORTH_INDEX = 1;
    private static int EAST_INDEX = 2;
    private static int SOUTH_INDEX = 3;
    public MazeSolver2(String file_name) throws FileNotFoundException {
        initialized_maze = new MazeCreator(file_name);
        initialized_maze.read_and_parse_maze();
    }
    
    public ArrayList<Node> get_solution_path(){
        return solution_path;
    }
    
    public boolean BFS_solve_maze() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(initialized_maze.get_starting_point());
        BFS_nodes_added = 1;
        Node current_node;
        boolean path_marked;
        
        while(!queue.isEmpty()) {
            if(queue.peek().get_type() == Node.node_type.END) {
                //System.out.println("BFS End Found");
                break;
            }
            
            current_node = queue.remove();
            current_node.toggle_traversed();
            
            Node[] current_node_neighbors = get_traversable_neighbors(current_node);
            
            for(int i = 0; i < NUM_NEIGHBORS; i++) {
                if(current_node_neighbors[i] != null) {
                    queue.add(current_node_neighbors[i]);
                    current_node_neighbors[i].set_predecessor(current_node);
                    BFS_nodes_added++;
                }
            }
        }
        
        if(queue.isEmpty()) {
            System.out.println("BFS NULL");
            return false;
        }
        
        path_marked = populate_solution_path(queue.peek());
        initialized_maze.mark_solution_path(solution_path);
        return path_marked;
    }
    
    public boolean DFS_solve_maze() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialized_maze.get_starting_point());
        DFS_nodes_added = 1;
        Node current_node;
        boolean path_marked;
        
        while(!stack.isEmpty()) {
            if(stack.peek().get_type() == Node.node_type.END) {
                //System.out.println("End found");
                break;
            }
            
            current_node = stack.pop();
            //System.out.println("Stack popped: " + current_node.get_type());
            current_node.toggle_traversed();
            
            Node[] current_node_neighbors = get_traversable_neighbors(current_node);
            
            for(int i = 0; i < NUM_NEIGHBORS; i++) {
                if(current_node_neighbors[i] != null) {
                    stack.push(current_node_neighbors[i]);
                    current_node_neighbors[i].set_predecessor(current_node);
                    DFS_nodes_added++;
                }
            }
        }
        
        if(stack.isEmpty()) {
            solution_path = null;
            System.out.println("Stack is empty");
        }
        
        path_marked = populate_solution_path(stack.peek());
        initialized_maze.mark_solution_path(solution_path);
        return path_marked;
    }
    
    private Node[] get_traversable_neighbors(Node node) {
        Node neighbors[] = new Node[NUM_NEIGHBORS];
        //set all initially to null
        for(int i = 0; i < NUM_NEIGHBORS; i++) {
            neighbors[i] = null;
        }
        int[] row_col_size = initialized_maze.get_row_col_size();
        
        //Western Neighbor
        if(node.get_col_index() - 1 >= 0) {
            if(!initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1).get_traversed()) {
                neighbors[WEST_INDEX] = initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1);
            }
        }
        
        //Northern Neighbor
        if(node.get_row_index() + 1 < row_col_size[0]) {
            if(!initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index()).get_traversed()) {
                neighbors[NORTH_INDEX] = initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index());
            }
        }
        
        //Eastern Neighbor
        if(node.get_col_index() + 1 < row_col_size[1]) {
            if(!initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1).get_traversed()) {
                neighbors[EAST_INDEX] = initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1);
            }
        }
        //Southern Neighbor
        if(node.get_row_index() - 1 >= 0) {
            if(!initialized_maze.get_node_at(node.get_row_index() - 1, node.get_col_index()).get_traversed()) {
                neighbors[SOUTH_INDEX] = initialized_maze.get_node_at(node.get_row_index() - 1, node.get_col_index());
            }
        }
        
        return neighbors;
    }
    
    private boolean populate_solution_path(Node end_node) {
        if(end_node == null) {
            return false;
        }
        
        solution_path.add(end_node);
        Node current_node = end_node;
        
        while(current_node.get_type() != Node.node_type.START) {
            current_node = current_node.get_predecessor();
            solution_path.add(current_node);
        }
        
        return true;
    }
    
    private boolean populate_solution_path(Queue<Node> queue) {
        if(queue.isEmpty()) {
            return false;
        }
        
        Node current_node = queue.remove();
        solution_path.add(current_node);
        
        while(current_node.get_type() != Node.node_type.START) {
            current_node = current_node.get_predecessor();
            solution_path.add(current_node);
        }
        
        queue.clear();
        return true;
    }
    
    public MazeCreator get_initialized_maze() {
        return initialized_maze;
    }
    
    public int get_DFS_nodes_added() {
        return DFS_nodes_added;
    }
    
    public int get_BFS_nodes_added() {
        return BFS_nodes_added;
    }
    
}
