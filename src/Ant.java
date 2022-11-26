import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Ant {

    boolean isWild;
    int id;
    int distance_made = 0;

    int start_pos;
    ArrayList<Integer>visited_places = new ArrayList<>();
    int Lk_j;


    public Ant(boolean isWild, int id,int start_pos)
    {
        this.id = id;
        this.isWild = isWild;
        this.start_pos=start_pos;
    }

    public void setDistance_made(int dist)
    {
        this.distance_made = dist;
    }

    boolean getWildness()
    {
        return isWild;
    }


    public void start_travelling(Graph graph, int citiesAmount)
    {
        if(!this.isWild)
        {
//            ArrayList<Integer> avaliable_cities = fill_cities(citiesAmount);
//            avaliable_cities.remove(this.start_pos);
//            while(!avaliable_cities.isEmpty())
//            {
//                int city_to_visit = avaliable_cities.get
//                if(!visited_places.contains(city_to_visit))
                //add to visited
                //delete the visited city
            System.out.println("not wild");
//            }
        }
        else {
            // make random decisions

            make_random_travelling(graph, citiesAmount);
        }
    }

    public static ArrayList<Integer> set_ants(int citiesAmount)
    {
        Random random = new Random();
        ArrayList<Integer> placed = new ArrayList<>();
        ArrayList<Integer> cities = fill_cities(citiesAmount);
        while(!cities.isEmpty())
        {
            int rand_city = cities.get(random.nextInt(cities.size()));
            placed.add(rand_city);
            cities.remove(cities.indexOf(rand_city));
        }
        return placed;

    }



    static ArrayList<Integer> fill_cities(int citiesAmount)
    {
        ArrayList<Integer> cities = new ArrayList<>();
        for(int i =0; i< citiesAmount; i++)
        {
            cities.add(i+citiesAmount^2);
        }
        return cities;
    }

    void make_random_travelling(Graph graph, int citiesAmount)
    {
        ArrayList<Integer> cities = fill_cities(citiesAmount);
        int counter = 0;
        ArrayList<Integer>route = new ArrayList<>();
        while (!cities.isEmpty())
        {
            Random random = new Random();
            int get_city_ind= random.nextInt(cities.size());
            int get_city = cities.get(get_city_ind);
            route.add(get_city);
            int previously_visited;
            if(visited_places.size()==0)
            {
                previously_visited = this.start_pos;
                counter--;
            }
            else
                previously_visited = visited_places.get(counter);
            distance_made+=graph.travelling_distance(graph,previously_visited,get_city);
            visited_places.add(get_city);
            cities.remove(get_city_ind);
            counter++;

        }
        System.out.println("ROUTE OF THIS ANT: "+ route);
        System.out.println("DISTANCE: "+distance_made);

    }

    public static ArrayList<Integer> make_wilds(int antAmount, int wildAntAmount)
    {
        ArrayList<Integer> wilds = new ArrayList<>();
        Random random = new Random();

        for(int i =0; i<wildAntAmount;i++)
        {
        int position = random.nextInt(antAmount);
        if(wilds.isEmpty()||!wilds.contains(position))
            wilds.add(position);
        else
            i--;
        }
        return wilds;
    }

    public void update_pheromone(double[][]pheromone)
    {
        double p =0.4;
        for(int i =0; i< pheromone.length;i++)
        {
            for(int j =0; j<pheromone.length;j++)
            {
                pheromone[i][j] *=p;
//                pheromone[i][j]+=
            }
        }
    }

    public double calculateDeltaPheromone(int from, int to, int citiesCount, int Lmin, int Lk)
    {
        double num = 0.0;
        for(int i =0; i<citiesCount;i++)
        {
            if(visited_places.contains(to) && visited_places.contains(from))
                num = num+(double) Lmin / (double)Lk_j;
        }
        return num;
    }

    public static int greedyFindMinLengthIndex(Ant ant,Graph gr, int startingIndex)
    {
        int length =gr.travelling_distance(gr, startingIndex,0);
        int minLengthIndex =0;
        for(int i =0; i<gr.graph_size();i++)
        {
            int dist = gr.travelling_distance(gr,startingIndex,i);
            if(!ant.visited_places.contains(i)&&dist<minLengthIndex) {
                length = dist;
                minLengthIndex = i;
            }

        }
        return minLengthIndex;
    }
}
