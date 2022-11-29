import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ant {
    int alpha = 4;
    int beta = 2;

    double rho = 0.3;

    boolean isWild;
    int id;
    int distance_made = 0;

    City start_pos;
    ArrayList<City>visited_places = new ArrayList<>();

    static double[][]pheromone;


    public Ant(boolean isWild, int id,City start_pos)
    {
        this.id = id;
        this.isWild = isWild;
        this.start_pos=start_pos;
    }

    public static void setPheromone(double[][] pheromone)
    {
        Ant.pheromone = pheromone;
    }

    public void setDistance_made(int dist)
    {
        this.distance_made = dist;
    }

    boolean getWildness()
    {
        return isWild;
    }


    public void start_travelling(Graph graph, ArrayList<City>cities)
    {
        if(!this.isWild)
        {
            make_not_random_travel(graph,cities);
        }
        else {
            // make random decisions

            make_random_travelling(graph, cities);
        }

//        System.out.print("ROUTE OF THIS ANT: ");
//        for(City c: visited_places)
//            System.out.print(c.id_to_string()+" ");
//        System.out.println();
//        System.out.println("DISTANCE: "+distance_made);
//        if(!this.isWild)
//        {
//            for(double[]c:pheromone)
//                System.out.println(Arrays.toString(c));
//        }
    }

    public static ArrayList<City> set_ants(ArrayList<City>cities)
    {
        Random random = new Random();
        ArrayList<City> placed = new ArrayList<>();
        while(!cities.isEmpty())
        {
            City rand_city = City.get_rand_city(cities);
            placed.add(rand_city);
            cities.remove(cities.indexOf(rand_city));
        }
        return placed;

    }



    static ArrayList<City> fill_cities(int citiesAmount)
    {
        ArrayList<City> cities = new ArrayList<>();
        for(int i =0; i< citiesAmount; i++)
        {
            cities.add(new City(i,i+citiesAmount^3));
        }
        return cities;
    }

    void make_random_travelling(Graph graph, ArrayList<City>cities)
    {
        int counter = 0;
        visited_places.add(this.start_pos);
        cities.remove(this.start_pos);
        while (!cities.isEmpty())
        {
            Random random = new Random();
            int get_city_ind= random.nextInt(cities.size());
            City get_city = cities.get(get_city_ind);

            City previously_visited;
            if(visited_places.size()==0)
            {
                previously_visited = this.start_pos;
                counter--;
            }
            else
                previously_visited = visited_places.get(counter);
            distance_made+=graph.travelling_distance(graph,previously_visited.getId(),get_city.getId());
            visited_places.add(get_city);
            cities.remove(get_city_ind);
            counter++;

        }
        visited_places.add(this.start_pos);
        distance_made+=graph.travelling_distance(graph,visited_places.get(visited_places.size()-2).getId(),this.start_pos.getId());


    }

    void make_not_random_travel(Graph gr, ArrayList<City> cities)
    {
        visited_places.add(this.start_pos);
        cities.remove(this.start_pos);
        City next_city = null;
        for(int i =0; i<cities.size();i++)
        {
            City start_city = visited_places.get(visited_places.size()-1);
            ArrayList<City> available_cities = get_available_cities(cities);
            next_city = find_min_probability(available_cities, gr,start_city);
            distance_made+=gr.travelling_distance(gr,start_city.getId(),next_city.getId());
            visited_places.add(next_city);
        }
        assert next_city != null;
        distance_made+= gr.travelling_distance(gr,next_city.getId(),this.start_pos.getId());
        visited_places.add(this.start_pos);
        update_pheromones();


    }

    void update_pheromones( )
    {
        for(int i =1; i < visited_places.size();i++)
        {
            int from_id = visited_places.get(i-1).getId();
            int to_id = visited_places.get(i).getId();
            double new_pho = pheromone[from_id][to_id];
            if(pheromone[from_id][to_id]==1)
                new_pho = (1-rho)+(1.0/distance_made);
            else
                new_pho+=(1.0/distance_made);

            pheromone[from_id][to_id] = new_pho;
            pheromone[to_id][from_id] = new_pho;

        }

    }

    ArrayList<City> get_available_cities(ArrayList<City> cities)
    {
        ArrayList<City> availables = new ArrayList<>();
        for(City c : cities)
        {
            if(!visited_places.contains(c))
                availables.add(c);
        }
        return availables;
    }


    City find_min_probability(ArrayList<City>cities_left_to_visit, Graph gr, City start_city)
    {
        double min_prob = 0.;
        double prob = 0;
        int[]distances = new int[cities_left_to_visit.size()];
        double[] down_values = new double[cities_left_to_visit.size()];
        int from_id = start_city.getId();
        for(int i =0;i < cities_left_to_visit.size();i++)
        {
            int to_id = cities_left_to_visit.get(i).getId();
            distances[i] = gr.travelling_distance(gr,start_city.getId(),cities_left_to_visit.get(i).getId());
            down_values[i] = Math.pow(pheromone[from_id][to_id],alpha)*Math.pow(1.0/distances[i],beta);

        }
        double sum_of_down_values = 0;
        for (double down_value : down_values) {
            sum_of_down_values += down_value;
        }
//        System.out.println("DOWN: "+Double.toString(sum_of_down_values));
        City min_prob_city = cities_left_to_visit.get(0);
//        System.out.println("PROBS: ");
        for(City c : cities_left_to_visit)
        {
            int to_id = c.getId();


            double upper = Math.pow(pheromone[from_id][to_id],alpha)*Math.pow(1.0/distances[cities_left_to_visit.indexOf(c)],beta);
//            System.out.println("UPPER: "+upper);
            prob = (upper/sum_of_down_values);
//            System.out.print(prob);
//            System.out.print("   ");
            if(prob>=min_prob)
            {
                min_prob = prob;
                min_prob_city = c;
            }
        }
//        System.out.println();

        return min_prob_city;
    }





}
