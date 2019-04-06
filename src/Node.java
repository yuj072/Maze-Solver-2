import java.util.ArrayList;

public class Node {
    
    /* Definition of the types a Node can be.
    *  Wall - A space that cannot be crossed. Represented by the character '#'.
    *  Path - A space that can be crossed. Represented by the character ' ' (space).
    *  Start - The starting point the maze. Represented by the character '!'.
    *  End - The exit to the maze. Represented by the character '$'.
    *  Solution - The solution path of the maze. Represented by the character 'O'.
     */
    enum node_type {
        WALL, PATH, START, END, SOLUTION;
    }
    
    //Variables for all Node instances.
    private node_type type;
    private boolean traversed;
    private int row_index;
    private int col_index;
    private Node predecessor;
    private boolean critical = false;
    private ArrayList<Node> critical_list = new ArrayList<>();
    
    
    /* Constructor for the Node class. It takes in a char argument and depending on
    *  the inputted value, it creates a Node of a certain class.
    *  @Params: char input_char - the character value the Node constructor needs to
    *           know what type of Node to make
    *  @Return: None
    *  @Throws: IllegalArgumentException - when input_char is not a character the Node
    *           constructor recognizes.
     */
    public Node(char input_char) throws IllegalArgumentException {
        this.traversed = false;
            
        switch(input_char) {
            case '#':
                set_node_type(node_type.WALL);
                traversed = true;
                break;
            case ' ':
                set_node_type(node_type.PATH);
                break;
            case '!':
                set_node_type(node_type.START);
                set_critical(true);
                break;
            case '$':
                set_node_type(node_type.END);
                set_critical(true);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
    
    /* This method toggles(flips) the traversed value for this node.
    *  @Params: None.
    *  @Returns: None.
     */
    public void toggle_traversed() {
        if(this.traversed == true && !(this.type == node_type.WALL)) {
            traversed = false;
            return;
        }
        traversed = true;
    }
    
    /* This method toggles(flips) the solution value for this node.
    *  @Params: None.
    *  @Returns: None.
     */
    public void toggle_solution() {
        if(this.type == node_type.SOLUTION) {
            this.type = node_type.PATH;
            return;
        }
        this.type = node_type.SOLUTION;
    }
    
    /* This is the definition of the toString method for Nodes. The String this method returns follows
    *  what the Node constructor recognizes closely. The only difference is when a Node is of type critical,
    *  or solution.
    *  @Params: None.
    *  @Returns: The string representation of this Node.
     */
    public String to_string() {
        String output_char = "";
        node_type current_type = this.get_type();
        if(!(current_type == node_type.END) && !(this.type == node_type.START) && !(this.type == node_type.SOLUTION)) {
            if(critical) {
                return "%";
            }
        }
        switch(current_type) {
            case WALL:
                output_char = "#";
                break;
            case PATH:
                output_char = " ";
                break;
            case START:
                output_char = "!";
                break;
            case END:
                output_char = "$";
                break;
            case SOLUTION:
                output_char = "O";
                break;
        }
        
        return output_char;
    }
    
    /* Getters and Setters */
    
    public void set_critical(boolean new_critical) {
        critical = new_critical;
    }
    
    public boolean get_critical() {
        return critical;
    }
    
    public void link(Node node) {
        this.critical_list.add(node);
    }
    
    public int get_critical_list_size() {
        return critical_list.size();
    }
    
    public Node get_critical_list_element() {
        return critical_list.remove(0);
    }
    
    private void set_node_type(node_type new_type) {
        type = new_type;
    }
    
    public void set_col_index(int new_index) {
        col_index = new_index;
    }
    
    public void set_row_index(int new_index) {
        row_index = new_index;
    }
    
    public int get_col_index() {
        return col_index;
    }
    
    public int get_row_index() {
        return row_index;
    }
    
    public boolean get_traversed() {
        return traversed;
    }
    
    public node_type get_type() {
        return type;
    }
    
    public Node get_predecessor() {
        return predecessor;
    }
    
    public void set_predecessor(Node new_predecessor) {
        predecessor = new_predecessor;
    }
}

