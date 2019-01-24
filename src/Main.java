import javax.naming.directory.NoSuchAttributeException;

enum node_type {
    WALL, PATH, START, END;
}

class Node {
    private node_type type;
    private boolean traversed;
    
    private void set_node_type(node_type new_type) {
        type = new_type;
    }
    
    public Node(char input_char) throws NoSuchAttributeException {
        traversed = false;
        
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
                throw new NoSuchAttributeException();
        }//end switch
    }//end Node constructor
}//end Node class definition


public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
