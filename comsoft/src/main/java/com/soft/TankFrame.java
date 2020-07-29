package com.soft;

import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TankFrame extends Frame {
    //这些代码如果现在再多一个坦克出来，那么就需要x1，y1 如果再有一个人来操控坦克，那么就x2，y2.。。。所以我们需要进行封装
//    int x = 200, y = 200;
//    //因为只有一个所以不用static修饰
//    private final int SPEED = 10;
    //封装上面的代码的过程，用起来会比较方便
    Tank myTank = new Tank(200, 200, Direction.DOWN, this);

    //容器中的引用，如果不及时清掉的话，就有内存泄漏的问题。
    List<Bullet> myBullets = new ArrayList<Bullet>();

    //敌人坦克，是一个集合
    List<Tank> tanks = new ArrayList<Tank>();

    static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;

    public TankFrame() {
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("tank war");
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.addKeyListener(new MyKeyListener());
    }

    //窗口在直接调用Frame 的时候就自动的调用这个方法了
    @Override
    public void paint(Graphics g) {
//        System.out.println("paint");
        //如果从tank 这个对象中，把变量一个一个的取出来，这样就又拆开了封装，不好，所以tank本身最知道应该自己处在哪个位置，怎么画，所以使用
        myTank.paint(g);//就像DOG 这个类，有一个方法，抓老鼠，如果把DOG的属性一个一个拿出来就相当于自己帮Dog来实现抓老鼠了，实际上应该Dog自己去抓老鼠
//        g.fillRect(x, y, 50, 50);
//        switch (dir) {
//            case UP:
//                y -= SPEED;
//                break;
//            case DOWN:
//                y += SPEED;
//                break;
//            case LEFT:
//                x -= SPEED;
//                break;
//            case RIGHT:
//                x += SPEED;
//                break;
//            default:
//                break;
//        }
        /*  这种遍历方式用的是集合内部的迭代器，在迭代器迭代的时候，不允许自身容器对数据进行删除，只允许迭代器自己对数据进行删除，用迭代器
        迭代的时候，只能在这个for循环里删除，不能在别的地方对集合类进行删除
        for (Bullet bullet : myBullets) {
            bullet.paint(g);
        }*/
        for (int i = 0; i < myBullets.size(); i++) {
            //也可以在这里判断bullet是不是死了，live = false 那么在这里直接删除也可以
            myBullets.get(i).paint(g);
        }
        //绘画所有的敌人坦克
        for (int i = 0; i < tanks.size(); i++) {
            //也可以在这里判断bullet是不是死了，live = false 那么在这里直接删除也可以
            tanks.get(i).paint(g);
        }
        //使用迭代器的方式来进行删除子弹
        /*        for (Iterator<Bullet> it = myBullets.iterator(); it.hasNext()) {
            Bullet b = it.next();
            if (!b.live) it.remove();
        }
        */
        Color c = g.getColor();
        g.setColor(Color.white);
        String showMessage = "bullet counts:" + myBullets.size();
        g.drawString(showMessage, 10, 60);
        g.setColor(c);
    }

    //使用双缓冲技术来解决刷屏，屏幕闪问题
    Image offScreenImage = null;

    // repaint 会调用update ，update中调用paint ，所以不是直接调用paint
    // 首先把该画出来的东西（坦克，子弹，鲜花在内存的图片上，图片大小和游戏画面一致）
    //把内存中图片一次性画到屏幕上，(内存的内容复制到显存)
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        //这个画笔就是内存中的画笔传给paint方法
        paint(gOffScreen);
        // 这个g 是屏幕上的画笔，将整张屏幕的画笔画整个图片，这样闪烁就消除了，正常游戏开发的时候，这部分是被游戏引擎封装的
        g.drawImage(offScreenImage, 0, 0, null);
    }


    //键盘监听处理类
    private class MyKeyListener extends KeyAdapter {

        // 表示一下键被按下去了
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            //但是这样有问题：我想要斜着走怎么办
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
//            repaint();
            setMainTankDir();

        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                // control 按键，打出一个子弹
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }


        private void setMainTankDir() {
            if (!bL && !bU && !bR && !bD)
                myTank.setMoving(false);
            else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Direction.LEFT);
                if (bU) myTank.setDir(Direction.UP);
                if (bR) myTank.setDir(Direction.RIGHT);
                if (bD) myTank.setDir(Direction.DOWN);
            }
        }
    }

}
