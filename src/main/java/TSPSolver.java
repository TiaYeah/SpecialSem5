import java.util.*;

public class TSPSolver {
    //private Map<Route, Double> routesHash;
    int clusteringDepth = 0;
    Strategy strategy;

    public TSPSolver(Strategy strategy) {
        this.strategy = strategy;
    }

    public double solve(List<Cluster> originalCities, int clusteringCoef, int fullSolveCount) {
        double result = 0;
        clusteringDepth = 0;
        List<Cluster> cities = new ArrayList<>(originalCities);

        while (cities.size() > fullSolveCount) {
            cities = clustering(cities, Math.ceilDiv(cities.size(), clusteringCoef));
//            for (Cluster c : cities) {
//                c.printCluster(0);
//            }
            clusteringDepth++;
        }

        cities = fullTSPSolve(cities, calculateRoutesLength(cities));

        //while (clusteringDepth != 0 ) {
        for (int i = 0; i < clusteringDepth; i++) {
            cities = recover(cities);
        }
        //}

        for (int i = 0; i < cities.size(); i++) {
            int cityId1 = i;
            int cityId2 = i == cities.size() - 1 ? 0 : i + 1;

            double len = Math.sqrt(Math.pow(cities.get(cityId1).centerX - cities.get(cityId2).centerX, 2) + Math.pow(cities.get(cityId1).centerY - cities.get(cityId2).centerY, 2));
            result += len;
        }
        return result;
    }

    private List<Cluster> clustering(List<Cluster> cities, int clusterCount) {
        List<Cluster> clusters = new ArrayList<>();

        List<List<Double>> routes = calculateRoutesLength(cities);

        if (strategy == Strategy.BASE) {
            //List<Double>


//        System.out.println(routes);
//        routes.sort(Comparator.comparingDouble(el -> el.length));
//        System.out.println(routes);
            Pair<Integer, Integer> maxDistancePair = maxLengthList(routes);
            // System.out.println(routes);
            // clusters.add(cities.get(maxDistancePair.first));

            Cluster tmpCluster = new Cluster(clusters.size() + 1);
            tmpCluster.addCity(cities.get(maxDistancePair.first));
            clusters.add(tmpCluster);
            tmpCluster = new Cluster(clusters.size() + 1);
            tmpCluster.addCity(cities.get(maxDistancePair.second));
            clusters.add(tmpCluster);
            cities.remove((int) maxDistancePair.first);
            cities.remove((int) maxDistancePair.second - 1);
            //System.out.println(clusters);

//
//        int index = 0;
            List<Double> sumLengths = new ArrayList<>();

            for (Cluster cluster : cities) {
                sumLengths.add(-1.0);
            }
            while (clusters.size() < clusterCount) {
                for (int i = 0; i < cities.size(); i++) {
//                boolean contains = false;
//                for (Cluster cluster : clusters) {
//                    if (cluster.cities.contains(cities.get(i))) {
//                        contains = true;
//                    }
//                }

//                if (!contains) {
                    double len = 0;
                    for (Cluster cluster : clusters) {
                        len += getRoute(routes, cluster.cities.getFirst().id - 1, cities.get(i).id - 1);
                    }
                    sumLengths.set(i, len);
//                }
                }
                int indexOfMaxSum = sumLengths.indexOf(sumLengths.stream().max(Double::compareTo).get());
                tmpCluster = new Cluster(clusters.size() + 1);
                tmpCluster.addCity(cities.get(indexOfMaxSum));
                clusters.add(tmpCluster);
                cities.remove((int) indexOfMaxSum);

//            System.out.println("Длинны " + sumLengths);
                for (int i = 0; i < cities.size(); i++) {
                    sumLengths.set(i, -1.0);
                }
                //System.out.println(clusters);
            }

            for (Cluster city : cities) {
                List<Double> lengths = new ArrayList<>();
                for (Cluster cluster : clusters) {
                    double len = Math.sqrt(Math.pow(city.centerX - cluster.centerX, 2) + Math.pow(city.centerY - cluster.centerY, 2));
                    lengths.add(len);
                }

                int nearestClusterIndex = lengths.indexOf(lengths.stream().min(Double::compareTo).get());
                clusters.get(nearestClusterIndex).addCity(city);
            }
        } else if (strategy == Strategy.OWN) {

                boolean isEvenSize = cities.size() % 2 == 0;
                int clusterCountOwn = isEvenSize ? cities.size() / 2 : cities.size() / 2 + 1;
                List<Integer> usedCities = new ArrayList<>();

                while (clusters.size() < clusterCountOwn) {
                    Pair<Integer, Integer> minLenPair = minLengthList(routes);
                    Cluster tmpCluster = new Cluster(clusters.size() + 1);
                    getTwoNearestCities(cities, routes, usedCities, tmpCluster, minLenPair);

                    clusters.add(tmpCluster);

                    if (!isEvenSize && clusters.size() == clusterCountOwn - 1) {
                        for (int i = 0; i < cities.size(); i++) {
                            if (usedCities.contains(i)) {
                                continue;
                            }
                            tmpCluster = new Cluster(clusters.size() + 1);
                            tmpCluster.addCity(cities.get(i));
                            clusters.add(tmpCluster);
                        }
                    }

                }
            
        }
        //System.out.println("Города после " + cities);
        //fullTSPSolve(cities, routes);

//
        return clusters;
    }

