package com.test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class ImageTest {
    @Test
    public void test() {
//        fail("Not yet implemented!!");
//        System.out.println("aaaaaaaaaaa");
        try {
            //获取相对路径上的文件 load到内存，注意image一定要放在src目录下，因为classLoader load classpath下的文件
            BufferedImage images = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(images);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
