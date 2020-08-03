package com.soft;

import java.awt.*;
import java.util.Random;

public class Tank {
    private int x, y;
    private Direction dir = Direction.DOWN;
    private final int SPEED = 3;
    //moving = true 的时候，才进行移动+-
    private boolean moving = true;
    private Random random = new Random();

    Rectangle rect = new Rectangle();

    //默认是不好的
    public Group group = Group.BAD;

    private boolean live = true;

    public static int HEIGHT = ResourceMgr.goodtankD.getHeight();
    public static int WIDTH = ResourceMgr.goodtankD.getWidth();
    //因为只有TankFrame才可以paint 所以我们需要TankFrame
    private TankFrame tf = null;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    public Tank(int x, int y, Direction dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        rect.x = this.x;
        rect.y = this.y;
        rect.height = HEIGHT;
        rect.width = WIDTH;
    }

    public void paint(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.yellow);
//        g.fillRect(x, y, 50, 50);
        if (!live) {
            this.tf.tanks.remove(this);
//            return;
        }

        switch (dir) {
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankU : ResourceMgr.badtankU, x, y, null);
                break;
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankL : ResourceMgr.badtankL, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankD : ResourceMgr.badtankD, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodtankR : ResourceMgr.badtankR, x, y, null);
                break;

        }
        move();
//        g.setColor(c);
    }

    private void move() {
        if (moving) {
            switch (dir) {
                case UP:
                    y -= SPEED;
                    break;
                case DOWN:
                    y += SPEED;
                    break;
                case LEFT:
                    x -= SPEED;
                    break;
                case RIGHT:
                    x += SPEED;
                    break;
                default:
                    break;
            }
        }
        if (Group.BAD == this.group && random.nextInt(100) > 95) {
            this.fire();
        }
        //加上个判断不然敌方坦克，一直在鬼畜
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            //每次move的时候随机给一个方向
            randomDir();
        }

        boudsCheck();
        // 让我们的矩形也跟着移动
        rect.x = this.x;
        rect.y = this.y;

    }

    // 坦克的边界检测
    private void boudsCheck() {
        if (this.x < 0) x = 0;
        if (this.y < 30) y = 30;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH) this.x = TankFrame.GAME_WIDTH - Tank.WIDTH;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT) this.y = TankFrame.GAME_HEIGHT - Tank.HEIGHT;
    }

    private void randomDir() {
        //enum . values 就可以获取数组，通过下标就可以拿去值
        this.dir = Direction.values()[random.nextInt(4)];
    }


    public void fire() {
        // 计算子弹的位置
        int bx = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        //如果子弹不及时的清除掉，就会有内存泄漏的问题
        tf.myBullets.add(new Bullet(bx, by, this.dir, this.group, this.tf));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public int getSPEED() {
        return SPEED;
    }

    public void die() {
        this.live = false;
    }
}
