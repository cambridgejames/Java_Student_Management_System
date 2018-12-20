package cn.compscosys.frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cn.compscosys.extend.borders.TextBorderUtlis;

public class JLoginDlg extends JDialog implements ActionListener {
	private static final long serialVersionUID = -5678771864593019818L;
	
	JButton close = new JButton("Χ");
	JButton login;
	
	JLabel usernameTip = new JLabel("User Name:");
	JLabel passwordTip = new JLabel("Password:");
	JLabel loginTip = new JLabel("");
	
	JTextField username = new JTextField("123");
	JPasswordField password = new JPasswordField("456");

	public JLoginDlg(JFrame frame, Boolean mode) {
		super(frame, "", mode);
		init();
		this.setSize(380, 276);					// Set width and height of window.
		this.setLocationRelativeTo(null);		// Display the window in center.
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setUndecorated(true);
		this.setVisible(true);
		this.addWindowListener( new WindowAdapter() { public void windowClosing(WindowEvent we) { System.exit(0); } } );
	}
	
	public void init() {
		ImageIcon loginDefault = new ImageIcon(JLoginDlg.class.getResource("/cn/compscosys/images/site_buttons_bg_default.png"));
		ImageIcon loginHover = new ImageIcon(JLoginDlg.class.getResource("/cn/compscosys/images/site_buttons_bg_hover.png"));
		login = buttonInit("LOGIN", loginDefault, loginHover);

		Color colorBackground = new Color(250, 250, 250);
		TextBorderUtlis borderText = new TextBorderUtlis(new Color(202, 210, 219), 2, true);
		loginTip.setForeground(Color.RED);
		
		close.addActionListener(this);
		close.setBorder(null);
		close.setBackground(colorBackground);
		login.addActionListener(this);
		
		this.getContentPane().setBackground(colorBackground);
		
		this.setLayout(null);
		
		add(close);
		add(usernameTip);
		add(username);
		add(passwordTip);
		add(password);
		add(loginTip);
		add(login);
		close.setBounds(356, 4, 20, 20);
		usernameTip.setBounds(35, 28, 310, 15);
		username.setBounds(36, 58, 308, 40);
		passwordTip.setBounds(35, 113, 310, 15);
		password.setBounds(36, 143, 308, 40);
		loginTip.setBounds(36, 193, 310, 20);
		login.setBounds(130, 218, 122, 32);

		username.setBackground(colorBackground);
		username.setBorder(borderText);
		password.setBackground(colorBackground);
		password.setBorder(borderText);
		
		MouseAdapter changeCursor = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
			public void mouseExited(MouseEvent e) { setCursor(Cursor.getDefaultCursor()); }
		};

		close.addMouseListener(changeCursor);
		login.addMouseListener(changeCursor);
		
		DocumentListener textChanged = new DocumentListener(){
			public void changedUpdate(DocumentEvent e) { loginTip.setText(""); }
			public void insertUpdate(DocumentEvent e) { loginTip.setText(""); }
			public void removeUpdate(DocumentEvent e) { loginTip.setText(""); }
		};
		
		username.getDocument().addDocumentListener(textChanged);
		password.getDocument().addDocumentListener(textChanged);
	}
	
	private JButton buttonInit(String _title, ImageIcon _defaultIcon, ImageIcon _hoverIcon) {
		JButton _button = new JButton("<html><b><font color=white>" + _title + "</font></b></html>", _defaultIcon);
		_button.setRolloverIcon(_hoverIcon);
		_button.setHorizontalTextPosition(SwingConstants.CENTER);
		_button.setVerticalTextPosition(SwingConstants.CENTER);
		_button.setBorder(null);
		return _button;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == close) {
			System.exit(0);
		}
		else if (e.getSource() == login) {
			String inputUserName = username.getText(), inputPassword = String.valueOf(password.getPassword());
			if (inputUserName.isEmpty() || inputPassword.isEmpty()) {
				loginTip.setText("Username or password can't be empty");
			}
			else if (!isUsernameAndPasswordMatch(inputUserName, inputPassword)) {
				loginTip.setText("Username or password incorrect");
			}
			else {
				this.dispose();
			}
		}
	}
	
	protected boolean isUsernameAndPasswordMatch(String _userName, String _password) {
		return _userName.equals("123") && _password.equals("456");
	}
}
