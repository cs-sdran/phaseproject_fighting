package com.chen.fighting_game.ui;
import com.chen.fighting_game.bean.EnemyCharacter;
import com.chen.fighting_game.bean.HeroCharacter;
import com.chen.fighting_game.bean.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class game {
    public void gameStart(String playerName)//启动游戏
    {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("  🎮 欢迎" + playerName + "来到文字格斗游戏 🎮       ");
        System.out.println("╚════════════════════════════════════════════╝");

        HeroCharacter hero = createHeroCharacter(playerName);

        System.out.println("角色创建成功");
        System.out.println("初始属性为：");
        hero.show();
        hero.showSkills();


/*        | 敌人名称 | 生命值 | 攻击力 | 防御力 | 技能（变量）                                           |
| -------- | ------ | ------ | ------ | ------------------------------------------------------ |
            | 初级战士 | 80     | 15     | 10     | 猛击（150%伤害）                                       |
| 敏捷刺客 | 60     | 20     | 5      | 快速攻击（2次50%伤害）                                 |
| 重装坦克 | 120    | 10     | 20     | 防御姿态（下回合伤害减半） buff（ boolean defendding） |
| 神秘法师 | 70     | 25     | 8      | 火球术（180%伤害）                                     |*/

        //创建敌人列表
        ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
        enemies.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemies.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemies.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemies.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        //5.1 重置敌人的属性，敌人属性每场HP+10, ATK+3, DEF+2（敌人：越来越打）（第二场的时候）
        int count = 1; //记录战斗次数
        int wins=0;//记录胜利次数
        while(hero.isAlive()) {
            if (wins != 0)//增加敌人属性
            {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).MAXHP += 10;// 增加敌人生命值
                    enemies.get(i).HP = enemies.get(i).MAXHP;
                    enemies.get(i).ATK += 3;  // 增加敌人攻击力
                    enemies.get(i).DEF += 2;  // 增加敌人防御力
                    enemies.get(i).defending = false;
                }
            }

            //5.2 随机选择敌人(Random)
            Random random = new Random();
            int index = random.nextInt(enemies.size());
            EnemyCharacter enemy = enemies.get(index);

            //5.3 战斗开始
            System.out.println("⚔\uFE0F 第"+count+"场战斗开始！！ 对手："+enemy.name+"！");

            int round = 1;//记录当前回合数
            while(hero.isAlive())
            {
                System.out.println("⚔\uFE0F 第"+round+"回合开始！");
                //打印双方血条
                System.out.println(printHPLine(hero.name, hero.HP, hero.MAXHP));
                System.out.println(printHPLine(enemy.name, enemy.HP, enemy.MAXHP));

                //5.4玩家回合
                playerRound(hero, enemy);
                //5.5判断敌人是否被击败
                if(!enemy.isAlive())
                {
                    System.out.println("🎉 恭喜你，你击败了："+(enemy.name)+"!!!!");
                    System.out.println("------------------------");
                    wins++;//胜场加1
                    break;
                }

                //5.6敌人回合
                enemyRound(hero, enemy);
                //5.7判断玩家是否被击败
                if(!hero.isAlive())
                {
                    System.out.println("☠\uFE0F 你被"+enemy.name+"击败了！");
                    System.out.println("--------------------------");
                    break;
                }

                round++;
            }

            //5.8 一轮战斗结束
            if(hero.isAlive())
            {
                Random r=new Random();
                int heal=r.nextInt(20,41);//战斗结束后恢复20-40点生命值
                hero.recover(heal);

                System.out.println("恢复"+heal+"点生命值");
                System.out.println("当前胜场为："+ wins);
                System.out.println("--------------------------");
            }
            //5.9 属性增加
            if(hero.isAlive()&&wins>0&&wins%3==0)//每胜利三场属性增加
            {
                System.out.println("恭喜 您获得了属性提升");
                hero.MAXHP+=30;
                hero.ATK+=5;
                hero.DEF+=5;
                System.out.println("最大生命+30,攻击力+5，防御力+5");
                System.out.println("当前属性为：");
                hero.show();
            }

            //5.10 询问玩家是否继续
            if(hero.isAlive())
            {
                Scanner scanner = new Scanner(System.in);
                System.out.println("是否继续战斗？(y/n)");
                String answer = scanner.next();
                if (answer.equalsIgnoreCase("y")) {
                    System.out.println("继续战斗！");
                    count++;
                    continue;//开始下一轮战斗
                } else {
                    System.out.println("游戏结束！");
                    break;
                }
            }


        }

        //游戏最终结算

        System.out.println("最终结果为：");
        hero.show();
        System.out.println("总胜场为："+wins);
        System.exit(0);




    }

    //创建玩家角色
    public HeroCharacter createHeroCharacter(String playerName) {
        System.out.println("创建你的角色");
        System.out.println("您的角色名为：" + playerName);
        System.out.println("请分配属性点，(共20点)");

        int point = 20;

        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");

        String[] attributes = {"HP", "ATK", "DEF"};
        int[] attributePoints = new int[3];

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            System.out.println("请输入" + attributes[i] + "的属性点数：");
            int points = sc.nextInt();
            if (points > point) {
                System.out.println("属性点超出，已将剩余属性全部分配到：" + attributes[i]);
                points = point;
            }
            if (points < 0) {
                System.out.println("属性点数不能小于0，已自动设置为0");
                points = 0;
            }
            point -= points;
            attributePoints[i] = points;
            System.out.println("已分配" + points + "点" + attributes[i] + "属性");
            System.out.println("还剩" + point + "点属性点");
        }
        HeroCharacter heroCharacter = new HeroCharacter(playerName, 100 + attributePoints[0] * 10, 10 + attributePoints[1] * 2, 0 + attributePoints[2]);

        heroCharacter.skills.add("普通攻击");
        heroCharacter.skills.add("强力一击");
        heroCharacter.skills.add("生命回复");

        return heroCharacter;
    }

    //定义一个方法打印双方血条
    public String printHPLine(String name, int HP, int MAXHP) {
        int barlength = 20;
        int truelength = (int) (HP * 1.0 * barlength / MAXHP);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(":").append("【");
        for (int i = 0; i < barlength; i++) {
            if (i < truelength) sb.append("█");
            else sb.append(" ");

        }
        sb.append("】").append(HP + "/" + MAXHP+" ").append("HP");
        return sb.toString();
    }


    //玩家回合
    public void playerRound(HeroCharacter hero, EnemyCharacter enemy) {
        System.out.println("===== 你的回合 =====");

        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击 (消耗10HP)");
        System.out.println("3. 生命汲取 (消耗10HP，恢复生命)");
        System.out.println("请选择技能：(1-3)");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            default -> System.out.println("没有该技能，默认使用普通攻击");
            case 1 -> {
                int damage1=calculateDamage(hero.ATK, enemy.DEF);
                System.out.println("⚔\uFE0F 你使用了普通攻击,对"+enemy.name+"造成了"+damage1+"点伤害");
                enemy.receiveDamage(damage1);
                break;
            }
            case 2 -> {
                if(hero.HP>10){
                    hero.receiveDamage(10);
                int damage2=calculateDamage((int)(hero.ATK*1.8), enemy.DEF);
                System.out.println("\uD83D\uDCA5 消耗10HP 你使用了强力一击,对"+enemy.name+"造成了"+damage2+"点伤害");
                enemy.receiveDamage(damage2);}
                else
                    System.out.println("你的HP不足，无法使用技能");
                break;
            }
            case 3 -> {
                if(hero.HP>10)
                {
                    hero.receiveDamage(10);
                    Random random = new Random();
                    int restore = random.nextInt(21) ;
                    hero.recover(restore);
                    System.out.println("⚡ 你使用了生命恢复，恢复了"+restore+"点生命");
                }
                else
                    System.out.println("你的HP不足，无法使用技能");

                break;
            }

        }


    }

    //敌人回合
    public void enemyRound(HeroCharacter hero, EnemyCharacter enemy) {
        System.out.println("===== 敌人回合 =====");
        Random random = new Random();
        String action="普通攻击";
        int num=random.nextInt(2);
        if(num==1) //50%的概率使用技能
        {
            action=enemy.skill;
        }
        switch (action) {
            case "普通攻击" -> {
                int damage1=calculateDamage(enemy.ATK, hero.DEF);
                System.out.println("⚔\uFE0F "+enemy.name+"使用了普通攻击,对"+hero.name+"造成了"+damage1+"点伤害");
                hero.receiveDamage(damage1);
                break;
            }
            case "猛击"-> {
                int damage2=calculateDamage((int)(enemy.ATK*1.5), hero.DEF);
                System.out.println("\uD83D\uDD25 "+enemy.name+"使用了猛击,对"+hero.name+"造成了"+damage2+"点伤害");
                hero.receiveDamage(damage2);
                break;
            }
            case "快速攻击"-> {
                int damage3=0;
                for (int i = 0; i < 2; i++) {
                    damage3+=calculateDamage(enemy.ATK, hero.DEF);
                }
                System.out.println("⚡ "+enemy.name+"使用了快速攻击,对"+hero.name+"造成了"+damage3+"点伤害");
                hero.receiveDamage(damage3);
                break;
            }
            case "防御姿态"-> {
                enemy.defending = true;
                System.out.println("🛡 "+enemy.name+"使用了防御姿态");
                break;
             }
             case "火球术"-> {
                int damage4=calculateDamage((int)(enemy.ATK*1.8), hero.DEF);
                System.out.println("\uD83D\uDD25 "+enemy.name+"使用了火球术,对"+hero.name+"造成了"+damage4+"点伤害");
                hero.receiveDamage(damage4);
                break;
             }

            }
    }

    //计算伤害
    public int calculateDamage(int ATK, int DEF) {
        int damage = ATK - DEF;
        if(damage < 0)
            damage = 1;
        return damage;
    }
}