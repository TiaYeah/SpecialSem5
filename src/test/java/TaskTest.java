import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskTest {
    Scanner scanner;
    List<Double> optimalLengths = Arrays.asList(6656.0, 564.0, 1019.0, 2513.0, 79114.0, 206171.0);
    static List<Double> baseLengths = new ArrayList<>();
    static List<Double> ownLengths = new ArrayList<>();

    @Test
    public void testBase() {
        List<Cluster> cities = new ArrayList<>();

        getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/dj38.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);

        System.out.println("Суммарный путь через города " + tspSolver.solve(cities, 4, 8));

        tspSolver = new TSPSolver(Strategy.OWN);

        System.out.println("Суммарный путь через города свой " + tspSolver.solve(cities, -1, 8));
    }


    @Test
    public void test01() {
        List<Cluster> cities = new ArrayList<>();

        //getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        getDataFromFile(new File("src/main/resources/dj38.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);
        double baseLength = tspSolver.solve(cities, 16, 8);
        baseLengths.add((baseLength - optimalLengths.get(0)) / optimalLengths.get(0));
        System.out.println("Суммарный путь через города базовый " + baseLength);
        System.out.println("Базовое отклонение " + baseLengths.getLast());

        tspSolver = new TSPSolver(Strategy.OWN);
        double ownLength = tspSolver.solve(cities, -1, 8);
        ownLengths.add((ownLength - optimalLengths.get(0)) / optimalLengths.get(0));
        System.out.println("Суммарный путь через города свой " + ownLength);
        System.out.println("Свое отклонение " + ownLengths.getLast());
    }

    @Test
    public void test02() {
        List<Cluster> cities = new ArrayList<>();

        //getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        getDataFromFile(new File("src/main/resources/xqf131.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);
        double baseLength = tspSolver.solve(cities, 16, 8);
        baseLengths.add((baseLength - optimalLengths.get(1)) / optimalLengths.get(1));
        System.out.println("Суммарный путь через города базовый " + baseLength);
        System.out.println("Базовое отклонение " + baseLengths.getLast());

        tspSolver = new TSPSolver(Strategy.OWN);
        double ownLength = tspSolver.solve(cities, -1, 8);
        ownLengths.add((ownLength - optimalLengths.get(1)) / optimalLengths.get(1));
        System.out.println("Суммарный путь через города свой " + ownLength);
        System.out.println("Свое отклонение " + ownLengths.getLast());
    }

    @Test
    public void test03() {
        List<Cluster> cities = new ArrayList<>();

        //getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        getDataFromFile(new File("src/main/resources/xqg237.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);
        double baseLength = tspSolver.solve(cities, 16, 8);
        baseLengths.add((baseLength - optimalLengths.get(2)) / optimalLengths.get(2));
        System.out.println("Суммарный путь через города базовый " + baseLength);
        System.out.println("Базовое отклонение " + baseLengths.getLast());

        tspSolver = new TSPSolver(Strategy.OWN);
        double ownLength = tspSolver.solve(cities, -1, 8);
        ownLengths.add((ownLength - optimalLengths.get(2)) / optimalLengths.get(2));
        System.out.println("Суммарный путь через города свой " + ownLength);
        System.out.println("Свое отклонение " + ownLengths.getLast());
    }

    @Test
    public void test04() {
        List<Cluster> cities = new ArrayList<>();

        //getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        getDataFromFile(new File("src/main/resources/xql662.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);
        double baseLength = tspSolver.solve(cities, 16, 8);
        baseLengths.add((baseLength - optimalLengths.get(3)) / optimalLengths.get(3));
        System.out.println("Суммарный путь через города базовый " + baseLength);
        System.out.println("Базовое отклонение " + baseLengths.getLast());

        tspSolver = new TSPSolver(Strategy.OWN);
        double ownLength = tspSolver.solve(cities, -1, 8);
        ownLengths.add((ownLength - optimalLengths.get(3)) / optimalLengths.get(3));
        System.out.println("Суммарный путь через города свой " + ownLength);
        System.out.println("Свое отклонение " + ownLengths.getLast());
    }

    @Test
    public void test05() {
        List<Cluster> cities = new ArrayList<>();

        //getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        getDataFromFile(new File("src/main/resources/uy734.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);
        double baseLength = tspSolver.solve(cities, 16, 8);
        baseLengths.add((baseLength - optimalLengths.get(4)) / optimalLengths.get(4));
        System.out.println("Суммарный путь через города базовый " + baseLength);
        System.out.println("Базовое отклонение " + baseLengths.getLast());

        tspSolver = new TSPSolver(Strategy.OWN);
        double ownLength = tspSolver.solve(cities, -1, 8);
        ownLengths.add((ownLength - optimalLengths.get(4)) / optimalLengths.get(4));
        System.out.println("Суммарный путь через города свой " + ownLength);
        System.out.println("Свое отклонение " + ownLengths.getLast());
    }

    @Test
    public void test06() {
        List<Cluster> cities = new ArrayList<>();

        //getDataFromFile(new File("src/main/resources/sixteenCities.tsp"), cities);
        //getDataFromFile(new File("src/main/resources/Example.tsp"), cities);
        getDataFromFile(new File("src/main/resources/ei8246.tsp"), cities);

        TSPSolver tspSolver = new TSPSolver(Strategy.BASE);
        double baseLength = tspSolver.solve(cities, 16, 8);
        baseLengths.add((baseLength - optimalLengths.get(5)) / optimalLengths.get(5));
        System.out.println("Суммарный путь через города базовый " + baseLength);
        System.out.println("Базовое отклонение " + baseLengths.getLast());

        tspSolver = new TSPSolver(Strategy.OWN);
        double ownLength = tspSolver.solve(cities, -1, 8);
        ownLengths.add((ownLength - optimalLengths.get(5)) / optimalLengths.get(5));
        System.out.println("Суммарный путь через города свой " + ownLength);
        System.out.println("Свое отклонение " + ownLengths.getLast());

        System.out.println("Среднее базовое отклонение " + baseLengths.stream().mapToDouble(a -> a).average());
        System.out.println("Среднее свое отклонение " + ownLengths.stream().mapToDouble(a -> a).average());
    }

    private void getDataFromFile(File file, List<Cluster> cities) {
        try {
            scanner = new Scanner(file);

            String line;
            int dimension;
            boolean nodeSection = false;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.startsWith("Dimension")) {
                    dimension = Integer.parseInt(line.split(" ")[1]);
                    continue;
                }

                if (line.startsWith("NODE_COORD_SECTION")) {
                    nodeSection = true;
                    continue;
                }

                if (line.startsWith("EOF")) {
                    break;
                }
                if (nodeSection) {
                    String[] cityData = line.split(" ");
                    cities.add(new Cluster(Integer.parseInt(cityData[0]), Double.parseDouble(cityData[1]), Double.parseDouble(cityData[2])));
                }
            }

            //System.out.println(cities);
//            for (int i = 0; i < cities.size(); i++) {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("A").append(i).append(" = (").append(cities.get(i).centerX).append(", ").append(cities.get(i).centerY).append(")").append("\n");
//                System.out.println(stringBuilder.toString());
//            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
