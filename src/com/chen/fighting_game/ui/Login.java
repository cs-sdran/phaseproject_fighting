package com.chen.fighting_game.ui;

import com.chen.fighting_game.bean.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
   public void start()
   {
       //集合要定义在循环外面
       ArrayList<User> list = new ArrayList<>();



       while (true) {
           System.out.println("╔════════════════════════════════╗");
           System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
           System.out.println("╚════════════════════════════════╝");
           System.out.println("请选择操作：1登录 2注册 3退出");

           Scanner sc = new Scanner(System.in);
           String choose = sc.next();
           switch (choose) {
               case "1" -> Login(list);
               case "2" -> register(list);
               case "3" -> {
                   System.out.println("用户选择了退出操作");
                   System.exit(0);
               }
               default -> System.out.println("输入错误，请重新输入");
           }
       }
   }

   //登录的方法
    public void Login(ArrayList<User> list){
        System.out.println("登录操作");
 /*2.2 登录功能：

        1. 键盘录入用户名
        2. 键盘录入密码
        3. 键盘录入验证码
        4. 登录最多重试三次，三次错误账号锁定

        验证要求：
	    用户名如果未注册提示：用户名未注册，请先注册
	    用户被锁定提示：用户xxx已经锁定，请联系官方客服：XXX-XXXXX
	    验证码错误提示：验证码输入错误，请重新输入，并生成一个新的验证码
	    判断用户名和密码是否正确，有3次机会，满3次账户锁定。*/

            System.out.println("请输入用户名");
            Scanner scanner = new Scanner(System.in);
            String name=scanner.next();
            if(!checkNameExists(name,list))
            {  System.out.println(name+"  该用户尚未注册");
            return;}

            int index=findindex(list, name);
            User u=list.get(index);
            if(u.getState()==0)
            { System.out.println("该用户"+name+"已被锁定，请联系管理员");
            return;}

            //继续录入密码和验证码


        //继续判断密码是否正确,共有三次机会
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码");
            String passwd=scanner.next();

            while (true) {
                //输入验证码
                String rightcode=getcode();
                System.out.println("正确的验证码为："+rightcode+"  请输入验证码");


                String inputcode=scanner.next();
                if(rightcode.equalsIgnoreCase(inputcode))
                {
                    System.out.println("验证码输入正确");
                    break;
                }
                else {
                    System.out.println("验证码输入错误，请重新输入");
                    continue;
                }
            }//验证验证码

            if(list.get(index).getPassword().equals(passwd)) {
                System.out.println("密码输入正确,登录成功！！！");
                game game = new game();
                game.gameStart(name);
                break;
            } else {
                if(i==2)//锁定账户
                {
                    u.setState(0);
                    System.out.println("该用户已被锁定");
                }
                else {
                    System.out.println("密码输入错误，请重新输入，您还剩" + (2 - i) + "次机会");
                }

            }

        }


    }

    //通过用户名获取用户
    public int findindex(ArrayList<User> list,String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
   }

    //注册的方法
    public void register(ArrayList<User> list){

        User user = new User();


        String name= null;
        //校验用户名
        while (true) {
            System.out.println("请输入用户名");
            Scanner sc = new Scanner(System.in);
            name = sc.next();

            //判断用户名长度
            if (!checklen(name, 3, 16)) {
                System.out.println("用户名长度必须在3-16位");
                continue;
            }

            //判断是否合法
            if(!checkname(name))
            {
                System.out.println("用户名只能由字母、数字组成，不能是纯数字");
                continue;
            }



            //判断用户名是否已经存在
            if(checkNameExists(name,list))
            { System.out.println("用户名已存在");
                continue;
            }

            //表示内容符合要求且唯一
            user.setName(name);
            break;

        }

        //校验密码
        while(true){
            System.out.println("请输入密码");
            Scanner scanner = new Scanner(System.in);
            String password = scanner.next();
            System.out.println("请再次输入密码");
            String password2=scanner.next();
            //判断长度是否符合要求
            if(!checklen(password,3,8)) {
                System.out.println("密码长度必须在3-8位");
                continue;
            }

            //判断密码是否只能是字母加数字的组合，不能有其他字母
            if(!checkpassword(password)) {
                System.out.println("密码只能是字母加数字的组合，不能有其他字母");
                continue;
            }

            //校验两次密码输入是否一致
            if(!password.equals(password2))
            { System.out.println("两次输入的密码不一致");
                continue;}

            user.setPassword(password);
            break;
        }

        user.setState(1);
        list.add(user);
        System.out.println("用户："+user.getName()+"注册成功");
   }

    //判断用户名长度和密码长度是否符合要求的方法
    public boolean checklen(String name,int minlen,int maxlen) {

        return !(name.length() < minlen || name.length() > maxlen);
    }

    //判断用户名是否为字母与数字结合或者为纯数字的方法
    public boolean checkname(String name) {//使用正则表达式

        return name!=null&&name.matches("^[a-zA-Z0-9]*[a-zA-Z][A-Za-z0-9]*$");
    }

    //判断用户名是否存在的方法
    public boolean checkNameExists(String name, ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //判断密码只能是字母加数字的组合，不能有其他字符
    public boolean checkpassword(String password) {//使用正则表达式


        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$");
    }

    //获取验证码
    public static String getcode()
    {
       /* 长度为5
	    由4位大写或者小写字母和1位数字组成，同一个字母可重复
	    数字可以出现在任意位置
        比如：aQa1K*/
        ArrayList<Character> list = new ArrayList<>(); //定义一个集合存储所有大小写字母
        for (int i = 0; i < 26; i++) {
            list.add((char) ('a' + i));
            list.add((char) ('A' + i));
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();//创建一个StringBuilder对象存储验证码的四个字母
        for (int i = 0; i < 4; i++) {
            sb.append(list.get(random.nextInt(list.size())));
        }
        /*System.out.println(sb.toString()); //输出验证码的四个字母测试*/

        sb.append(random.nextInt(10)); //在验证码的末尾添加一个数字
        //对数字的位置进行随机放置
        char[] c=sb.toString().toCharArray();
        int index=random.nextInt(sb.length()-1);//获取随机索引

        char temp=c[index];
        c[index]=c[sb.length()-1];
        c[sb.length()-1]=temp;

        /*System.out.println(new String(c));*/ //测试打印
        return new String(c);

    }

}