    private void getTwoNearestCities(List<Cluster> cities, List<List<Double>> routes, List<Integer> usedCities, Cluster tmpCluster, Pair<Integer, Integer> minLenPair) {
        tmpCluster.addCity(cities.get(minLenPair.first));
        tmpCluster.addCity(cities.get(minLenPair.second + 1 + minLenPair.first));
        usedCities.add(minLenPair.first);
        usedCities.add(minLenPair.second + 1 + minLenPair.first);


        for (int i = 0; i < routes.get(minLenPair.first).size(); i++) {
            routes.get(minLenPair.first).set(i, Double.MAX_VALUE);
        }
        for (int i = 0; i < minLenPair.first; i++) {
            routes.get(i).set(minLenPair.first - 1 - i, Double.MAX_VALUE);
        }

        for (int i = 0; i < minLenPair.first + minLenPair.second + 1; i++) {
            routes.get(i).set(minLenPair.second + minLenPair.first - i, Double.MAX_VALUE);
        }
        if (minLenPair.second + 1 + minLenPair.first != cities.size() - 1) {
            for (int i = 0; i < routes.get(minLenPair.second + 1 + minLenPair.first).size(); i++) {
                routes.get(minLenPair.second + 1 + minLenPair.first).set(i, Double.MAX_VALUE);
            }
        }
    }

    public List<Cluster> fullTSPSolve(List<Cluster> clusters, List<List<Double>> routes) {
        List<List<Cluster>> permutations = new ArrayList<>();

        generatePermutations(clusters, 0, permutations);

        List<Double> permutationsLengths = new ArrayList<>();
        for (List<Cluster> permutation : permutations) {
            double length = 0;
            for (int i = 0; i < permutation.size(); i++) {
                int id1 = permutation.get(i).id;
                int id2 = i == permutation.size() - 1 ? permutation.getFirst().id : permutation.get(i + 1).id;
                length += getRoute(routes, id1 - 1, id2 - 1);
            }
            permutationsLengths.add(length);
        }

        //System.out.println(permutationsLengths);
        Double min = Double.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < permutationsLengths.size(); i++) {
            if (permutationsLengths.get(i) < min) {
                min = permutationsLengths.get(i);
                minIndex = i;
            }
        }

