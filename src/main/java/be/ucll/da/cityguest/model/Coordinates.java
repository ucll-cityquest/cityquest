package be.ucll.da.cityguest.model;

public class Coordinates {
    private double lat;
    private double lng;

    private Coordinates() { }

    public Coordinates(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
