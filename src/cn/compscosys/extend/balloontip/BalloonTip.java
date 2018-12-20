package cn.compscosys.extend.balloontip;   
   
import java.awt.Color;   
import java.awt.Component;   
import java.awt.Container;   
import java.awt.GridBagConstraints;   
import java.awt.GridBagLayout;   
import java.awt.Insets;   
import java.awt.Point;   
import java.awt.event.ActionEvent;   
import java.awt.event.ActionListener;   
import java.awt.event.ComponentAdapter;   
import java.awt.event.ComponentEvent;   
import java.awt.event.HierarchyBoundsAdapter;   
import java.awt.event.HierarchyEvent;   
import java.awt.event.MouseAdapter;   
import java.awt.event.MouseEvent;   
   
import javax.swing.BorderFactory;   
import javax.swing.Icon;   
import javax.swing.ImageIcon;   
import javax.swing.JButton;   
import javax.swing.JDialog;   
import javax.swing.JFrame;   
import javax.swing.JInternalFrame;   
import javax.swing.JLabel;   
import javax.swing.JLayeredPane;   
import javax.swing.JPanel;   
import javax.swing.SwingUtilities;   
import javax.swing.border.Border;   
import javax.swing.border.EmptyBorder;   
   
/**  
 * <p>  
 * A balloon tip which can be displayed  
 * <ul>  
 * <li>left-aligned above</li>  
 * <li>left-aligned below</li>  
 * <li>right-aligned above</li>  
 * <li>right-aligned below</li>  
 * </ul>  
 * the attached component. See enumeration <code>BalloonTip.Alignment</code>.  
 * </p>  
 * <p>  
 * Additionally the triangle tip location can be set manually to the following attached component's locations:  
 * <ul>  
 * <li>Northwest</li>  
 * <li>North</li>  
 * <li>Northeast</li>  
 * <li>West</li>  
 * <li>Center</li>  
 * <li>East</li>  
 * <li>Southwest</li>  
 * <li>South</li>  
 * <li>Southeast</li>  
 * </ul>  
 * See enumeration <code>BalloonTip.TriangleTipLocation</code>.  
 * </p>  
 * The balloon tip uses a <code>JLabel</code> to render its contents which allows use of HTML code.  
 * </p>  
 * <p>  
 * Create a balloon tip by using the static <code>create</code> methods. You can choose between two looks:  
 * <ul>  
 * <li>Edged</li>  
 * <li>Rounded</li>  
 * </ul>  
 * </p>  
 * @author Bernhard Pauler  
 */   
public class BalloonTip extends JPanel { 
	private static final long serialVersionUID = 5160762684968421626L;

	public enum Alignment {LEFT_ALIGNED_ABOVE, RIGHT_ALIGNED_ABOVE, LEFT_ALIGNED_BELOW, RIGHT_ALIGNED_BELOW};   
    public enum TriangleTipLocation {AUTOMATIC, CENTER, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};   
   
    private final Icon defaultIcon  = new ImageIcon(BalloonTip.class.getResource("/cn/compscosys/images/closebutton_default.png"));   
    private final Icon rolloverIcon = new ImageIcon(BalloonTip.class.getResource("/cn/compscosys/images/closebutton_rollover.png"));   
    private final Icon pressedIcon  = new ImageIcon(BalloonTip.class.getResource("/cn/compscosys/images/closebutton_pressed.png"));   
   
    private final Component attachedComponent;   
    private final JLabel label = new JLabel();   
    private JButton closeButton;   
    private final Alignment alignment;   
    private TriangleTipLocation triangleTipLocation = TriangleTipLocation.AUTOMATIC;   
    private final int hOffset;   
    @SuppressWarnings("unused")
	private final int vOffset;   
   
