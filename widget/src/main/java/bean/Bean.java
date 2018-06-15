package bean;

/**
 * Created by hayden on 18-6-11.
 */

public class Bean {
    private String name;
    private String address;
    private double distance;

    public Bean(String name, String address, double distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", distance=" + distance +
                '}';
    }
}
