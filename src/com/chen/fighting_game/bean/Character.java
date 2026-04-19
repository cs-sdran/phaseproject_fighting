package com.chen.fighting_game.bean;

import java.security.PublicKey;

public class Character {
     public String name;
     public int HP;
     public int MAXHP;
     public int ATK;
     public int DEF;

    public Character(String name, int HP, int ATK, int DEF) {
        this.name = name;
        this.HP = HP;
        this.MAXHP = HP;
        this.ATK = ATK;
        this.DEF = DEF;
    }

    public Character() {
    }


    //  1.判断是否存活
    public boolean isAlive() {
        return HP > 0;
    }
    //  2.回复血量
    public void recover(int hp) {
        HP += hp;
        if (HP > MAXHP) {
            HP = MAXHP;
        }
    }
    //  3.收到伤害 ，敌人收到的伤害需要重写
    public void receiveDamage(int damage) {
        HP -= damage;
        if (HP < 0) {
            HP = 0;
        }
    }
    //  4.展示人物属性
    public void show() {
            System.out.println("名称：" + name);
            System.out.println("当前血量：" + HP);
            System.out.println("攻击力：" + ATK);
            System.out.println("防御力：" + DEF);
    }


}