        //System.out.println("Минимальный путь: " + min);
        //System.out.print("Перестановка ");
        for (int i = 0; i < permutations.get(minIndex).size(); i++) {
            //System.out.print(permutations.get(minIndex).get(i).id + " ");
            if (i != permutations.get(minIndex).size() - 1) {
                //System.out.print("-> ");
            }
        }
        //System.out.println();
        return permutations.get(minIndex);
    }

    public List<Cluster> recover(List<Cluster> clusters) {
        //System.out.println("Возврат");
        //List<List<Double>> routes = calculateRoutesLength(clusters);

        for (int i = 0; i < clusters.size(); i++) {
            int id1 = i;
            int id2 = i == clusters.size() - 1 ? 0 : i + 1;
            Cluster cluster1 = clusters.get(id1);
            Cluster cluster2 = clusters.get(id2);

            List<List<Double>> routes = new ArrayList<>();

            for (Cluster city1 : cluster1.cities) {
                routes.add(new ArrayList<>());
            }

            for (Cluster city1 : cluster1.cities) {
                boolean startCityExists = city1 == cluster1.startCity;

                for (Cluster city2 : cluster2.cities) {
                    if (cluster1.cities.size() != 1 && startCityExists) {
                        routes.get(cluster1.cities.indexOf(city1)).add(Double.MAX_VALUE);
                        continue;
                    }
                    if (cluster2.cities.size() != 1 && city2 == cluster2.endCity) {
                        routes.get(cluster1.cities.indexOf(city1)).add(Double.MAX_VALUE);
                        continue;
                    }
                    double len = Math.sqrt(Math.pow(city1.centerX - city2.centerX, 2) + Math.pow(city1.centerY - city2.centerY, 2));
                    routes.get(cluster1.cities.indexOf(city1)).add(len);
                }
            }
            Pair<Integer, Integer> minDistancePair = minLengthList(routes);

            //printList(routes);
            cluster1.endCity = cluster1.cities.get(minDistancePair.first);
            cluster2.startCity = cluster2.cities.get(minDistancePair.second);
            //System.out.println("Пара проставлена");
        }

        calculateRouteThroughCities(clusters);

        List<Cluster> result = new ArrayList<>();

        for (Cluster cluster : clusters) {
            result.addAll(cluster.routeThroughCities);
        }

        for (int i = 0; i < result.size(); i++) {
            //System.out.print(result.get(i).id + " ");
            if (i != result.size() - 1) {
                //System.out.print("-> ");
            }
        }
        //System.out.println();
        return result;
    }

    private void calculateRouteThroughCities(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            List<Cluster> routeThroughCities = new ArrayList<>();

            routeThroughCities.add(cluster.startCity);
            List<List<Double>> routes = calculateRoutesLength(cluster.cities);

            //System.out.println("Пути внтури кластера " + routes);

            //int startIndex = cluster.cities.indexOf(cluster.startCity);
            int endIndex = cluster.cities.indexOf(cluster.endCity);

            while (routeThroughCities.size() < cluster.cities.size() - 1) {
                double minLen = Double.MAX_VALUE;
                int minCityIndex = -1;

                for (int i = 0; i < cluster.cities.size(); i++) {
                    if (i == endIndex || routeThroughCities.contains(cluster.cities.get(i))) {
                        continue;
                    }

                    int previousIndex = cluster.cities.indexOf(routeThroughCities.getLast());

                    Double length = getRoute(routes, i, previousIndex); // Длинна между предыдущим и следующим

                    if (length < minLen) {
                        minCityIndex = i;
                    }

                }

                routeThroughCities.add(cluster.cities.get(minCityIndex));
            }

            if (cluster.cities.size() != 1) {
                routeThroughCities.add(cluster.endCity);
            }
            cluster.setRouteThroughCities(routeThroughCities);
            for (int i = 0; i < routeThroughCities.size(); i++) {
                //System.out.print(routeThroughCities.get(i).id + " ");
                if (i != routeThroughCities.size() - 1) {
                    //System.out.print("-> ");
                }
            }
            //System.out.println();
        }
    }

    public void generatePermutations(List<Cluster> clusters, int start, List<List<Cluster>> permutations) {

        if (start == clusters.size() - 1) {
            permutations.add(new ArrayList<>(clusters));
            return;
        }

        for (int i = start; i < clusters.size(); i++) {
            swapClusters(clusters, start, i);
            generatePermutations(clusters, start + 1, permutations);
            swapClusters(clusters, start, i);
        }
    }

    private void swapClusters(List<Cluster> clusters, int i, int j) {
        Cluster tempCluster = clusters.get(i);
        clusters.set(i, clusters.get(j));
        clusters.set(j, tempCluster);
    }

    private List<List<Double>> calculateRoutesLength(List<Cluster> clusters) {
        List<List<Double>> routes = new ArrayList<>();
        for (int i = 0; i < clusters.size() - 1; i++) {
            routes.add(new ArrayList<>());
        }

        for (int i = 0; i < clusters.size() - 1; i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                double len = Math.sqrt(Math.pow(clusters.get(i).centerX - clusters.get(j).centerX, 2) + Math.pow(clusters.get(i).centerY - clusters.get(j).centerY, 2));

                routes.get(i).add(len);
            }
        }

        //printList(routes);
        //System.out.println(getRoute(routes, 4, 2));
        //System.out.println(getRoute(routes, 2, 4));
        return routes;
    }

    private Double getRoute(List<List<Double>> routes, int id1, int id2) {
        return routes.get(Math.min(id1, id2)).get(Math.max(id1, id2) - 1 - Math.min(id1, id2));
    }

    private int minLength(List<Double> routes) {
        return routes.indexOf(routes.stream().min(Double::compareTo).get());
    }

    private Pair<Integer, Integer> minLengthList(List<List<Double>> routes) {
        List<Double> result = new ArrayList<>();
        for (List<Double> route : routes) {
            result.add(route.get((minLength(route))));
        }
        int minIndex = result.indexOf(result.stream().min(Double::compareTo).get());

        return new Pair<>(minIndex, minLength(routes.get(minIndex)));
    }

    private int maxLength(List<Double> routes) {
        return routes.indexOf(routes.stream().max(Double::compareTo).get());
    }

    private Pair<Integer, Integer> maxLengthList(List<List<Double>> routes) {
        List<Double> result = new ArrayList<>();
        for (List<Double> route : routes) {
            result.add(route.get((maxLength(route))));
        }
        int maxIndex = result.indexOf(result.stream().max(Double::compareTo).get());

        return new Pair<>(maxIndex, maxLength(routes.get(maxIndex)) + maxIndex + 1);
    }

    private void printList(List<List<Double>> routes) {
        for (int i = 0; i < routes.size(); i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < routes.get(i).size(); j++) {
                System.out.print(routes.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}
