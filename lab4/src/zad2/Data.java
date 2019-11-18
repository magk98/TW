package zad2;

import static java.lang.Math.abs;

public class Data {
    private int id;
    public Data(int id) { this.id = id; }
    public Data() { this(0); }

    @Override
    public String toString() {
        return "Data " + id;
    }

}
