import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Graph {
    private int V;
    private ArrayList<Integer> adj[]; //Adjacency Lists


    public int graph_size() {
        int size_gr=0;

        for (int i = 0; i < V; i++) {
            if (!adj[i].isEmpty())
                size_gr=i;
            else
                i=V;
        }
        return size_gr;
    }


    public ArrayList<Integer> get_adj(int V)
    {
        return adj[V];
    }


    public void print_graph()
    {
        for(int i =0; i< V;i++) {
            if(!adj[i].isEmpty())
                System.out.print(adj[i]);
            else
                i = V;
        }
        System.out.println();
    }

    // Constructor
    public Graph(int v) {
        V = v;
        adj = new ArrayList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new ArrayList<>();
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
    }
    void changeEdge(int v, int index, int new_value) {
        adj[v].set(index,new_value);
    }

    void make_distances(Graph gr)
    {
        for(int i =0; i< V;i++)
        {
            for(int j =0; j<V;j++) {
                gr.addEdge(i,0);
            }
            }

        Random random_dist = new Random();
        for(int i =0; i< V;i++)
        {
            for(int j =0; j<V;j++) {
                if (gr.get_adj(i).get(j) == 0) {
                    if (i == j)
                        gr.changeEdge(i, j,0);
                    else {
                        int rand_dist = random_dist.nextInt(1, 40);
                        gr.changeEdge(i,j,rand_dist);
                        gr.changeEdge(j,i, rand_dist);
                    }

                }
            }
        }


    }

    public int travelling_distance(Graph graph, int place_one , int place_two)
    {
        return graph.get_adj(place_one).get(place_two);
    }







}
