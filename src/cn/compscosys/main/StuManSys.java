package cn.compscosys.main;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import cn.compscosys.frame.JEditCrudDlg;

public class StuManSys {
	public static void main(String[] args) {
		initGobalFont(new Font("微软雅黑", Font.PLAIN, 15));
		new JEditCrudDlg("Java Student Management System");
	}
	
	public static void initGobalFont(Font font) {
	    FontUIResource fontResource = new FontUIResource(font);
	    for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if(value instanceof FontUIResource) {
	            UIManager.put(key, fontResource);
	        }
	    }
	}
}
