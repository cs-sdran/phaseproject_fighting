package com.chen.fighting_game.ui;

import com.chen.fighting_game.bean.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RegisterDialog extends JDialog {
    private ArrayList<User> userList;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterDialog(Frame parent, ArrayList<User> userList) {
        super(parent, "用户注册", true);
        this.userList = userList;
        initUI();
    }

    private void initUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);

        Font chineseFont = new Font("微软雅黑", Font.PLAIN, 14);
        Font chineseFontBold = new Font("微软雅黑", Font.BOLD, 14);
        Font titleFont = new Font("微软雅黑", Font.BOLD, 18);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("新用户注册", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(60, 179, 113));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
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
        JLabel confirmLabel = new JLabel("确认密码:");
        confirmLabel.setFont(chineseFont);
        formPanel.add(confirmLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setFont(chineseFont);
        formPanel.add(confirmPasswordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton registerBtn = new JButton("注册");
        JButton cancelBtn = new JButton("取消");

        registerBtn.setFont(chineseFontBold);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBackground(new Color(60, 179, 113));
        registerBtn.setFocusPainted(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setPreferredSize(new Dimension(100, 35));

        cancelBtn.setFont(chineseFontBold);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(new Color(128, 128, 128));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setPreferredSize(new Dimension(100, 35));

        registerBtn.addActionListener(e -> handleRegister());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(registerBtn);
        buttonPanel.add(cancelBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.length() < 3 || username.length() > 16) {
            JOptionPane.showMessageDialog(this, "Username must be 3-16 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!checkUsername(username)) {
            JOptionPane.showMessageDialog(this, "Username can only contain letters and numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (checkNameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 3 || password.length() > 8) {
            JOptionPane.showMessageDialog(this, "Password must be 3-8 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!checkPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must contain both letters and numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(username, password);
        userList.add(newUser);

        JOptionPane.showMessageDialog(this, 
            "Registration successful!\nUsername: " + username + "\nUser ID: " + newUser.getId(), 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private boolean checkUsername(String name) {
        int charCount = 0;
        int otherCount = 0;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    charCount++;
                }
            } else {
                otherCount++;
            }
        }
        return charCount > 0 && otherCount == 0;
    }

    private boolean checkPassword(String password) {
        int charCount = 0;
        int numCount = 0;
        int otherCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                charCount++;
            } else if (c >= '0' && c <= '9') {
                numCount++;
            } else {
                otherCount++;
            }
        }
        return charCount > 0 && numCount > 0 && otherCount == 0;
    }

    private boolean checkNameExists(String name) {
        for (User user : userList) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
