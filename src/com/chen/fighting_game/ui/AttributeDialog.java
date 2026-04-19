package com.chen.fighting_game.ui;

import com.chen.fighting_game.bean.HeroCharacter;

import javax.swing.*;
import java.awt.*;

public class AttributeDialog extends JDialog {
    private HeroCharacter hero;
    private JTextField hpField;
    private JTextField atkField;
    private JTextField defField;
    private JLabel remainLabel;
    private int remainingPoints;

    public AttributeDialog(Frame parent, String playerName) {
        super(parent, "分配属性点", true);
        remainingPoints = 20;
        initUI(playerName);
    }

    private void initUI(String playerName) {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);

        Font chineseFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font chineseFontBold = new Font("微软雅黑", Font.BOLD, 14);
        Font titleFont = new Font("微软雅黑", Font.BOLD, 18);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("创建角色 - " + playerName, SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(70, 130, 180));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createTitledBorder("基础属性"));

        JLabel baseInfo = new JLabel("<html>基础属性：<br>HP: 100 (每点+10)<br>ATK: 10 (每点+2)<br>DEF: 0 (每点+1)</html>");
        baseInfo.setFont(chineseFont);
        baseInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(baseInfo);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel hpLabel = new JLabel("生命值点数:");
        hpLabel.setFont(chineseFont);
        formPanel.add(hpLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        hpField = new JTextField(5);
        hpField.setFont(chineseFont);
        hpField.setText("0");
        formPanel.add(hpField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel hpPreview = new JLabel("(+0 HP)");
        hpPreview.setFont(chineseFont);
        hpPreview.setForeground(new Color(0, 150, 0));
        formPanel.add(hpPreview, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel atkLabel = new JLabel("攻击力点数:");
        atkLabel.setFont(chineseFont);
        formPanel.add(atkLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        atkField = new JTextField(5);
        atkField.setFont(chineseFont);
        atkField.setText("0");
        formPanel.add(atkField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel atkPreview = new JLabel("(+0 ATK)");
        atkPreview.setFont(chineseFont);
        atkPreview.setForeground(new Color(200, 100, 0));
        formPanel.add(atkPreview, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel defLabel = new JLabel("防御力点数:");
        defLabel.setFont(chineseFont);
        formPanel.add(defLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        defField = new JTextField(5);
        defField.setFont(chineseFont);
        defField.setText("0");
        formPanel.add(defField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel defPreview = new JLabel("(+0 DEF)");
        defPreview.setFont(chineseFont);
        defPreview.setForeground(new Color(0, 100, 200));
        formPanel.add(defPreview, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        remainLabel = new JLabel("剩余点数: 20", SwingConstants.CENTER);
        remainLabel.setFont(chineseFontBold);
        remainLabel.setForeground(new Color(220, 20, 60));
        formPanel.add(remainLabel, gbc);

        mainPanel.add(formPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton confirmBtn = new JButton("确认创建");
        JButton randomBtn = new JButton("随机分配");

        confirmBtn.setFont(chineseFontBold);
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBackground(new Color(60, 179, 113));
        confirmBtn.setFocusPainted(false);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setPreferredSize(new Dimension(120, 35));

        randomBtn.setFont(chineseFontBold);
        randomBtn.setForeground(Color.WHITE);
        randomBtn.setBackground(new Color(70, 130, 180));
        randomBtn.setFocusPainted(false);
        randomBtn.setBorderPainted(false);
        randomBtn.setPreferredSize(new Dimension(120, 35));

        hpField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void updatePreview() {
                try {
                    int hp = Integer.parseInt(hpField.getText());
                    hpPreview.setText("(+" + (hp * 10) + " HP)");
                } catch (NumberFormatException e) {
                    hpPreview.setText("(+0 HP)");
                }
                updateRemaining();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
        });

        atkField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void updatePreview() {
                try {
                    int atk = Integer.parseInt(atkField.getText());
                    atkPreview.setText("(+" + (atk * 2) + " ATK)");
                } catch (NumberFormatException e) {
                    atkPreview.setText("(+0 ATK)");
                }
                updateRemaining();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
        });

        defField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void updatePreview() {
                try {
                    int def = Integer.parseInt(defField.getText());
                    defPreview.setText("(+" + def + " DEF)");
                } catch (NumberFormatException e) {
                    defPreview.setText("(+0 DEF)");
                }
                updateRemaining();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
        });

        confirmBtn.addActionListener(e -> {
            try {
                int hp = Integer.parseInt(hpField.getText());
                int atk = Integer.parseInt(atkField.getText());
                int def = Integer.parseInt(defField.getText());

                if (hp < 0 || atk < 0 || def < 0) {
                    JOptionPane.showMessageDialog(this, "属性点数不能为负数！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (hp + atk + def != 20) {
                    JOptionPane.showMessageDialog(this, "总点数必须等于20！\n当前总点数: " + (hp + atk + def), "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                hero = new HeroCharacter(playerName, 100 + hp * 10, 10 + atk * 2, 0 + def);
                hero.skills.add("普通攻击");
                hero.skills.add("强力一击");
                hero.skills.add("生命回复");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        randomBtn.addActionListener(e -> {
            java.util.Random random = new java.util.Random();
            int hp = random.nextInt(8) + 4;
            int atk = random.nextInt(8) + 4;
            int def = 20 - hp - atk;
            if (def < 0) {
                def = 0;
                int excess = hp + atk - 20;
                if (hp > atk) {
                    hp -= excess;
                } else {
                    atk -= excess;
                }
            }
            hpField.setText(String.valueOf(hp));
            atkField.setText(String.valueOf(atk));
            defField.setText(String.valueOf(def));
            updateRemaining();
        });

        buttonPanel.add(confirmBtn);
        buttonPanel.add(randomBtn);

        add(mainPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateRemaining() {
        try {
            int hp = Integer.parseInt(hpField.getText());
            int atk = Integer.parseInt(atkField.getText());
            int def = Integer.parseInt(defField.getText());
            remainingPoints = 20 - (hp + atk + def);
            remainLabel.setText("剩余点数: " + remainingPoints);
            if (remainingPoints < 0) {
                remainLabel.setForeground(new Color(220, 20, 60));
            } else if (remainingPoints == 0) {
                remainLabel.setForeground(new Color(0, 150, 0));
            } else {
                remainLabel.setForeground(new Color(70, 130, 180));
            }
        } catch (NumberFormatException e) {
            remainLabel.setText("剩余点数: 20");
        }
    }

    public HeroCharacter getHero() {
        return hero;
    }
}

