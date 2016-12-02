package com.xiaoyuan54.child.edu.app.dao.impl;

import com.xiaoyuan54.child.edu.app.bean.user.User;
import com.xiaoyuan54.child.edu.app.dao.AbstractDao;
import com.xiaoyuan54.child.edu.app.db.utils.SQLUtil;

/**
 * Created by m on 2016-11-30.
 */

public class UserDao extends AbstractDao {

    private String findByMobile = "select * from user where mobile='%s' limit 1";
    private String deleteByMobile = "delete from user where mobile='%s'";

    public static UserDao instance()
    {
        return new UserDao();
    }

    public User findByMobile(String mobile){
        String sql = String.format(findByMobile,mobile);
        User dbUser = SQLUtil.instance().queryOne(User.class, sql);
        return dbUser;
    }

    public void modifyUserByMobile(User user){
        String sql = String.format(findByMobile,user.getMobile());
        String delete = String.format(deleteByMobile,user.getMobile());
        User dbUser = SQLUtil.instance().queryOne(User.class, sql);
        if(dbUser != null) {
            SQLUtil.instance().execute(delete);
        }
        this.insert(user);
    }

}
