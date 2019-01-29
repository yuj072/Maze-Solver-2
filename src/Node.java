
public class Node {
    
    enum node_type {
        WALL, PATH, START, END;
    }
    
    private node_type type;
    private boolean traversed;
    private int row_index;
    private int col_index;
    
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
        traversed = true;
    }
    
    public node_type get_type() {
        return type;
    }
}

