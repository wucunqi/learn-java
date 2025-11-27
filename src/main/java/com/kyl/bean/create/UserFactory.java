package com.kyl.bean.create;

import com.kyl.bean.create.pojo.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author wucunqi
 */
@Component
public class UserFactory implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}


