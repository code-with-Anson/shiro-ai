package com.alice.shiroai.utils;

public class UserContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 获取当前登录的用户id
     *
     * @return
     */
    public static Long getUser() {
        return threadLocal.get();
    }

    /**
     * 保存当前登录用户id到ThreadLocal
     *
     * @param userId
     */
    public static void setUser(Long userId) {
        threadLocal.set(userId);
    }

    /**
     * 移除当前登录用户信息
     */
    public static void removeUser() {
        threadLocal.remove();
    }

}