    public static BalloonTip createEdgedBalloonTip(Component attachedComponent,   
                                                   Alignment alignment,   
                                                   Color borderColor, Color fillColor,   
                                                   int borderWidth,   
                                                   int horizontalOffset, int verticalOffset,   
                                                   boolean useCloseButton) {   
        Border border = new EdgedBalloonBorder(alignment, horizontalOffset, verticalOffset, borderColor, fillColor);   
        BalloonTip balloonTip = new BalloonTip(attachedComponent, alignment, border, borderWidth, horizontalOffset, verticalOffset, useCloseButton);   
        if (useCloseButton) balloonTip.setCloseButtonBorderWidth(1, 0, 1, 1);   
        return balloonTip;   
    }   
   
    public static BalloonTip createRoundedBalloonTip(Component attachedComponent,   
                                                     Alignment alignment,   
                                                     Color borderColor, Color fillColor,   
                                                     int borderWidth,   
                                                     int horizontalOffset, int verticalOffset,   
                                                     int arcWidth, int arcHeight,   
                                                     boolean useCloseButton) {   
        Border border = new RoundedBalloonBorder(alignment, horizontalOffset, verticalOffset, arcHeight, arcHeight, borderColor, fillColor);   
        return new BalloonTip(attachedComponent, alignment, border, borderWidth, horizontalOffset, verticalOffset, useCloseButton);   
    }   
   
