import java.util.Objects;

public class Route implements Comparable<Route> {
    Cluster cluster1, cluster2;
    Double length;

    public Route(Cluster cluster1, Cluster cluster2) {
        this.cluster1 = cluster1;
        this.cluster2 = cluster2;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;
        return Objects.equals(cluster1, route.cluster1) && Objects.equals(cluster2, route.cluster2) || Objects.equals(cluster1, route.cluster2) && Objects.equals(cluster2, route.cluster1);
    }

    @Override
    public int hashCode() {
        int hash1 = Objects.hash(cluster1);
        int hash2 = Objects.hash(cluster2);

        return 31 * Math.min(hash1, hash2) + Math.max(hash1, hash2);
    }


    @Override
    public String toString() {
        return "Route{" +
                "id=" + cluster1.id +
                ", id=" + cluster2.id +
                ", length=" + length +
                '}';
    }

    @Override
    public int compareTo(Route o) {
        return this.length.compareTo(o.length);
    }
}
