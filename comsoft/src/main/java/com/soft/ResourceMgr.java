package com.soft;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// 初始化程序的时候加载所有的图片
public class ResourceMgr {
    public static BufferedImage tankL, tankU, tankR, tankD;
    static {
        try {
            tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
            tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
