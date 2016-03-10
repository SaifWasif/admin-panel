import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Rgb2GrayAndChecking {
	private static BufferedImage image;

	public static void convertingImageIntoGrayScale() {
		try {
			image = ImageIO.read(new File(
					"D:\\SAIF\\OPEN CV Eg Images\\s\\try_1.jpg"));
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static boolean checking(int x_coordinate, int y_coordinate) {
		if (image.getData().getSample(x_coordinate, y_coordinate, 0) == 255) {
			return true;
		} else {
			return false;
		}
	}
}