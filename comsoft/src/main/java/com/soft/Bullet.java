package com.soft;

import java.awt.*;

//子弹
public class Bullet {
    private static final int SPEED = 5;
    private int x, y;
    private Direction dir;
    public static int WIDTH = ResourceMgr.bulletD.getWidth(), HEIGHT = ResourceMgr.bulletD.getHeight();

    //用于判断是不是还活着
    private Boolean live = true;

    private TankFrame tf;

    public Bullet(int x, int y, Direction dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {

        if (!live) {
            //这样的处理方式有bug的  Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException
            tf.myBullets.remove(this);
        }
//        Color c = g.getColor();
//        g.setColor(Color.RED);
//        g.fillOval(x, y, WIDTH, HEIGHT);
        switch (dir) {
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;

        }
        move();
    }

    private void move() {
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

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
            live = false;
    }

    public void collideWith(Tank tank) {
        Rectangle rec1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
        Rectangle rec2 = new Rectangle(tank.getX(), tank.getY(), tank.WIDTH, tank.HEIGHT);
        if (rec1.intersects(rec2)) {
            tank.die();
            this.die();
        }
    }

    private void die() {
        this.live = false;
    }
}
