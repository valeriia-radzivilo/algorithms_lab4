import java.util.ArrayList;

public class main_tsl {

    public static void start_program(int start_pos_not_wild,Graph gr,int antAmount, int wildAntAmount, int citiesAmount, int iterator) {


        int Lmin = citiesAmount*40; // max possible if all have distance of 40 - max

        ArrayList<City> cities = Ant.fill_cities(citiesAmount);
//        System.out.println("CITIES: ");
//        for (City c : cities)
//            System.out.println("ID: " + Integer.toString(c.getId()) + "   Name: " + Integer.toString(c.getValue()));


        ArrayList<City> starting_positions = Ant.set_ants(new ArrayList<>(cities));

//        if(wildAntAmount>0) System.out.println("SET WILDS");

        // запускаємо диких, хай побігають
        for (int i = 0; i < wildAntAmount; i++) {
            Ant ant = new Ant(true, i, starting_positions.get(i));
            ant.start_travelling(gr, new ArrayList<>(cities));
            if(ant.distance_made<Lmin) Lmin = ant.distance_made;
        }

        // set pheromone
        double[][] pheromone = new double[citiesAmount][citiesAmount];
        for (int i = 0; i < citiesAmount; i++)
            for (int j = 0; j < citiesAmount; j++)
                if (i == j)
                    pheromone[i][j] = 0;
                else
                    pheromone[i][j] = 1;

        Ant.setPheromone(pheromone);
//        System.out.println("SET NON-WILDS");

        // let not wild out
        for (int i = wildAntAmount; i < antAmount; i++) {
            City st_pos = starting_positions.get(i);
            if(start_pos_not_wild!=-1 && i==wildAntAmount)
                st_pos = cities.get(start_pos_not_wild);
            if(start_pos_not_wild!=-1 && i==wildAntAmount+1)
                st_pos = cities.get(start_pos_not_wild+2);

            Ant ant = new Ant(false, i, st_pos);
            ant.start_travelling(gr,new ArrayList<>(cities));
            if(ant.distance_made<Lmin) Lmin = ant.distance_made;

        }
        if(iterator%20==0) System.out.println("Min distance from ants: "+ Lmin);
    }
}
