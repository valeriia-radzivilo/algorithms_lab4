import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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

        for(int i =0; i<=100;i++) {
            if(i%20==0) System.out.println("Iteration №"+ i);
            main_tsl.start_program(-1, gr, antAmount, wildAntAmount, citiesAmount, i);
        }

        Graph.greedyLength(gr,citiesAmount);



    }

    @Test
    public void TestNotRandomValue()
    {
        int antAmount = 2,
                wildAntAmount = 0,
                citiesAmount = 5;
        int[][]distances = new int[][]{
                {0, 3, 28, 16, 28},
                {3, 0, 35, 3, 8},
                {28, 35, 0, 6, 38},
                {16, 3, 6, 0, 30},
                {28, 8, 38, 30, 0}
        };

        Graph gr = Graph.ready_graph(distances);
        gr.print_graph();
        main_tsl.start_program(1, gr, antAmount,wildAntAmount,citiesAmount,20);

        //рахує не правильно
        Graph.greedyLength(gr,citiesAmount);

    }

}