
public class Node {
    
    enum node_type {
        WALL, PATH, START, END, SOLUTION;
    }
    
    private node_type type;
    private boolean traversed;
    private int row_index;
    private int col_index;
    private Node predecessor;
    
    private void set_node_type(node_type new_type) {
            type = new_type;
        }
        
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
                break;
            case '$':
                set_node_type(node_type.END);
                break;
            default:
                throw new IllegalArgumentException();
        }
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
    
    public void toggle_traversed() {
        if(this.traversed == true) {
            traversed = false;
        }
        traversed = true;
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
    
    public void toggle_solution() {
        if(this.type == node_type.SOLUTION) {
            this.type = node_type.PATH;
        }
        this.type = node_type.SOLUTION;
    }
    
    public String to_string() {
        String output_char = "";
        node_type current_type = this.get_type();
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
}

