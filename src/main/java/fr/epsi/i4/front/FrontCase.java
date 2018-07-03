package fr.epsi.i4.front;

import fr.epsi.i4.back.model.board.Case;
import fr.epsi.i4.back.model.board.content.Content;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FrontCase extends JPanel {

	private Case aCase;

	public FrontCase(Case c) {
		this.aCase = c;

		setLayout(new OverlayLayout(this));
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		refresh(c);
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
//			e.printStackTrace();
		}
		return imageIcon;
	}

	public Case getaCase() {
		return aCase;
	}

	public void refresh(Case c) {
		removeAll();
		File file;
		JLabel label;
		Content content;
		FrontContent frontContent;
		Content[] contents = c.getContents();
		for (int i = 0; i < contents.length; i++) {
			content = contents[i];
			if (content != null) {
				frontContent = FrontContent.getFrontContentByName(content.toString());
				file = frontContent.getFile();
				if (file != null) {
					label = new JLabel(getImageIcon(file));
				} else {
					label = new JLabel(content.toString());
				}
//				if (content.equals(Content.AGENT) || content.equals(Content.WALL)) {
				add(label);
//				}
			}
		}
		label = new JLabel(Integer.toString(c.getWeight().getWeight()));
		label.setForeground(Color.RED);
		label.setFont(new Font("Serif", Font.BOLD, 20));
//		add(label);
	}
}
