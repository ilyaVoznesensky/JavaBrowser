package res;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Paint {

	public void paint(Graphics paintt) {
		ImageIcon i11 = new ImageIcon(getClass().getResource("Banner.jpg"));
		Image im11 = i11.getImage();
		paintt.drawImage(im11, 79, 85, 468, 200, null);
	}

}
