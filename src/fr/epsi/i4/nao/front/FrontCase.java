package fr.epsi.i4.nao.front;

import java.awt.*;
import javax.swing.*;

import fr.epsi.i4.nao.back.model.board.Case;
import fr.epsi.i4.nao.back.model.board.content.Content;

public class FrontCase extends JPanel {

	public FrontCase(Case c) {
		setLayout(new OverlayLayout(this));
		setOpaque(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		Content[] contents = c.getContents();
		for (int i = 0; i < contents.length; i++) {
			Content content = contents[i];
			if (content != null) {
				JLabel label = new JLabel(content.toString());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				label.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
				label.setOpaque(false);
				add(label);
			}
		}
		validate();
		repaint();
	}
}
