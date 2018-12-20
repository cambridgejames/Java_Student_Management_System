package cn.compscosys.extend.balloontip.utils;   
   
import java.awt.Point;   
   
public class FlipUtils {   
   
    private FlipUtils() {};   
   
    public static Point flipHorizontally(int x, int y, int flipAxis) {   
        Point p = new Point(x, y);   
        p.move(p.x, flipAxis-p.y);   
        return p;   
    }   
   
    public static Point flipVertically(int x, int y, int flipAxis) {   
        Point p = new Point(x, y);   
        p.move(flipAxis-p.x, p.y);   
        return p;   
    }   
   
}   

