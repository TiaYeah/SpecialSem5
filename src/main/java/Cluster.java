import java.util.ArrayList;
import java.util.List;

public class Cluster {
    int id;
    List<Cluster> cities;
    double centerX, centerY;
    Cluster startCity;
    Cluster endCity;
    List<Cluster> routeThroughCities;

    public Cluster(int id) {
        this.id = id;
        cities = new ArrayList<>();
    }

    public Cluster(int id, double centerX, double centerY) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        cities = new ArrayList<>();
    }

    public void addCity(Cluster cluster) {
        cities.add(cluster);
        double sumX = 0, sumY = 0;
        for (Cluster c : cities) {
            sumX += c.centerX;
            sumY += c.centerY;
        }

        centerX = sumX / cities.size();
        centerY = sumY / cities.size();
    }

    public void setRouteThroughCities(List<Cluster> routeThroughCities) {
        this.routeThroughCities = routeThroughCities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cluster cluster = (Cluster) o;
        return id == cluster.id && Double.compare(centerX, cluster.centerX) == 0 && Double.compare(centerY, cluster.centerY) == 0;
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(centerX);
        result = 31 * result + Double.hashCode(centerY);
        return result;
    }

    public void printCluster(int margin) {
        addSpaces(margin);
        System.out.print("ID");
        System.out.println();
        addSpaces(margin);
        System.out.print(id);
        System.out.println();
        if (!cities.isEmpty()) {
            addSpaces(margin);
            System.out.print("Города в кластере");
            System.out.println();
        }

        for (Cluster c : cities) {
            c.printCluster(margin + 2);
        }
    }

    private void addSpaces(int margin) {
        for (int i = 0; i < margin; i++) {
            System.out.print(" ");
        }
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "id=" + id +
                ", cities=" + cities +
                ", centerX=" + centerX +
                ", centerY=" + centerY +
                '}';
    }
}
