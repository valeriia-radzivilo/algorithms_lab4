import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int iterAmount = 250,
                antAmount = 5,
                wildAntAmount = 2,
                citiesAmount = 10,
                alpha = 4,
                beta = 2;
        double rho = 0.3;



        print_info.print_task(antAmount,wildAntAmount,citiesAmount,alpha,beta,rho);

        Graph gr = new Graph(250);
        gr.make_distances(gr);





        ArrayList<Integer> starting_positions =Ant.set_ants(citiesAmount);
//        ArrayList<Integer> wilds = Ant.make_wilds(antAmount,wildAntAmount);
//        System.out.println("Wilds are placed at: "+ wilds);
        // запускаємо диких, хай побігають
        for(int i =0; i<wildAntAmount;i++)
        {
            Ant ant = new Ant(true,i,starting_positions.get(i));
            ant.start_travelling(gr,citiesAmount);
        }

        // set pheromone
        double[][] pheromone = new double[citiesAmount][citiesAmount];
        for(int i =0; i< citiesAmount;i++)
            for (int j =0; j< citiesAmount;j++)
                pheromone[i][j] = 1;


    }
}