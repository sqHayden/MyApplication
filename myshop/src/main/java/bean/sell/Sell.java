package bean.sell;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hayden on 18-5-31.
 */

@Entity
public class Sell {
    @Id
    private Long sell_id;
    @Property
    private String sell_name;
    @Property
    private int sell_count;
    @Property
    private double sell_price;
    private Long orderId;
    public Long getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public double getSell_price() {
        return this.sell_price;
    }
    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }
    public int getSell_count() {
        return this.sell_count;
    }
    public void setSell_count(int sell_count) {
        this.sell_count = sell_count;
    }
    public String getSell_name() {
        return this.sell_name;
    }
    public void setSell_name(String sell_name) {
        this.sell_name = sell_name;
    }
    public Long getSell_id() {
        return this.sell_id;
    }
    public void setSell_id(Long sell_id) {
        this.sell_id = sell_id;
    }
    @Generated(hash = 582014543)
    public Sell(Long sell_id, String sell_name, int sell_count, double sell_price,
            Long orderId) {
        this.sell_id = sell_id;
        this.sell_name = sell_name;
        this.sell_count = sell_count;
        this.sell_price = sell_price;
        this.orderId = orderId;
    }
    @Generated(hash = 1012060935)
    public Sell() {
    }
}
