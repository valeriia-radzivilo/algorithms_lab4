package com.labs.Additionals;

import java.util.ArrayList;
import java.util.Collections;

public class main_tsl {

    public static int start_program(int start_pos_not_wild,Graph gr,int antAmount, int wildAntAmount, int citiesAmount) {
        // set pheromone
        double rho = 0.3;
        double[][] pheromone = new double[citiesAmount][citiesAmount];
        for (int i = 0; i < citiesAmount; i++) {

            for (int j = 0; j < citiesAmount; j++)
                pheromone[i][j] = 1-rho;

            pheromone[i][i] = 1;
        }

        int Lmin = citiesAmount * 40; // max possible if all have distance of 40 - max
        int Lpr = citiesAmount*40;
        ArrayList<Integer>minis = new ArrayList<>();
        ArrayList<City> cities = Ant.fill_cities(citiesAmount);
        double[][] updated_pheromones = pheromone.clone();
        for (int iterator = 1; iterator <= 1000; iterator++) {
            // set pheromones for this colony
            Ant.pheromone = Ant.copy_matrix(updated_pheromones);
            ArrayList<City> starting_positions = Ant.set_ants(new ArrayList<>(cities));

            // запускаємо диких, хай побігають
            for (int i = 0; i < wildAntAmount; i++) {
                Ant ant = new Ant(true, i, starting_positions.get(i));
                ant.start_travelling(gr, new ArrayList<>(cities), start_pos_not_wild - 1,updated_pheromones);
                if (ant.distance_made < Lmin) Lmin = ant.distance_made;
            }



            // let not wild out

            for (int i = wildAntAmount; i < antAmount; i++) {
                City st_pos = starting_positions.get(i);
                if (start_pos_not_wild != -1 && i == wildAntAmount)
                    st_pos = cities.get(start_pos_not_wild);
                if (start_pos_not_wild != -1 && i == wildAntAmount + 1)
                    st_pos = cities.get(start_pos_not_wild + 2);

                Ant ant = new Ant(false, i, st_pos);

                updated_pheromones = ant.start_travelling(gr, new ArrayList<>(cities), start_pos_not_wild - 1, updated_pheromones);

                if (ant.distance_made < Lmin)
                {
                    Lmin = ant.distance_made;
                }

            }
            minis.add(Lmin);
            if (iterator % 20 == 0)
            {
                System.out.println("Iteration №"+iterator+ "\nLpr from these ants: " + Lmin);
                System.out.println("Min distance: "+Collections.min(minis));
            }


        }
        return Collections.min(minis);
    }


}
