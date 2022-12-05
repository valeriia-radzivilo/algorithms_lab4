
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        int min = main_tsl.start_program(1, gr, antAmount,wildAntAmount,citiesAmount);

        System.out.println("\nMinimum from all iterations: "+ min);
        Graph.greedyLength(gr,citiesAmount);

        assertEquals(71,min,"Min value Test Check");

    }

}