package com.jfcore.tools;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

 

public class ImageCut {
	
	/***
	 * 矩形裁剪，设定起始位置，裁剪宽度，裁剪长度* 裁剪范围需小于等于图像范围* @param image* @param
	 * xCoordinate* @param yCoordinate* @param xLength* @param yLength* @return
	 */
	public BufferedImage imageCutByRectangle(BufferedImage image, int xCoordinate, int yCoordinate, int xLength,
			int yLength) {
		// 判断x、y方向是否超过图像最大范围
		if ((xCoordinate + xLength) >= image.getWidth()) {
			xLength = image.getWidth() - xCoordinate;
		}
		if ((yCoordinate + yLength) >= image.getHeight()) {
			yLength = image.getHeight() - yCoordinate;
		}
		BufferedImage resultImage = new BufferedImage(xLength, yLength, image.getType());
		for (int x = 0; x < xLength; x++) {
			for (int y = 0; y < yLength; y++) {
				int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
				resultImage.setRGB(x, y, rgb);
			}
		}
		return resultImage;
	}

	/***
	 * 圆形裁剪，定义圆心坐标，半径* 裁剪半径可以输入任意大于零的正整数* @param image* @param xCoordinate* @param
	 * yCoordinate* @param radius* @return
	 */
	public BufferedImage imageCutByCircle(BufferedImage image, int xCoordinate, int yCoordinate, int radius) {
		// 判断圆心左右半径是否超限
		if ((xCoordinate + radius) > image.getWidth() || radius > xCoordinate) {
			int a = image.getWidth() - 1 - xCoordinate;
			if (a > xCoordinate) {
				radius = xCoordinate;
			} else {
				radius = a;
			}
		}
		// 判断圆心上下半径是否超限
		if ((yCoordinate + radius) > image.getHeight() || radius > yCoordinate) {
			int a = image.getHeight() - 1 - yCoordinate;
			if (a > yCoordinate) {
				radius = yCoordinate;
			} else {
				radius = a;
			}
		}
		int length = 2 * radius + 1;
		BufferedImage resultImage = new BufferedImage(length, length, image.getType());
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				int x = i - radius;
				int y = j - radius;
				int distance = (int) Math.sqrt(x * x + y * y);
				if (distance <= radius) {
					int rgb = image.getRGB(x + xCoordinate, y + yCoordinate);
					resultImage.setRGB(i, j, rgb);
				}
			}
		}

		return resultImage;
	}

	public static void main(String[] args) throws Exception {
		
		String imgpath = "D:\\tmp\\11.jpg";
		File input = new File(imgpath);
		 
		BufferedImage image = ImageIO.read(input);
		BufferedImage result = new ImageCut().imageCutByRectangle(image, 78, 766, 1162,932);
		File output = new File("D:\\tmp\\22.jpg");
		ImageIO.write(result, "jpg", output);
	}


}
