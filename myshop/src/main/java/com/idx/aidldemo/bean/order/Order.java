package com.idx.aidldemo.bean.order;

/**
 * Created by hayden on 18-5-31.
 */

import com.idx.aidldemo.bean.sell.Sell;
import com.idx.aidldemo.bean.sell.SellDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * 用户订单类
 */
@Entity(nameInDb = "_ORDER")
public class Order {
    @Id
    private Long order_id;
    @Property
    private long order_time;
    @Property
    private double order_cost;
    private Long userId;
    @ToMany(referencedJoinProperty = "orderId")
    private List<Sell> sells;
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1410553039)
    public synchronized void resetSells() {
        sells = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 650184484)
    public List<Sell> getSells() {
        if (sells == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SellDao targetDao = daoSession.getSellDao();
            List<Sell> sellsNew = targetDao._queryOrder_Sells(order_id);
            synchronized (this) {
                if(sells == null) {
                    sells = sellsNew;
                }
            }
        }
        return sells;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 965731666)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 949219203)
    private transient OrderDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public double getOrder_cost() {
        return this.order_cost;
    }
    public void setOrder_cost(double order_cost) {
        this.order_cost = order_cost;
    }
    public long getOrder_time() {
        return this.order_time;
    }
    public void setOrder_time(long order_time) {
        this.order_time = order_time;
    }
    public Long getOrder_id() {
        return this.order_id;
    }
    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
    @Generated(hash = 705648569)
    public Order(Long order_id, long order_time, double order_cost, Long userId) {
        this.order_id = order_id;
        this.order_time = order_time;
        this.order_cost = order_cost;
        this.userId = userId;
    }
    @Generated(hash = 1105174599)
    public Order() {
    }
}
