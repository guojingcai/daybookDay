package com.sailing.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Hello world!
 *
 */
public class Pdf2Img {

	public static void main(String[] args) {
		// 将pdf装图片 并且自定义图片得格式大小
		File file = new File("E:\\workALL\\java\\content.pdf");
		try {
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			System.out.println(pageCount);
			for (int i = 0; i < pageCount; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, 196); // Windows
				BufferedImage srcImage = resize(image, 1000, 1240);// 产生缩略图
				File file2 = new File("E:\\workALL\\java\\content\\com" + System.currentTimeMillis() + ".png");
				if (file2.exists()) {
					System.out.println("file exists");
				} else {
					System.out.println("file not exists, create it ...");
					try {
						//file2.createNewFile();
						ImageIO.write(srcImage, "PNG", file2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
}
