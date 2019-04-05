import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            MazeSolver2 solver = new MazeSolver2(args[0]);
            System.out.println("Unsolved Maze: ");
            System.out.println(solver.get_initialized_maze().print_unsolved_maze());
            System.out.println("DFS Solved Maze: ");
            solver.DFS_solve_maze();
            System.out.println(solver.get_initialized_maze().print_solved_maze());
            System.out.println("Solved using " + solver.get_DFS_nodes_added() + " steps");
            
            solver = new MazeSolver2(args[0]);
            solver.BFS_solve_maze();
            System.out.println();
            System.out.println("BFS Solved Maze: ");
            System.out.println(solver.get_initialized_maze().print_solved_maze());
            System.out.println("Solved using " + solver.get_BFS_nodes_added() + " steps");
            System.out.println();
    
            solver = new MazeSolver2(args[0]);
            System.out.println("Unsolved Maze with critical nodes:");
            solver.parse_critical_nodes();
            solver.get_initialized_maze().update_maze(false);
            System.out.println(solver.get_initialized_maze().print_unsolved_maze());
            solver.BFS_critical_solve_maze();
            System.out.println("BFS Critical Solved Maze: ");
            System.out.println(solver.get_initialized_maze().print_solved_maze());
            System.out.println("Solved using " + solver.get_BFS_critical_nodes_added() + " steps");
            System.out.println();
    
            solver = new MazeSolver2(args[0]);
            solver.parse_critical_nodes();
            solver.DFS_critical_solve_maze();
            System.out.println("DFS Critical Solved Maze: ");
            System.out.println(solver.get_initialized_maze().print_solved_maze());
            System.out.println("Solved using " + solver.get_DFS_critical_nodes_added() + " steps");
    
    
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found: " + args[0]);
        }
        
        
    }
}
