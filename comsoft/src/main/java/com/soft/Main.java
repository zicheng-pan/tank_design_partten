package com.soft;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        for (int i = 0; i < 5; i++) {
            tankFrame.tanks.add(new Tank(50 + i * 50, 200, Direction.DOWN, Group.BAD, tankFrame));
        }
        while (true) {
            Thread.sleep(50);
            tankFrame.repaint();
        }
    }
}
