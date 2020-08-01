package com.soft;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// 初始化程序的时候加载所有的图片
public class ResourceMgr {
    public static BufferedImage goodtankL, goodtankU, goodtankR, goodtankD;
    public static BufferedImage badtankL, badtankU, badtankR, badtankD;
    public static BufferedImage bulletL, bulletU, bulletR, bulletD;

    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
//            tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
//            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
//            tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
//            tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            //使用了更好看的图片
            goodtankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodtankL = ImageUtil.rotateImage(goodtankU, -90);
            goodtankR = ImageUtil.rotateImage(goodtankU, 90);

            goodtankD = ImageUtil.rotateImage(goodtankU, 180);

            badtankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badtankL = ImageUtil.rotateImage(badtankU, -90);
            badtankR = ImageUtil.rotateImage(badtankU, 90);

            badtankD = ImageUtil.rotateImage(badtankU, 180);

            bulletL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
            bulletD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
