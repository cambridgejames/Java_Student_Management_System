package cn.compscosys.extend.borders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.border.LineBorder;

public class TextBorderUtlis extends LineBorder {
	private static final long serialVersionUID = 81582888533644857L;

	public TextBorderUtlis(Color color, int thickness, boolean roundedCorners) {
		super(color, thickness, roundedCorners);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color oldColor = g.getColor();
		Graphics2D g2 = (Graphics2D) g;
		int i;
		g2.setRenderingHints(rh);
		g2.setColor(lineColor);
		for (i = 0; i < thickness; i++) {
			if (!roundedCorners) {
				g2.drawRect(x + i, y + i, width - i - i - 1, height - i - i - 1);
			} else {
				g2.drawRoundRect(x + i, y + i, width - i - i - 1, height - i - i - 1, 5, 5);
			}
		}
		g2.setColor(oldColor);
	}
}
