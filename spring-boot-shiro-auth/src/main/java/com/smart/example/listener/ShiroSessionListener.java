package com.smart.example.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

public class ShiroSessionListener implements SessionListener {
    private AtomicInteger integer = new AtomicInteger();


    @Override
    public void onStart(Session session) {
        //session被创建时触发方法
        integer.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        //用户退出触发的方法
        integer.decrementAndGet();
    }

    /**
     * session过期触发的方法
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        integer.decrementAndGet();
    }

    public AtomicInteger getInteger(){
        return integer;
    }
}
