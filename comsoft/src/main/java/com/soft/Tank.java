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

    //默认是不好的
    public Group group = Group.BAD;

    private boolean live = true;

    public static int HEIGHT = ResourceMgr.tankD.getHeight();
    public static int WIDTH = ResourceMgr.tankD.getWidth();
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
                g.drawImage(ResourceMgr.tankU, x, y, null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.tankL, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR, x, y, null);
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
        if (random.nextInt(10) > 8) {
            this.fire();
        }
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
