package cn.compscosys.extend.balloontip;   
   
import java.awt.Color;   
import java.awt.Component;   
import java.awt.Dimension;   
import java.awt.Graphics;   
import java.awt.Insets;   
import java.awt.Point;   
   
import javax.swing.border.Border;

import cn.compscosys.extend.balloontip.BalloonTip.Alignment;
import cn.compscosys.extend.balloontip.utils.FlipUtils;   
   
/**  
 * @author Bernhard Pauler  
 */   
public class RoundedBalloonBorder implements Border {   
   
    private final Alignment alignment;   
    private final int hOffset;   
    private final int vOffset;   
    private final int arcWidth;   
    private final int arcHeight;   
    private final Color fillColor;   
    private final Color borderColor;   
   
    Dimension lastSize;   
    Insets insets = new Insets(0, 0, 0, 0);   
   
    public RoundedBalloonBorder(Alignment alignment, int hOffset, int vOffset, int arcWidth, int arcHeight, Color borderColor, Color fillColor) {   
        this.alignment = alignment;   
        this.hOffset = hOffset;   
        this.vOffset = vOffset;   
        this.arcWidth = arcWidth;   
        this.arcHeight = arcHeight;   
        this.borderColor = borderColor;   
        this.fillColor = fillColor;   
    }   
   
    public Insets getBorderInsets(Component c) {   
        Dimension currentSize = c.getSize();   
   
        if (currentSize.equals(lastSize)) {   
            return insets;   
        }   
   
        switch (alignment) {   
            case LEFT_ALIGNED_ABOVE:   
            case RIGHT_ALIGNED_ABOVE:   
                insets = new Insets(arcHeight, arcWidth, arcHeight+vOffset, arcWidth);   
                break;   
            case LEFT_ALIGNED_BELOW:   
            case RIGHT_ALIGNED_BELOW:   
                insets = new Insets(vOffset+arcHeight, arcWidth, arcHeight, arcWidth);   
                break;   
        }   
   
        lastSize = currentSize;   
   
        return insets;   
    }   
   
    public boolean isBorderOpaque() {   
        return true;   
    }   
   
    public void paintBorder(Component c, Graphics g, int x, int y, int bWidth, int bHeight) {   
        int rectY = y;   
        if (alignment == Alignment.LEFT_ALIGNED_BELOW ||   
            alignment == Alignment.RIGHT_ALIGNED_BELOW) {   
            rectY = y + vOffset;   
        }   
        g.setColor(fillColor);   
        g.fillRoundRect(x, rectY, bWidth, bHeight-vOffset, arcWidth*2, arcHeight*2);   
        g.setColor(borderColor);   
        g.drawRoundRect(x, rectY, bWidth-1, bHeight-vOffset-1, arcWidth*2, arcHeight*2);   
   
        int[] triangleX = {x+hOffset, x+hOffset+vOffset, x+hOffset};   
        int[] triangleY = {y+bHeight-vOffset-1, y+bHeight-vOffset-1, y+bHeight-1};   
   
        if (alignment == Alignment.LEFT_ALIGNED_BELOW ||   
            alignment == Alignment.RIGHT_ALIGNED_BELOW) {   
            int flipAxis = bHeight-1;   
            for (int i = 0; i < triangleX.length; i++) {   
                Point flippedPoint = FlipUtils.flipHorizontally(triangleX[i], triangleY[i], flipAxis);   
                triangleX[i] = flippedPoint.x;   
                triangleY[i] = flippedPoint.y;   
            }   
        }   
   
        if (alignment == Alignment.RIGHT_ALIGNED_ABOVE ||   
            alignment == Alignment.RIGHT_ALIGNED_BELOW) {   
            int flipAxis = bWidth-1;   
            for (int i = 0; i < triangleX.length; i++) {   
                Point flippedPoint = FlipUtils.flipVertically(triangleX[i], triangleY[i], flipAxis);   
                triangleX[i] = flippedPoint.x;   
                triangleY[i] = flippedPoint.y;   
            }   
        }   
   
        g.setColor(fillColor);   
        g.fillPolygon(triangleX, triangleY, 3);   
        g.setColor(borderColor);   
        g.drawLine(triangleX[0], triangleY[0], triangleX[2], triangleY[2]);   
        g.drawLine(triangleX[1], triangleY[1], triangleX[2], triangleY[2]);   
   
        // bug workaround, Java Bug Database ID 6644471   
        g.setColor(fillColor);   
        g.drawLine(triangleX[0], triangleY[0], triangleX[1], triangleY[1]);   
        g.setColor(borderColor);   
        g.drawLine(triangleX[0], triangleY[0], triangleX[0], triangleY[0]);   
        g.drawLine(triangleX[1], triangleY[1], triangleX[1], triangleY[1]);   
    }   
   
}   

