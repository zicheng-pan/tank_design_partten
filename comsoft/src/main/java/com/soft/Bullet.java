package com.soft;

import java.awt.*;

//子弹
public class Bullet {
    private static final int SPEED = 5;
    private int x, y;
    private Direction dir;
    public static int WIDTH = ResourceMgr.bulletD.getWidth(), HEIGHT = ResourceMgr.bulletD.getHeight();

    Rectangle rect = new Rectangle();

    //用于判断是不是还活着
    private Boolean live = true;

    private TankFrame tf;

    //同样的将子弹也区分好坏
    public Group group = Group.BAD;

    public Bullet(int x, int y, Direction dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        rect.x = this.x;
        rect.y = this.y;
        rect.height = HEIGHT;
        rect.width = WIDTH;
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
        // 让我们的矩形也跟着移动
        rect.x = this.x;
        rect.y = this.y;
        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
            live = false;
    }

    //因为每移动一次，就需要做碰撞检测，并且每个子弹和每个坦克都会产生一个Rectangle对象，所以每次都会产生很多对象，容易触发gc,要是老触发垃圾回收器，就会影响
    //程序执行，打游戏得感觉
    public void collideWith(Tank tank) {
        if (this.group == tank.group)
            return;
        //TODO 用一个Rect来记录子弹的位置，每次都new出来太多了，java越来越多的内存占用，垃圾回收器会占用时间。
//        Rectangle rec1 = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
//        Rectangle rec2 = new Rectangle(tank.getX(), tank.getY(), tank.WIDTH, tank.HEIGHT);
        //直接使用tank的rect 和 子弹的rect就可以了
        if (this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            //在坦克死，子弹死的时候显示爆炸
            int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            tf.explodes.add(new Explode(eX, eY, tf));
        }
    }

    private void die() {
        this.live = false;
    }
}
