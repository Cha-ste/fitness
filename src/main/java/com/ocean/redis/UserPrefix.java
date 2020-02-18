package com.ocean.redis;

public class UserPrefix extends BasePrefix{

    public UserPrefix(String prefix) {
        super(prefix);
    }

    public static UserPrefix getById = new UserPrefix("Id");
    public static UserPrefix getByName = new UserPrefix("Name");
    public static UserPrefix getByToken = new UserPrefix("Token");
}
