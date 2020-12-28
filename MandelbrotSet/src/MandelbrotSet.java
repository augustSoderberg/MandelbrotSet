import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;

/**
 * This class allows the user to generate a custom color Mandlebrot Set fractal and save the image
 * to their computer.
 * @author August
 *
 */

public class MandelbrotSet extends JFrame{
	
	public static final double CANVAS_WIDTH = 3840;
	public static final double CANVAS_HEIGHT = 2160;
	public static final double X_MIN = -2.2;
	public static final double X_MAX = 1.35;
	public static final double Y_MIN = -1;
	public static final double Y_MAX = 1;
	
	public MandelbrotSet() throws HeadlessException, IOException {
		setSize((int) CANVAS_WIDTH, (int) CANVAS_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		DrawArea dA = new DrawArea();
		setContentPane(dA);
		setVisible(true);
		//Requires a key press to save image so that the user knows the full image was generated
		Scanner sc = new Scanner(System.in);
		sc.next();
		System.out.println("Printing...");
		BufferedImage bi = ScreenImage.createImage(dA);
		//Set name here
		ScreenImage.writeImage(bi, "M.png");
	}
	
	public static void main(String[] args) throws HeadlessException, IOException {
		new MandelbrotSet();
	}
		
}

class DrawArea extends JPanel {
	
	private int blowUpValue;
	private int iterations;
	
	public DrawArea() {
		blowUpValue = 2;
		iterations = 70;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.getHSBColor(0, 0, 0));
		for (int i = 0; i <= MandelbrotSet.CANVAS_WIDTH; i++) {
			for (int j = 0; j <= MandelbrotSet.CANVAS_HEIGHT; j++) {
				double variable = map(
						getIterations(
								map(
										i, 0, MandelbrotSet.CANVAS_WIDTH, 
										MandelbrotSet.X_MIN, MandelbrotSet.X_MAX
								), 
								map(
										j, 0, MandelbrotSet.CANVAS_HEIGHT, MandelbrotSet.Y_MIN, 
										MandelbrotSet.Y_MAX
								)
						), 
						0, iterations, 1, 0);
				//This is where you set the color scheme
				g.setColor(Color.getHSBColor((float) variable, (float) variable, (float) .8));
				g.drawLine(i, j, i, j);
			}
		}
	}
	
	private double map(double val, double oMin, double oMax, double fMin, double fMax) {
		return ((val - oMin) / (oMax - oMin) * (fMax - fMin) + fMin);
	}
	
	private int getIterations(double cx, double cy) {
		double x = 0;
		double y = 0;
		for (int i = 0; i < this.iterations; i++) {
			if (norm(x, y) > 2) {
				return i;
			}
			double tempx = x*x - y*y + cx;
			y = 2*x*y + cy;
			x = tempx;
		}
		return iterations;
	}
	
	private double norm(double x, double y) {
		return Math.sqrt(x*x + y*y);
	}
	
}
