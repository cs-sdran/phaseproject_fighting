package com.chen.fighting_game.ui;

import com.chen.fighting_game.bean.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LoginFrame extends JFrame {
    private ArrayList<User> userList;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField codeField;
    private JLabel codeLabel;
    private String currentCode;
    private int loginAttempts;

    public LoginFrame() {
        userList = new ArrayList<>();
        loginAttempts = 0;
        initUI();
    }

    private void initUI() {
        setTitle("文字格斗游戏 - 登录");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Font chineseFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font chineseFontBold = new Font("微软雅黑", Font.BOLD, 14);
        Font titleFont = new Font("微软雅黑", Font.BOLD, 20);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("欢迎来到文字格斗游戏", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(70, 130, 180));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(chineseFont);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        usernameField = new JTextField(15);
        usernameField.setFont(chineseFont);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(chineseFont);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(15);
        passwordField.setFont(chineseFont);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel codeLabel2 = new JLabel("验证码:");
        codeLabel2.setFont(chineseFont);
        formPanel.add(codeLabel2, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPanel codePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        codePanel.setOpaque(false);
        codeField = new JTextField(8);
        codeField.setFont(chineseFont);
        codeLabel = new JLabel();
        codeLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        codeLabel.setForeground(Color.RED);
        generateCode();
        JButton refreshCodeBtn = new JButton("刷新");
        refreshCodeBtn.setFont(chineseFont);
        refreshCodeBtn.addActionListener(e -> generateCode());
        codePanel.add(codeField);
        codePanel.add(codeLabel);
        codePanel.add(refreshCodeBtn);
        formPanel.add(codePanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton loginBtn = new JButton("登录");
        JButton registerBtn = new JButton("注册");
        JButton exitBtn = new JButton("退出");

        styleButton(loginBtn, new Color(70, 130, 180), chineseFontBold);
        styleButton(registerBtn, new Color(60, 179, 113), chineseFontBold);
        styleButton(exitBtn, new Color(220, 20, 60), chineseFontBold);

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> showRegisterDialog());
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(exitBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor, Font font) {
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
    }

    private void generateCode() {
        currentCode = getcode();
        codeLabel.setText(currentCode);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String inputCode = codeField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!inputCode.equalsIgnoreCase(currentCode)) {
            JOptionPane.showMessageDialog(this, "验证码错误，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
            generateCode();
            codeField.setText("");
            return;
        }

        int index = findUserIndex(username);
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "用户名未注册，请先注册！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userList.get(index);
        if (user.getState() == 0) {
            JOptionPane.showMessageDialog(this, "用户 " + username + " 已锁定，请联系客服！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new GameFrame(username);
        } else {
            loginAttempts++;
            if (loginAttempts >= 3) {
                user.setState(0);
                JOptionPane.showMessageDialog(this, "密码错误3次，账户已锁定！", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "密码错误，还剩 " + (3 - loginAttempts) + " 次机会！", 
                    "错误", JOptionPane.ERROR_MESSAGE);
            }
            passwordField.setText("");
            codeField.setText("");
            generateCode();
        }
    }

    private void showRegisterDialog() {
        RegisterDialog dialog = new RegisterDialog(this, userList);
        dialog.setVisible(true);
    }

    private int findUserIndex(String name) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static String getcode() {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(list.get(random.nextInt(list.size())));
        }
        sb.append(random.nextInt(10));
        char[] c = sb.toString().toCharArray();
        int index = random.nextInt(sb.length() - 1);
        char temp = c[index];
        c[index] = c[sb.length() - 1];
        c[sb.length() - 1] = temp;
        return new String(c);
    }
}
