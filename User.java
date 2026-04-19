package com.chen.fighting_game.bean;

import java.util.Random;

public class User {
    private String id;
    private String name;
    private String password;
    private int state;

    public User() {
        this.id = createid();
        this.state = 1;
    }
    public User( String name, String password) {
        this.id=createid() ;
        this.name = name;
        this.password = password;
        this.state = 1;

    }

    //用户无法自己设置id，是自动生成的，格式为people+随机五个数字
    public String createid()
    {
        Random random = new Random();
       //不能这么写，可能没有五位 return "people" + random.nextInt(100000);

        StringBuilder sb = new StringBuilder("people");
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
        }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
