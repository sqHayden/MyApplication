package utils;

/**
 * Created by hayden on 18-5-30.
 */

import android.content.Context;

import bean.order.DaoMaster;
import bean.order.DaoSession;

/**
 * 数据库管理类
 */
public class DaoManger {
    //数据库名称
    private static final String DB_NAME = "shop.db";
    //数据库管理对象(防止多线程访问)
    private volatile static DaoManger daoManger;
    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private Context mContext;
    /*
     * 使用单例模式获取数据库
     */
    public static DaoManger getInstance(){
        if(daoManger==null){
            synchronized (DaoManger.class){
                if(daoManger==null){
                    daoManger = new DaoManger();
                }
            }
        }
        return daoManger;
    }

    /**
     * 传递对象
     * @param context 上下文
     */
    public void init(Context context){
        mContext = context;
    }

    /**
     * 判断是否存在数据库，如果没有则创建数据库
     * @return DaoMaster
     */
    private DaoMaster getDaoMaster(){
        if (daoMaster==null){
            //获取一个DevOpenHelper对象
            devOpenHelper = new DaoMaster.DevOpenHelper(mContext,DB_NAME, null);
            daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        }
        return daoMaster;
    }

    /**
     * 获取session对象
     * @return session
     */
    public DaoSession getDaoSession(){
        if (daoSession==null){
            if (daoMaster==null){
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 关闭所有的操作,数据库开启的时候，使用完毕了必须要关闭
     */
    public void close(){
        closeHelper();
        closeDaoSession();
    }
    private void closeHelper(){
        if (devOpenHelper!=null){
            devOpenHelper.close();
            devOpenHelper = null;
        }
    }
    private void closeDaoSession(){
        if (daoSession!=null){
            daoSession.clear();
            daoSession = null;
        }
    }
}
