package com.labs.Additionals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ant {
    int alpha = 4;
    int beta = 2;

    boolean isWild;
    int id;
    int distance_made = 0;

    City start_pos;
    ArrayList<City>visited_places = new ArrayList<>();

    static double[][] pheromone;


    public Ant(boolean isWild, int id,City start_pos)
    {
        this.id = id;
        this.isWild = isWild;
        this.start_pos=start_pos;
    }

    public double[][] start_travelling(Graph graph, ArrayList<City>cities, int for_test_not_zero, double[][] pheromone_new)
    {
        double[][] pheromone_old = copy_matrix(pheromone);
        double [][] new_updated = copy_matrix(pheromone_new);
        double [][] old_shit = copy_matrix(pheromone_old);
        if(!this.isWild)
        {
            new_updated = make_not_random_travel(graph,cities,pheromone_new,pheromone_old);
        }

        else {
            // make random decisions

            make_random_travelling(graph, cities);
        }
        pheromone = old_shit.clone();

        if(for_test_not_zero == 0) {
            System.out.print("ROUTE OF THIS ANT: ");
            for (City c : visited_places)
                System.out.print(c.getId() + " ");
            System.out.println();
            System.out.println("DISTANCE: " + distance_made);
            if (!this.isWild) {
                for (double[] c : pheromone)
                    System.out.println(Arrays.toString(c));
            }
        }
        return new_updated;
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

    double[][] make_not_random_travel(Graph gr, ArrayList<City> cities, double[][] previously_updated, double[][]old_pheromone)
    {
        visited_places.add(this.start_pos);
        cities.remove(this.start_pos);
        City next_city = null;
        for(int i =0; i<cities.size();i++)
        {
            City start_city = visited_places.get(visited_places.size()-1);
            ArrayList<City> available_cities = get_available_cities(cities);
            next_city = find_min_probability(available_cities, gr,start_city, old_pheromone);
            distance_made+=gr.travelling_distance(gr,start_city.getId(),next_city.getId());
            visited_places.add(next_city);
        }
        assert next_city != null;
        distance_made+= gr.travelling_distance(gr,next_city.getId(),this.start_pos.getId());
        visited_places.add(this.start_pos);
        return update_pheromones(previously_updated);


    }

    double[][] update_pheromones(double[][] previously_updated)
    {
        for(int i =1; i < visited_places.size();i++)
        {
            int from_id = visited_places.get(i-1).getId();
            int to_id = visited_places.get(i).getId();
            double new_pho =(1.0/distance_made);

            previously_updated[from_id][to_id] += new_pho;
            previously_updated[to_id][from_id] += new_pho;
            previously_updated[i-1][i-1] = 1;

        }

        return previously_updated;
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


    City find_min_probability(ArrayList<City>cities_left_to_visit, Graph gr, City start_city, double[][] old_pheromone)
    {
        double min_prob = 0;
        double prob = 0;
        int[]distances = new int[cities_left_to_visit.size()];
        double[] down_values = new double[cities_left_to_visit.size()];
        int from_id = start_city.getId();
        for(int i =0;i < cities_left_to_visit.size();i++)
        {
            int to_id = cities_left_to_visit.get(i).getId();
            distances[i] = gr.travelling_distance(gr,start_city.getId(),cities_left_to_visit.get(i).getId());
            down_values[i] = Math.pow(old_pheromone[from_id][to_id],alpha)*Math.pow(1.0/distances[i],beta);

        }
        double sum_of_down_values = 0;
        for (double down_value : down_values) {
            sum_of_down_values += down_value;
        }

        City min_prob_city = cities_left_to_visit.get(0);
        ArrayList<Double> PROBS = new ArrayList<>();
        for(City c : cities_left_to_visit)
        {
            int to_id = c.getId();


            double upper = Math.pow(old_pheromone[from_id][to_id],alpha)*Math.pow(1.0/distances[cities_left_to_visit.indexOf(c)],beta);

            prob = (upper/sum_of_down_values);
            PROBS.add(prob);
        }
        ArrayList<Double> sums = new ArrayList<>();
        for(int k =0; k<PROBS.size();k++)
        {
            sums.add(sum_till_end(PROBS,k));
        }
        sums.add(0.);


        Random random = new Random();
        double val = random.nextDouble();
        int right_sum_pos = 0;
        for(int l =0; l<sums.size()-1;l++)
        {
            if(val<=sums.get(l) && val>sums.get(l+1))
            {
                right_sum_pos = l;
            }

        }
        min_prob_city = cities_left_to_visit.get(right_sum_pos);


        return min_prob_city;
    }

    double sum_till_end(ArrayList<Double> probs, int pos)
    {
        double answer =0.;
        for(int i = pos; i<probs.size();i++)
        {
            answer+=probs.get(i);
        }
        return answer;
    }


    static double[][]copy_matrix(double[][] old_matr)
    {
        double[][] new_matr = new double[old_matr.length][old_matr.length];
        for(int i =0; i< old_matr.length;i++)
        {
            for(int j =0; j<old_matr.length;j++)
            {
                new_matr[i][j] = old_matr[i][j];
            }

        }
        return new_matr;
    }


}
