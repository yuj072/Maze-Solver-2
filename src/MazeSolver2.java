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
    private int DFS_critical_nodes_added;
    private int BFS_critical_nodes_added;
    private static int NUM_NEIGHBORS = 4;
    private static int WEST_INDEX = 0;
    private static int NORTH_INDEX = 1;
    private static int EAST_INDEX = 2;
    private static int SOUTH_INDEX = 3;
    
    public MazeSolver2(String file_name) throws FileNotFoundException {
        initialized_maze = new MazeCreator(file_name);
        initialized_maze.read_and_parse_maze();
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
    
    public boolean BFS_critical_solve_maze() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(initialized_maze.get_starting_point());
        BFS_critical_nodes_added = 1;
        Node current_node;
        Node processing_node;
        boolean path_marked;
        int current_node_list_size;
        
        while(!queue.isEmpty()) {
            if(queue.peek().get_type() == Node.node_type.END) {
                break;
            }
            
            current_node = queue.remove();
            current_node.toggle_traversed();
            
            current_node_list_size = current_node.get_critical_list_size();
            for(int i = 0; i < current_node_list_size; i++) {
                processing_node = current_node.get_critical_list_element();
                if(!processing_node.get_traversed() && !queue.contains(processing_node)) {
                    queue.add(processing_node);
                    processing_node.set_predecessor(current_node);
                    BFS_critical_nodes_added++;
                }
            }
        }
        
        if(queue.isEmpty()) {
            System.out.println("Critical Queue Empty");
            return false;
        }
    
        //System.out.println("Critical BFS queue not empty, peek element: " + queue.peek().get_type());
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
    
    public boolean DFS_critical_solve_maze() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialized_maze.get_starting_point());
        DFS_critical_nodes_added = 1;
        Node current_node;
        Node processing_node;
        boolean path_marked;
        int current_node_list_size;
        
        while(!stack.isEmpty()) {
            if(stack.peek().get_type() == Node.node_type.END) {
                break;
            }
            
            current_node = stack.pop();
            current_node.toggle_traversed();
            current_node_list_size = current_node.get_critical_list_size();
            for(int i = 0; i < current_node_list_size; i++) {
                processing_node = current_node.get_critical_list_element();
                if(!processing_node.get_traversed() && !stack.contains(processing_node)) {
                    stack.push(processing_node);
                    processing_node.set_predecessor(current_node);
                    DFS_critical_nodes_added++;
                }
            }
        }
        
        if(stack.isEmpty()) {
            return false;
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
    
    private Node[] get_all_neighbors(Node node) {
        Node neighbors[] = new Node[NUM_NEIGHBORS];
        //set all initially to null
        for(int i = 0; i < NUM_NEIGHBORS; i++) {
            neighbors[i] = null;
        }
        int[] row_col_size = initialized_maze.get_row_col_size();
        
        //Western Neighbor
        if(node.get_col_index() - 1 >= 0) {
            if(initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1).get_type() != Node.node_type.WALL) {
                neighbors[WEST_INDEX] = initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1);
            }
        }
        
        //Northern Neighbor
        if(node.get_row_index() + 1 < row_col_size[0]) {
            if(initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index()).get_type() != Node.node_type.WALL) {
                neighbors[NORTH_INDEX] = initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index());
            }
        }
        
        //Eastern Neighbor
        if(node.get_col_index() + 1 < row_col_size[1]) {
            if(initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1).get_type() != Node.node_type.WALL) {
                neighbors[EAST_INDEX] = initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1);
            }
        }
        //Southern Neighbor
        if(node.get_row_index() - 1 >= 0) {
            if(initialized_maze.get_node_at(node.get_row_index() - 1, node.get_col_index()).get_type() != Node.node_type.WALL) {
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
    
    
    public void parse_critical_nodes() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialized_maze.get_starting_point());
        Node current_node;
    
        while(!stack.isEmpty()) {
            current_node = stack.pop();
            current_node.toggle_traversed();
            //System.out.println("Stack popped: " + current_node.get_type());
            Node[] current_node_neighbors = get_all_neighbors(current_node);
            int current_traversable_neighbors = count_traversable_neighbors(current_node_neighbors);
            //System.out.println("Popped neighbors: " + current_traversable_neighbors);
            
            switch(current_traversable_neighbors) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    if (current_node_neighbors[WEST_INDEX] != null && current_node_neighbors[EAST_INDEX] != null) {
                        break;
                    }
                    if (current_node_neighbors[NORTH_INDEX] != null && current_node_neighbors[SOUTH_INDEX] != null) {
                        break;
                    }
                    current_node.set_critical(true);
                    break;
                case 3:
                    current_node.set_critical(true);
                    break;
                case 4:
                    current_node.set_critical(true);
                    break;
                default:
                    break;
            }
            
            for(int i = 0; i < NUM_NEIGHBORS; i++) {
                if(current_node_neighbors[i] != null) {
                    if(!current_node_neighbors[i].get_traversed()) {
                        stack.push(current_node_neighbors[i]);
                    }
                }
            }
        }
        
        initialized_maze.reset_maze();
        link_critical_nodes();
    }
    
    private void link_critical_nodes() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialized_maze.get_starting_point());
        Node current_node;
        Node processing_node;
        int counter = 1;
        int[] row_col_size = initialized_maze.get_row_col_size();
        
        while(!stack.isEmpty()) {
            current_node = stack.pop();
            current_node.toggle_traversed();
            
            //Western direction
            while(current_node.get_col_index() - counter >= 0) {
                processing_node = initialized_maze.get_node_at(current_node.get_row_index(), current_node.get_col_index() - counter);
                if(processing_node.get_type() == Node.node_type.WALL) {
                    break;
                }
                if(processing_node.get_critical()) {
                    if(!processing_node.get_traversed()) {
                        stack.add(processing_node);
                        current_node.link(processing_node);
                    }
                    break;
                }
                counter++;
            }
            counter = 1;
            
            //Northern Direction
            while(current_node.get_row_index() + counter < row_col_size[0]) {
                processing_node = initialized_maze.get_node_at(current_node.get_row_index() + counter, current_node.get_col_index());
                if(processing_node.get_type() == Node.node_type.WALL) {
                    break;
                }
                if(processing_node.get_critical()) {
                    if(!processing_node.get_traversed()) {
                        stack.add(processing_node);
                        current_node.link(processing_node);
                    }
                    break;
                }
                counter++;
            }
            counter = 1;
            
            //Eastern Direction
            while(current_node.get_col_index() + counter < row_col_size[1]) {
                processing_node = initialized_maze.get_node_at(current_node.get_row_index(), current_node.get_col_index() + counter);
                if(processing_node.get_type() == Node.node_type.WALL) {
                    break;
                }
                if(processing_node.get_critical()) {
                    if(!processing_node.get_traversed()) {
                        stack.add(processing_node);
                        current_node.link(processing_node);
                    }
                    break;
                }
                counter++;
            }
            counter = 1;
            
            //Southern Direction
            while(current_node.get_row_index() - counter >= 0) {
                processing_node = initialized_maze.get_node_at(current_node.get_row_index() - counter, current_node.get_col_index());
                if(processing_node.get_type() == Node.node_type.WALL) {
                    break;
                }
                if(processing_node.get_critical()) {
                    if(!processing_node.get_traversed()) {
                        stack.add(processing_node);
                        current_node.link(processing_node);
                    }
                    break;
                }
                counter++;
            }
            counter = 1;
        }
        initialized_maze.reset_maze();
    }
    
    private int count_traversable_neighbors(Node neighbors[]) {
        int num_traversable_neighbors = 0;
        for(int i = 0; i < NUM_NEIGHBORS; i++) {
            if(neighbors[i] != null) {
                num_traversable_neighbors++;
            }
        }
        return num_traversable_neighbors;
    }
    
    public ArrayList<Node> get_solution_path(){
        return solution_path;
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
    
    public int get_BFS_critical_nodes_added() {
        return BFS_critical_nodes_added;
    }
    
    public int get_DFS_critical_nodes_added() {
        return DFS_critical_nodes_added;
    }
}
