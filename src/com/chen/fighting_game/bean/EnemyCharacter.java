package com.chen.fighting_game.bean;

public class EnemyCharacter extends  Character {
    public String skill;
    public boolean defending;//当前人物是否拥有减少伤害的BUFF

    public EnemyCharacter() {
    }
    public EnemyCharacter(String name, int HP, int ATK, int DEF, String skill) {
        super(name, HP, ATK, DEF);
        this.skill = skill;
    }

    public void receiveDamage(int damage) {
        int max=damage;
        if (defending) {
            max=damage/2>1?damage/2:1;
            defending = false;
        }
        super.receiveDamage(max);
    }
}
