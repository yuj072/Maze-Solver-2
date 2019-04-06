import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver2 {
    
    //Variables for all instances of MazeSolver2
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
    
    /* The constructor for an instance of MazeSolver2. This takes in the file name of the
    *  .txt file the user passes in as a command line argument and then creates an internal
    *  instance of MazeCreator. Then it calls the MazeCreator's read_and_parse_maze method.
    *  @Params: String file_name - the name of the .txt file containing the text version of the maze.
    *  @Returns: None.
    *
     */
    public MazeSolver2(String file_name) throws FileNotFoundException {
        initialized_maze = new MazeCreator(file_name);
        initialized_maze.read_and_parse_maze();
    }
    
    /* This method solves the maze normally using Breath First Search (BFS). It uses a LinkedList
    *  as its queue and adds the starting point of the maze. Then for each of the element inside of
    *  the queue it looks at all the non-traversed neighbors and then adds them into the queue also.
    *  This process stops when the end is found or when the queue runs out of Nodes.
    *  @Params: None.
    *  @Return: boolean - true of an exit is found, false otherwise
     */
    public boolean BFS_solve_maze() {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(initialized_maze.get_starting_point());
        BFS_nodes_added = 1;
        Node current_node;
        boolean path_marked;
        
        while(!queue.isEmpty()) {
            if(queue.peek().get_type() == Node.node_type.END) {
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
            return false;
        }
        
        path_marked = populate_solution_path(queue.peek());
        initialized_maze.mark_solution_path(solution_path);
        return path_marked;
    }
    
    /* The critical version of the BFS solve maze. This method is very similar to BFS_solve_maze
    *  except it uses the critical graph created in the parse_critical_nodes method.
    *  @Params: None.
    *  @Returns: boolean - true if an exit is found, false otherwise.
    *
     */
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
            return false;
        }
    
        path_marked = populate_solution_path(queue.peek());
        initialized_maze.mark_solution_path(solution_path);
        return path_marked;
    }
    
    /* This method solves the maze normally using Depth First Search (DFS). This method is very
     * similar to BFS_solve_maze except it uses a stack instead of queue to obtain the
     * First-in-Last-Out property of DFS.
     * @Param: None.
     * @Return: boolean - true if an exit has been found, false otherwise.
     */
    public boolean DFS_solve_maze() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialized_maze.get_starting_point());
        DFS_nodes_added = 1;
        Node current_node;
        boolean path_marked;
        
        while(!stack.isEmpty()) {
            if(stack.peek().get_type() == Node.node_type.END) {
                break;
            }
            
            current_node = stack.pop();
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
            return false;
        }
        
        path_marked = populate_solution_path(stack.peek());
        initialized_maze.mark_solution_path(solution_path);
        return path_marked;
    }
    
    /* The critical version of DFS_solve_maze. This is very similar to DFS_solve_maze except
    *  it uses the critical graph generated by parse_critical_nodes method.
    *  @Params: None.
    *  @Returns: boolean - true if an exit has been found, false otherwise.
     */
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
    
    /* This method obtains all the nodes adjacent to the parameter Node and it returns all
     * the adjacent Nodes with their traversed boolean equal to false. In other words, it obtains
     * all the neighbors that has not been explored yet.
     * @Params: Node node - the Node to obtain explorable neighbors from.
     * @Return: Node[] - An array of fixed sized 4 containing the Western, Northern, Eastern, Southern
     *                   neighbor of the Node passed in, in that order. If any of those neighbors is
     *                   not traversable, the  respective index will be set to null.
     */
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
    
    /* Helper method to aid in finding critical nodes. This method is very similar to get_traversable_neighbors
     * except it returns all neighbors regardless of the value of their traversed boolean. Any node that is not
     * a wall will be included in its return array.
     * @Params: Node node - The node to obtain all neighbors from
     * @Returns: Node[] - An array of fixed sized 4 containing the Western, Northern, Eastern, Southern
     *                   neighbor of the Node passed in, in that order. If any of those neighbors is
     *                   a wall, the respective index will be set to null.
     */
    private Node[] get_all_neighbors(Node node) {
        Node neighbors[] = new Node[NUM_NEIGHBORS];
        //set all initially to null
        for(int i = 0; i < NUM_NEIGHBORS; i++) {
            neighbors[i] = null;
        }
        int[] row_col_size = initialized_maze.get_row_col_size();
        
        //Western Neighbor
        if(node.get_col_index() - 1 >= 0) {
            if(initialized_maze.get_node_at(node.get_row_index(),
                                            node.get_col_index() - 1).get_type() != Node.node_type.WALL) {
                neighbors[WEST_INDEX] = initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() - 1);
            }
        }
        
        //Northern Neighbor
        if(node.get_row_index() + 1 < row_col_size[0]) {
            if(initialized_maze.get_node_at(node.get_row_index() + 1,
                                            node.get_col_index()).get_type() != Node.node_type.WALL) {
                neighbors[NORTH_INDEX] = initialized_maze.get_node_at(node.get_row_index() + 1, node.get_col_index());
            }
        }
        
        //Eastern Neighbor
        if(node.get_col_index() + 1 < row_col_size[1]) {
            if(initialized_maze.get_node_at(node.get_row_index(),
                                            node.get_col_index() + 1).get_type() != Node.node_type.WALL) {
                neighbors[EAST_INDEX] = initialized_maze.get_node_at(node.get_row_index(), node.get_col_index() + 1);
            }
        }
        //Southern Neighbor
        if(node.get_row_index() - 1 >= 0) {
            if(initialized_maze.get_node_at(node.get_row_index() - 1,
                                            node.get_col_index()).get_type() != Node.node_type.WALL) {
                neighbors[SOUTH_INDEX] = initialized_maze.get_node_at(node.get_row_index() - 1, node.get_col_index());
            }
        }
        
        return neighbors;
    }
    
    /* This method helps obtain the obtain the solution path via the chain of predecessor variables
     * set in any of the search. It starts from the end, and looks at the predecessor field of the Nodes
     * until it finds the start Node.
     * @Params: Node end_node - the exit of a maze
     * @Returns: boolean - true if the chain of predecessors leads back to the start, false otherwise
     */
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
    
    /* This method parses all the modes in a maze and marks those that are deemed critical.
     * Criteria for a critical Node:
     *    -4 adjacent explorable neighbors (this is a fork in a path)
     *    -3 adjacent explorable neighbors (this is a fork in a path)
     *    -2 adjacent explorable neighbors that are NOT in a straight line. (this is a change in direction)
     *
     * @Params: None.
     * @Return: None.
     */
    public void parse_critical_nodes() {
        Stack<Node> stack = new Stack<>();
        stack.push(initialized_maze.get_starting_point());
        Node current_node;
    
        while(!stack.isEmpty()) {
            current_node = stack.pop();
            current_node.toggle_traversed();
            Node[] current_node_neighbors = get_all_neighbors(current_node);
            int current_traversable_neighbors = count_traversable_neighbors(current_node_neighbors);
            
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
    
    /* This method is the helper method to parse_critical_nodes and its job is to create
     * the graphical representation of the critical nodes by linking all of them. This creates
     * a directed graph with the start of the maze as the root.
     * @Param: None.
     * @Return: None.
     */
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
                processing_node = initialized_maze.get_node_at(current_node.get_row_index(),
                                                               current_node.get_col_index() - counter);
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
                processing_node = initialized_maze.get_node_at(current_node.get_row_index() + counter,
                                                               current_node.get_col_index());
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
                processing_node = initialized_maze.get_node_at(current_node.get_row_index(),
                                                               current_node.get_col_index() + counter);
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
                processing_node = initialized_maze.get_node_at(current_node.get_row_index() - counter,
                                                               current_node.get_col_index());
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
    
    /* This is a helper method to parse_critical_nodes to count the number to non-wall neighbors.
     * @Params Node neighbors[] - an Array of size 4 containing all the neighbors to count
     * @Returns: int - all the non-null elements inside of neighbors[];
     */
    private int count_traversable_neighbors(Node neighbors[]) {
        int num_traversable_neighbors = 0;
        for(int i = 0; i < NUM_NEIGHBORS; i++) {
            if(neighbors[i] != null) {
                num_traversable_neighbors++;
            }
        }
        return num_traversable_neighbors;
    }
    
    /* Getters and Setters */
    
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
