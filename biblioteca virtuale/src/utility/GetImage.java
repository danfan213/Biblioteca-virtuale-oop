package utility;

import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.ImageIcon;

public class GetImage {

	public ImageIcon getImageIcon(java.sql.Blob blob) {
		byte[] image = null;
		try {
			image = blob.getBytes(1, (int) blob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Image img = Toolkit.getDefaultToolkit().createImage(image);
		ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage()
				.getScaledInstance(350, 500, Image.SCALE_DEFAULT));

		return icon;
	}

	
	
	public ImageIcon getImageList(ImageIcon image) {
		ImageIcon icon = new ImageIcon(image.getImage().getScaledInstance(200,
				280, Image.SCALE_DEFAULT));

		return icon;
	}

	public ImageIcon getImageAuthor(ImageIcon image) {
		ImageIcon icon = new ImageIcon(image.getImage().getScaledInstance(150,
				200, Image.SCALE_DEFAULT));

		return icon;
	}

	public ImageIcon getImageSingle(ImageIcon image) {
		ImageIcon icon = new ImageIcon(image.getImage().getScaledInstance(350,
				500, Image.SCALE_DEFAULT));

		return icon;
	}
}