    private BalloonTip(Component attachedComponent, Alignment alignment, Border border, int borderWidth, int horizontalOffset, int verticalOffset, boolean useCloseButton) {   
        this.attachedComponent = attachedComponent;   
        this.alignment = alignment;   
        hOffset = horizontalOffset;   
        vOffset = verticalOffset;   
   
        setBorder(border);   
        setOpaque(false);   
        setLayout(new GridBagLayout());   
   
        label.setBorder(new EmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));   
        add(label, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));   
   
        if (useCloseButton) {   
            closeButton = new JButton();   
            closeButton.setBorder(null);   
            closeButton.setContentAreaFilled(false);   
            closeButton.setIcon(defaultIcon);   
            closeButton.setRolloverIcon(rolloverIcon);   
            closeButton.setPressedIcon(pressedIcon);   
            closeButton.setFocusable(false); // close button needs no focus / must not steal focus from attached or other components   
            closeButton.addActionListener(new ActionListener() {   
                public void actionPerformed(ActionEvent e) {   
                    setVisible(false);   
                }   
            });   
            add(closeButton, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));   
        }   
   
        // we use the popup layer of the top level container (frame or dialog) to show the balloon tip   
        // first we need to determine the top level container...   
        Container parent = attachedComponent.getParent();   
        JLayeredPane layeredPane;   
        while (true) {   
            if (parent instanceof JFrame) {   
                layeredPane = ((JFrame) parent).getLayeredPane();   
                break;   
            } else if (parent instanceof JDialog) {   
                layeredPane = ((JDialog) parent).getLayeredPane();   
                break;   
            } else if (parent instanceof JInternalFrame) {   
                layeredPane = ((JInternalFrame) parent).getLayeredPane();   
                break;   
            }   
            parent = parent.getParent();   
        }   
        layeredPane.add(this, JLayeredPane.POPUP_LAYER);   
   
        // if the attached component is moved while the balloon tip is visible, we need to move as well   
        attachedComponent.addComponentListener(new ComponentAdapter() {   
            public void componentMoved(ComponentEvent e) {   
                if (isShowing()) {   
                    determineAndSetLocation();   
                }   
            }   
        });   
        // sometimes the attached component is moved indirectly by a parent component ("ancestor")...   
        // in this case the attached component receives no ComponentEvent but an HierarchyEvent   
        attachedComponent.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {   
            public void ancestorMoved(HierarchyEvent e) {   
                if (isShowing()) {   
                    determineAndSetLocation();   
                }   
            }   
        });   
   
        // don't allow to click 'through' the component   
        addMouseListener(new MouseAdapter() {   
            public void mouseClicked(MouseEvent e) {   
                e.consume();   
            }   
        });   
   
        // must not be visible at first   
        setVisible(false);   
    }   
   
    private void determineAndSetLocation() {   
        Point location = SwingUtilities.convertPoint(attachedComponent, getLocation(), this);   
        int x = 0, y = 0;   
   
        if (triangleTipLocation == TriangleTipLocation.AUTOMATIC) {   
            switch (alignment) {   
                case LEFT_ALIGNED_ABOVE:   
                    x = location.x;   
                    y = location.y - getPreferredSize().height;   
                    break;   
                case LEFT_ALIGNED_BELOW:   
                    x = location.x;   
                    y = location.y + attachedComponent.getHeight();   
                    break;   
                case RIGHT_ALIGNED_ABOVE:   
                    x = location.x + attachedComponent.getWidth() - getPreferredSize().width;   
                    y = location.y - getPreferredSize().height;   
                    break;   
                case RIGHT_ALIGNED_BELOW:   
                    x = location.x + attachedComponent.getWidth() - getPreferredSize().width;   
                    y = location.y + attachedComponent.getHeight();   
                    break;   
            }   
        } else {   
            // in order to simplify/merge the calculation for the different triangle tip locations   
            // independently of the alignments, always start off the northwest corner and proceed from there   
            switch (alignment) {   
                case LEFT_ALIGNED_ABOVE:   
                    x = location.x - hOffset;   
                    y = location.y - getPreferredSize().height;   
                    break;   
                case LEFT_ALIGNED_BELOW:   
                    x = location.x - hOffset;   
                    y = location.y;   
                    break;   
                case RIGHT_ALIGNED_ABOVE:   
                    x = location.x - getPreferredSize().width + hOffset;   
                    y = location.y - getPreferredSize().height;   
                    break;   
                case RIGHT_ALIGNED_BELOW:   
                    x = location.x - getPreferredSize().width + hOffset;   
                    y = location.y;   
                    break;   
            }   
            switch (triangleTipLocation) {   
                case NORTHWEST:   
                    // nothing to do here   
                    break;   
                case NORTH:   
                    x = x + attachedComponent.getWidth()/2;   
                    break;   
                case NORTHEAST:   
                    x = x + attachedComponent.getWidth();   
                    break;   
                case WEST:   
                    y = y + attachedComponent.getHeight()/2;   
                    break;   
                case CENTER:   
                    x = x + attachedComponent.getWidth()/2;   
                    y = y + attachedComponent.getHeight()/2;   
                    break;   
                case EAST:   
                    x = x + attachedComponent.getWidth();   
                    y = y + attachedComponent.getHeight()/2;   
                    break;   
                case SOUTHWEST:   
                    y = y + attachedComponent.getHeight();   
                    break;   
                case SOUTH:   
                    x = x + attachedComponent.getWidth()/2;   
                    y = y + attachedComponent.getHeight();   
                    break;   
                case SOUTHEAST:   
                    x = x + attachedComponent.getWidth();   
                    y = y + attachedComponent.getHeight();   
                    break;
				case AUTOMATIC:
					break;
				default:
					break;   
            }   
        }   
   
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);   
        validate();   
    }   
   
    public void setText(String text) {   
        label.setText(text);   
    }   
   
    public void setIcon(Icon icon) {   
        label.setIcon(icon);   
    }   
   
    public void setIconTextGap(int iconTextGap) {   
        label.setIconTextGap(iconTextGap);   
    }   
   
    public void setVisible(boolean show) {   
        if (show) {   
            determineAndSetLocation();   
        }   
        super.setVisible(show);   
    }   
   
    public void setTriangleTipLocation(TriangleTipLocation triangleTipLocation) {   
        this.triangleTipLocation = triangleTipLocation;   
        determineAndSetLocation();   
    }   
   
    public void setCloseButtonBorderWidth(int top, int left, int bottom, int right) {   
        if (closeButton != null) {   
            closeButton.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));   
        }   
    }   
   
}   

