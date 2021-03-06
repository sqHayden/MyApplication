package com.idx.aidldemo.bean.order;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.idx.aidldemo.bean.order.Order;
import com.idx.aidldemo.bean.sell.Sell;
import com.idx.aidldemo.bean.user.User;

import com.idx.aidldemo.bean.order.OrderDao;
import com.idx.aidldemo.bean.sell.SellDao;
import com.idx.aidldemo.bean.user.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig orderDaoConfig;
    private final DaoConfig sellDaoConfig;
    private final DaoConfig userDaoConfig;

    private final OrderDao orderDao;
    private final SellDao sellDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        sellDaoConfig = daoConfigMap.get(SellDao.class).clone();
        sellDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        orderDao = new OrderDao(orderDaoConfig, this);
        sellDao = new SellDao(sellDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Order.class, orderDao);
        registerDao(Sell.class, sellDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        orderDaoConfig.clearIdentityScope();
        sellDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public SellDao getSellDao() {
        return sellDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
