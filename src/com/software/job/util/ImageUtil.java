package com.software.job.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static void toSmall(File srcFile,File result,int width,int height) {
		try {
			BufferedImage src=ImageIO.read(srcFile);
			Image image=src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage tag=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics g=tag.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
			ImageIO.write(tag, "GIF", result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
