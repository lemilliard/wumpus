package fr.epsi.i4.front;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import fr.epsi.i4.back.model.board.content.Content;
import fr.epsi.i4.back.model.board.Case;

public class FrontCase extends JPanel {

	public FrontCase(Case c) {
		setLayout(new OverlayLayout(this));
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		Content[] contents = c.getContents();
		for (int i = 0; i < contents.length; i++) {
			Content content = contents[i];
			if (content != null) {
				FrontContent frontContent = FrontContent.getFrontContentByName(content.toString());
				File file = frontContent.getFile();
				JLabel label;
				if (file != null) {
					label = new JLabel(getImageIcon(file));
				} else {
					label = new JLabel(content.toString());
				}
				add(label);
			}
		}

		JLabel label = new JLabel(Integer.toString(c.getWeight().getWeight()));
		add(label);

		validate();
		repaint();
	}

	private ImageIcon getImageIcon(File file) {
		ImageIcon imageIcon = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			imageIcon = new ImageIcon(bufferedImage);
			Image image = imageIcon.getImage();
			Image scaledImage = image.getScaledInstance(Game.caseSize, Game.caseSize, Image.SCALE_SMOOTH);
			imageIcon.setImage(scaledImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageIcon;
	}
}
