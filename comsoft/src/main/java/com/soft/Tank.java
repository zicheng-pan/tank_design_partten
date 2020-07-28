package com.soft;

import java.awt.*;

public class Tank {
    private int x, y;
    private Direction dir = Direction.DOWN;
    private final int SPEED = 5;
    //moving = true 的时候，才进行移动+-
    private boolean moving = false;

    //因为只有TankFrame才可以paint 所以我们需要TankFrame
    private TankFrame tf = null;

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    public Tank(int x, int y, Direction dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.yellow);
        g.fillRect(x, y, 50, 50);
        move();
        g.setColor(c);
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
    }


    public void fire() {
        //如果子弹不及时的清除掉，就会有内存泄漏的问题
        tf.myBullets.add(new Bullet(this.x, this.y, this.dir, this.tf));
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
}
