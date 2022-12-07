package com.labs.Additionals;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private int V;
    private ArrayList<Integer> adj[]; //Adjacency Lists

    public ArrayList<Integer> get_adj(int V)
    {
        return adj[V];
    }

    public void setAdj(int v, ArrayList<Integer>arr)
    {
        adj[v] = new ArrayList<Integer>(arr);
    }


    public void print_graph()
    {
        System.out.println(V);
        for(int i =0; i< V;i++) {
            if(!adj[i].isEmpty())
                for(int j : get_adj(i))
                {
                    System.out.print(j+"   ");
                }
            else
                i = V;
            System.out.println();
        }

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

    public void make_distances(Graph gr)
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
                        int rand_dist = random_dist.nextInt(40)+1;
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


    public static Graph ready_graph(int[][]distances)
    {
        Graph gr = new Graph(distances.length);


        for(int i = 0; i< distances.length;i++)
        {
            gr.setAdj(i,int_arr_to_list(distances[i]));
        }

        return gr;

    }

    static ArrayList<Integer> int_arr_to_list(int[] arr)
    {
        ArrayList<Integer>answer = new ArrayList<>();
        for (int j : arr) {
            answer.add(j);
        }
    return answer;
    }

    int find_min_in_adj(int v, ArrayList<Integer>visited)
    {
        ArrayList<Integer> ar = new ArrayList<>(this.get_adj(v));
        int min = ar.size()*1000000;
        int pos = 0;
        for(int i=0;i<ar.size();i++)
            if(ar.get(i)<min && i!=0 && !visited.contains(i))
            {
                min = ar.get(i);
                pos = i;
            }

        return pos;
    }


    public static void greedyLength(Graph graph, int citiesCount)
    {
        int min_dist =0;

        ArrayList<Integer>visited = new ArrayList<>();
        visited.add(0);
        for(int k =0; k<citiesCount-1;k++) {
            int val = graph.find_min_in_adj(visited.get(visited.size() - 1),visited);
            visited.add(val);
            min_dist+=graph.travelling_distance(graph,visited.get(visited.size()-2),visited.get(visited.size()-1));

        }
        visited.add(0);
        min_dist+=graph.travelling_distance(graph,visited.get(visited.size()-2),visited.get(visited.size()-1));

//        System.out.println("Greedy path: "+ visited);
        System.out.println("Greedy Lmin: "+ min_dist);

    }


}
