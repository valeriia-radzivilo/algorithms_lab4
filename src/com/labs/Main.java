package com.labs;

import com.labs.Additionals.Graph;
import com.labs.Additionals.main_tsl;
import com.labs.Additionals.print_info;

public class Main {
    public static void main(String[] args) {
        int antAmount = 45,
            wildAntAmount = 10,
            citiesAmount = 250,
            alpha = 4,
            beta = 2;
        double rho = 0.3;

        Graph gr = new Graph(citiesAmount);
        gr.make_distances(gr);
        print_info.print_task(antAmount, wildAntAmount, citiesAmount, alpha, beta, rho);

            int this_min = main_tsl.start_program(-1, gr, antAmount, wildAntAmount, citiesAmount);


        System.out.println("\nMinimum from all iterations: "+ this_min);
        Graph.greedyLength(gr,citiesAmount);



    }



}