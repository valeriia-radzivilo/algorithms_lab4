import java.util.ArrayList;
import java.util.Random;

public class City {
    int id;
    int value;

    City(int id, int value)
    {
        this.id = id;
        this.value = value;
    }
    int getId(){
        return id;
    }

    public static City get_rand_city(ArrayList<City> cities)
    {
        Random random = new Random();
        int pos = random.nextInt(cities.size());
        return cities.get(pos);
    }

}
