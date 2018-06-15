package bean;

/**
 * Created by hayden on 18-5-31.
 */

/**
 * 此类只是用来封装Adapter
 */
public class AdapterSell {
    private int sell_img;
    private String sell_name;
    private double sell_price;

    public AdapterSell(int sell_img, String sell_name, double sell_price) {
        this.sell_img = sell_img;
        this.sell_name = sell_name;
        this.sell_price = sell_price;
    }

    public int getSell_img() {
        return sell_img;
    }

    public void setSell_img(int sell_img) {
        this.sell_img = sell_img;
    }

    public String getSell_name() {
        return sell_name;
    }

    public void setSell_name(String sell_name) {
        this.sell_name = sell_name;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }
}
