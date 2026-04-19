package com.chen.fighting_game.bean;

import java.util.ArrayList;

public class HeroCharacter extends  Character{
    public ArrayList<String > skills;
    public HeroCharacter()
    {
        super();
        skills=new ArrayList<>();
    }
    public HeroCharacter(String name, int HP, int ATK, int DEF)
    {
        super(name,HP,ATK,DEF);
        skills=new ArrayList<>();
    }

    //  5.展示技能
    public void showSkills() {
        System.out.println("技能列表：");
        for (int i = 0; i < skills.size(); i++) {
            System.out.println((i + 1) + "." + skills.get(i));
        }
    }
}
