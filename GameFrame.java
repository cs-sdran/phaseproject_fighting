package com.chen.fighting_game.ui;

import com.chen.fighting_game.bean.EnemyCharacter;
import com.chen.fighting_game.bean.HeroCharacter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends JFrame {
    private HeroCharacter hero;
    private EnemyCharacter currentEnemy;
    private ArrayList<EnemyCharacter> enemies;
    private int wins;
    private int battleCount;
    private boolean inBattle;

    private JTextArea gameLog;
    private JLabel heroHPBar;
    private JLabel enemyHPBar;
    private JLabel heroInfo;
    private JLabel enemyInfo;
    private JLabel enemyNameLabel;
    private JLabel winLabel;
    private JButton attackBtn;
    private JButton strongAttackBtn;
    private JButton healBtn;
    private JButton continueBtn;

    public GameFrame(String playerName) {
        wins = 0;
        battleCount = 1;
        inBattle = false;
        initGame(playerName);
        initUI();
    }

    private void initGame(String playerName) {
        hero = createHeroCharacter(playerName);
        enemies = new ArrayList<>();
        enemies.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemies.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemies.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemies.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));
        
        startNewBattle();
    }

    private void initUI() {
        setTitle("文字格斗游戏 - 战斗中");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font chineseFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font chineseFontBold = new Font("微软雅黑", Font.BOLD, 14);
        Font titleFont = new Font("微软雅黑", Font.BOLD, 16);
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 13);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(30, 30, 30));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2, 10, 0));
        topPanel.setOpaque(false);

        JPanel heroPanel = createCharacterPanel("英雄", hero, true, labelFont, chineseFontBold);
        JPanel enemyPanel = createCharacterPanel("敌人", currentEnemy, false, labelFont, chineseFontBold);

        topPanel.add(heroPanel);
        topPanel.add(enemyPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JScrollPane logScrollPane = new JScrollPane();
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gameLog.setBackground(new Color(20, 20, 20));
        gameLog.setForeground(new Color(0, 255, 0));
        gameLog.setLineWrap(true);
        gameLog.setWrapStyleWord(true);
        logScrollPane.setViewportView(gameLog);
        logScrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)), 
            "战斗日志", 
            javax.swing.border.TitledBorder.LEFT, 
            javax.swing.border.TitledBorder.TOP,
            titleFont,
            new Color(200, 200, 200)
        ));

        mainPanel.add(logScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout(10, 10));
        controlPanel.setOpaque(false);

        winLabel = new JLabel("胜场: 0", SwingConstants.CENTER);
        winLabel.setFont(titleFont);
        winLabel.setForeground(new Color(255, 215, 0));
        controlPanel.add(winLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        attackBtn = createGameButton("普通攻击", new Color(70, 130, 180), chineseFontBold);
        strongAttackBtn = createGameButton("强力一击 (消耗10HP)", new Color(255, 140, 0), chineseFontBold);
        healBtn = createGameButton("生命汲取 (消耗10HP)", new Color(60, 179, 113), chineseFontBold);
        continueBtn = createGameButton("继续战斗", new Color(138, 43, 226), chineseFontBold);

        attackBtn.addActionListener(e -> playerAction(1));
        strongAttackBtn.addActionListener(e -> playerAction(2));
        healBtn.addActionListener(e -> playerAction(3));
        continueBtn.addActionListener(e -> continueBattle());

        buttonPanel.add(attackBtn);
        buttonPanel.add(strongAttackBtn);
        buttonPanel.add(healBtn);
        buttonPanel.add(continueBtn);

        controlPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        appendLog("╔════════════════════════════════════════════╗\n");
        appendLog("  欢迎来到文字格斗游戏       \n");
        appendLog("╚════════════════════════════════════════════╝\n\n");
        appendLog("角色创建成功！初始属性：\n");
        appendLog(hero.showInfo() + "\n\n");
        appendLog("⚔️ 第" + battleCount + "场战斗开始！！ 对手：" + currentEnemy.name + "！\n\n");

        updateDisplay();
        enableButtons(true);
        continueBtn.setEnabled(false);
    }

    private JPanel createCharacterPanel(String title, com.chen.fighting_game.bean.Character character, boolean isHero, Font labelFont, Font boldFont) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(isHero ? new Color(0, 255, 0) : new Color(255, 0, 0), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(new Color(40, 40, 40));

        JLabel nameLabel = new JLabel(title + ": " + character.name, SwingConstants.CENTER);
        nameLabel.setFont(boldFont);
        nameLabel.setForeground(isHero ? new Color(0, 255, 0) : new Color(255, 100, 100));
        
        if (!isHero) {
            enemyNameLabel = nameLabel;
        }
        
        panel.add(nameLabel, BorderLayout.NORTH);

        if (isHero) {
            heroInfo = new JLabel("", SwingConstants.CENTER);
            heroInfo.setFont(labelFont);
            heroInfo.setForeground(Color.WHITE);
            panel.add(heroInfo, BorderLayout.CENTER);

            heroHPBar = new JLabel("", SwingConstants.CENTER);
            heroHPBar.setFont(new Font("微软雅黑", Font.BOLD, 14));
            heroHPBar.setForeground(new Color(0, 255, 0));
            panel.add(heroHPBar, BorderLayout.SOUTH);
        } else {
            enemyInfo = new JLabel("", SwingConstants.CENTER);
            enemyInfo.setFont(labelFont);
            enemyInfo.setForeground(Color.WHITE);
            panel.add(enemyInfo, BorderLayout.CENTER);

            enemyHPBar = new JLabel("", SwingConstants.CENTER);
            enemyHPBar.setFont(new Font("微软雅黑", Font.BOLD, 14));
            enemyHPBar.setForeground(new Color(255, 100, 100));
            panel.add(enemyHPBar, BorderLayout.SOUTH);
        }

        return panel;
    }

    private JButton createGameButton(String text, Color color, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(180, 45));
        return button;
    }

    private HeroCharacter createHeroCharacter(String playerName) {
        AttributeDialog dialog = new AttributeDialog(this, playerName);
        dialog.setVisible(true);
        
        return dialog.getHero();
    }

    private void startNewBattle() {
        if (wins > 0) {
            for (EnemyCharacter enemy : enemies) {
                enemy.MAXHP += 10;
                enemy.HP = enemy.MAXHP;
                enemy.ATK += 3;
                enemy.DEF += 2;
                enemy.defending = false;
            }
        }

        Random random = new Random();
        int index = random.nextInt(enemies.size());
        currentEnemy = enemies.get(index);
        inBattle = true;
    }

    private void playerAction(int action) {
        if (!inBattle) return;

        enableButtons(false);

        switch (action) {
            case 1:
                int damage1 = calculateDamage(hero.ATK, currentEnemy.DEF);
                appendLog("⚔️ 你使用了普通攻击，对 " + currentEnemy.name + " 造成了 " + damage1 + " 点伤害\n");
                currentEnemy.receiveDamage(damage1);
                break;
            case 2:
                if (hero.HP > 10) {
                    hero.receiveDamage(10);
                    int damage2 = calculateDamage((int)(hero.ATK * 1.8), currentEnemy.DEF);
                    appendLog("💥 消耗10HP，你使用了强力一击，对 " + currentEnemy.name + " 造成了 " + damage2 + " 点伤害\n");
                    currentEnemy.receiveDamage(damage2);
                } else {
                    appendLog("❌ HP不足，无法使用技能！\n");
                    enableButtons(true);
                    continueBtn.setEnabled(false);
                    return;
                }
                break;
            case 3:
                if (hero.HP > 10) {
                    hero.receiveDamage(10);
                    Random random = new Random();
                    int restore = random.nextInt(21);
                    hero.recover(restore);
                    appendLog("⚡ 你使用了生命恢复，恢复了 " + restore + " 点生命\n");
                } else {
                    appendLog("❌ HP不足，无法使用技能！\n");
                    enableButtons(true);
                    continueBtn.setEnabled(false);
                    return;
                }
                break;
        }

        updateDisplay();

        if (!currentEnemy.isAlive()) {
            handleVictory();
        } else {
            Timer timer = new Timer(1000, e -> {
                enemyAction();
                updateDisplay();
                if (!hero.isAlive()) {
                    handleDefeat();
                } else {
                    enableButtons(true);
                    continueBtn.setEnabled(false);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void enemyAction() {
        Random random = new Random();
        String action = "普通攻击";
        int num = random.nextInt(2);
        if (num == 1) {
            action = currentEnemy.skill;
        }

        switch (action) {
            case "普通攻击":
                int damage1 = calculateDamage(currentEnemy.ATK, hero.DEF);
                appendLog("⚔️ " + currentEnemy.name + " 使用了普通攻击，对你造成了 " + damage1 + " 点伤害\n");
                hero.receiveDamage(damage1);
                break;
            case "猛击":
                int damage2 = calculateDamage((int)(currentEnemy.ATK * 1.5), hero.DEF);
                appendLog("🔥 " + currentEnemy.name + " 使用了猛击，对你造成了 " + damage2 + " 点伤害\n");
                hero.receiveDamage(damage2);
                break;
            case "快速攻击":
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                    damage3 += calculateDamage(currentEnemy.ATK, hero.DEF);
                }
                appendLog("⚡ " + currentEnemy.name + " 使用了快速攻击，对你造成了 " + damage3 + " 点伤害\n");
                hero.receiveDamage(damage3);
                break;
            case "防御姿态":
                ((EnemyCharacter)currentEnemy).defending = true;
                appendLog("🛡 " + currentEnemy.name + " 使用了防御姿态\n");
                break;
            case "火球术":
                int damage4 = calculateDamage((int)(currentEnemy.ATK * 1.8), hero.DEF);
                appendLog("🔥 " + currentEnemy.name + " 使用了火球术，对你造成了 " + damage4 +  " 点伤害\n");
                hero.receiveDamage(damage4);
                break;
        }
    }

    private void handleVictory() {
        inBattle = false;
        wins++;
        appendLog("\n🎉 恭喜你，你击败了：" + currentEnemy.name + "！\n");

        Random r = new Random();
        int heal = r.nextInt(21) + 20;
        hero.recover(heal);
        appendLog("💚 恢复 " + heal + " 点生命值\n");

        if (wins % 3 == 0) {
            appendLog("\n⭐ 恭喜！你获得了属性提升！\n");
            hero.MAXHP += 30;
            hero.ATK += 5;
            hero.DEF += 5;
            appendLog("最大生命+30，攻击力+5，防御力+5\n");
        }

        appendLog("\n当前胜场：" + wins + "\n");
        appendLog("--------------------------\n\n");

        updateDisplay();
        enableButtons(false);
        continueBtn.setEnabled(true);
    }

    private void handleDefeat() {
        inBattle = false;
        appendLog("\n☠️ 你被 " + currentEnemy.name + " 击败了！\n");
        appendLog("\n===== 游戏结束 =====\n");
        appendLog("最终属性：\n" + hero.showInfo() + "\n");
        appendLog("总胜场：" + wins + "\n");

        enableButtons(false);
        continueBtn.setEnabled(false);

        int choice = JOptionPane.showConfirmDialog(this,
            "游戏结束！总胜场：" + wins + "\n是否重新开始？",
            "游戏结束",
            JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            new GameFrame(hero.name);
        } else {
            System.exit(0);
        }
    }

    private void continueBattle() {
        battleCount++;
        startNewBattle();
        appendLog("\n⚔️ 第" + battleCount + "场战斗开始！！ 对手：" + currentEnemy.name + "！\n\n");
        updateDisplay();
        enableButtons(true);
        continueBtn.setEnabled(false);
    }

    private void updateDisplay() {
        if (heroInfo != null) {
            heroInfo.setText("<html>HP: " + hero.HP + "/" + hero.MAXHP + 
                           "<br>ATK: " + hero.ATK + 
                           "<br>DEF: " + hero.DEF + "</html>");
        }
        if (enemyInfo != null) {
            enemyInfo.setText("<html>HP: " + currentEnemy.HP + "/" + currentEnemy.MAXHP + 
                            "<br>ATK: " + currentEnemy.ATK + 
                            "<br>DEF: " + currentEnemy.DEF + "</html>");
        }
        if (enemyNameLabel != null) {
            enemyNameLabel.setText("敌人: " + currentEnemy.name);
        }
        if (heroHPBar != null) {
            heroHPBar.setText(createHPBar(hero.HP, hero.MAXHP, 20));
        }
        if (enemyHPBar != null) {
            enemyHPBar.setText(createHPBar(currentEnemy.HP, currentEnemy.MAXHP, 20));
        }
        if (winLabel != null) {
            winLabel.setText("胜场: " + wins + " | 战斗: " + battleCount);
        }
        
        revalidate();
        repaint();
    }

    private String createHPBar(int hp, int maxHp, int length) {
        int trueLength = (int)(hp * 1.0 * length / maxHp);
        StringBuilder sb = new StringBuilder();
        sb.append("【");
        for (int i = 0; i < length; i++) {
            if (i < trueLength) sb.append("█");
            else sb.append(" ");
        }
        sb.append("】 ").append(hp).append("/").append(maxHp).append(" HP");
        return sb.toString();
    }

    private void appendLog(String message) {
        gameLog.append(message);
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    private void enableButtons(boolean enable) {
        attackBtn.setEnabled(enable);
        strongAttackBtn.setEnabled(enable);
        healBtn.setEnabled(enable);
    }

    private int calculateDamage(int atk, int def) {
        int damage = atk - def;
        if (damage < 1) damage = 1;
        return damage;
    }
}
