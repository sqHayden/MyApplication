// IMyAidlInterface.aidl
package com.idx.aidl;

import com.idx.aidl.bean.User;
// Declare any non-default types here with import statements

interface IService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addUser(in User user);
    User getUser();
}
