package cn.compscosys.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cn.compscosys.extend.balloontip.BalloonTip;
import cn.compscosys.extend.borders.TextBorderUtlis;
import cn.compscosys.objects.BasicStudent;;

@SuppressWarnings("serial")
public class JInfoEditDlg extends JDialog implements ActionListener {
	private BasicStudent student;
	
	private JLabel tipStudentNumber = new JLabel("ID Number:");
	private JLabel tipStudentName = new JLabel("Name:");
	private JLabel tipStudentSex = new JLabel("Sex:");
	private JLabel tipStudentAge = new JLabel("Age:");
	private JLabel tipEntryYear = new JLabel("Entry Year:");
	private JLabel tipSchoolName = new JLabel("School Name:");
	private JLabel tipStudentGrade = new JLabel("Grade:");
	private JLabel tipClassName = new JLabel("Class Name:");
	
	private JLabel studentNumber = new JLabel("");
	private JTextField studentName = new JTextField("");
	private JComboBox<String> studentSex = new JComboBox<String>(new String[]{"Male", "Female"});
	private JTextField studentAge = new JTextField("");
	private JTextField entryYear = new JTextField("");
	private JTextField schoolName = new JTextField("");
	private JTextField studentGrade = new JTextField("");
	private JTextField className = new JTextField("");

	private BalloonTip balloonStudentName;
	private BalloonTip balloonStudentAge;
	private BalloonTip balloonEntryYear;
	private BalloonTip balloonSchoolName;
	private BalloonTip balloonStudentGrade;
	private BalloonTip balloonClassName;
	
	private JButton save;
	
	public JInfoEditDlg(JFrame frame, boolean modal, String _studentNumber) {
		super(frame, "Student Information Editor", modal);
		studentNumber.setText(_studentNumber);
		init();
		this.setSize(610, 311);					// Set width and height of window.
		this.setLocationRelativeTo(null);		// Display the window in center.
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	public JInfoEditDlg(JFrame frame, boolean modal, BasicStudent _student) {
		super(frame, "Student Information Editor", modal);
		setInfo(_student);
		init();
		this.setSize(610, 311);					// Set width and height of window.
		this.setLocationRelativeTo(null);		// Display the window in center.
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private void init() {
		Color colorBackground = Color.WHITE;
		TextBorderUtlis borderText = new TextBorderUtlis(new Color(46, 125, 225), 1, true);
		
		studentName.setBackground(colorBackground);
		studentSex.setBackground(colorBackground);
		studentAge.setBackground(colorBackground);
		entryYear.setBackground(colorBackground);
		schoolName.setBackground(colorBackground);
		studentGrade.setBackground(colorBackground);
		className.setBackground(colorBackground);
		
		studentName.setBorder(borderText);
		studentSex.setBorder(borderText);
		studentAge.setBorder(borderText);
		entryYear.setBorder(borderText);
		schoolName.setBorder(borderText);
		studentGrade.setBorder(borderText);
		className.setBorder(borderText);

		ImageIcon loginDefault = new ImageIcon(JEditCrudDlg.class.getResource("/cn/compscosys/images/site_buttons_bg_default.png"));
		ImageIcon loginHover = new ImageIcon(JEditCrudDlg.class.getResource("/cn/compscosys/images/site_buttons_bg_hover.png"));
		save = buttonInit("SAVE", loginDefault, loginHover);
		
		save.addActionListener(this);
		
		setLayout(null);

		add(tipStudentNumber);
		add(tipStudentName);
		add(tipStudentSex);
		add(tipStudentAge);
		add(tipEntryYear);
		add(tipSchoolName);
		add(tipStudentGrade);
		add(tipClassName);
	
		add(studentNumber);
		add(studentName);
		add(studentSex);
		add(studentAge);
		add(entryYear);
		add(schoolName);
		add(studentGrade);
		add(className);
	
		add(save);

		tipStudentNumber.setBounds(20, 20, 110, 32);
		tipStudentName.setBounds(20, 67, 110, 32);
		tipStudentSex.setBounds(20, 114, 110, 32);
		tipStudentAge.setBounds(20, 161, 110, 32);
		
		studentNumber.setBounds(130, 20, 150, 32);
		studentName.setBounds(130, 67, 150, 32);
		studentSex.setBounds(130, 114, 150, 32);
		studentAge.setBounds(130, 161, 150, 32);

		tipEntryYear.setBounds(310, 20, 110, 32);
		tipSchoolName.setBounds(310, 67, 110, 32);
		tipStudentGrade.setBounds(310, 114, 110, 32);
		tipClassName.setBounds(310, 161, 110, 32);
	
		entryYear.setBounds(420, 20, 150, 32);
		schoolName.setBounds(420, 67, 150, 32);
		studentGrade.setBounds(420, 114, 150, 32);
		className.setBounds(420, 161, 150, 32);
	
		save.setBounds(244, 220, 122, 32);
		
		ImageIcon balloonIcon = new ImageIcon(JEditCrudDlg.class.getResource("/cn/compscosys/images/frameicon.png"));
		
		balloonStudentName = BalloonTip.createRoundedBalloonTip(studentName, BalloonTip.Alignment.LEFT_ALIGNED_BELOW, Color.BLACK,
				new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
		balloonStudentName.setTriangleTipLocation(BalloonTip.TriangleTipLocation.SOUTHWEST);
		balloonStudentName.setIcon(balloonIcon);
		balloonStudentName.setText("Student's name can't be empty!");
		balloonStudentName.setIconTextGap(10);
		
		balloonStudentAge = BalloonTip.createRoundedBalloonTip(studentAge, BalloonTip.Alignment.LEFT_ALIGNED_ABOVE, Color.BLACK,
				new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
		balloonStudentAge.setTriangleTipLocation(BalloonTip.TriangleTipLocation.NORTHWEST);
		balloonStudentAge.setIcon(balloonIcon);
		balloonStudentAge.setText("Student's age can't be empty!");
		balloonStudentAge.setIconTextGap(10);
		
		balloonEntryYear = BalloonTip.createRoundedBalloonTip(entryYear, BalloonTip.Alignment.RIGHT_ALIGNED_BELOW, Color.BLACK,
				new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
		balloonEntryYear.setTriangleTipLocation(BalloonTip.TriangleTipLocation.SOUTHEAST);
		balloonEntryYear.setIcon(balloonIcon);
		balloonEntryYear.setText("Entry year can't be empty!");
		balloonEntryYear.setIconTextGap(10);
		
		balloonSchoolName = BalloonTip.createRoundedBalloonTip(schoolName, BalloonTip.Alignment.RIGHT_ALIGNED_BELOW, Color.BLACK,
				new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
		balloonSchoolName.setTriangleTipLocation(BalloonTip.TriangleTipLocation.SOUTHEAST);
		balloonSchoolName.setIcon(balloonIcon);
		balloonSchoolName.setText("School name can't be empty!");
		balloonSchoolName.setIconTextGap(10);
		
		balloonStudentGrade = BalloonTip.createRoundedBalloonTip(studentGrade, BalloonTip.Alignment.RIGHT_ALIGNED_ABOVE, Color.BLACK,
				new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
		balloonStudentGrade.setTriangleTipLocation(BalloonTip.TriangleTipLocation.NORTHEAST);
		balloonStudentGrade.setIcon(balloonIcon);
		balloonStudentGrade.setText("Sudent's grade can't be empty!");
		balloonStudentGrade.setIconTextGap(10);
		
		balloonClassName = BalloonTip.createRoundedBalloonTip(className, BalloonTip.Alignment.RIGHT_ALIGNED_ABOVE, Color.BLACK,
				new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
		balloonClassName.setTriangleTipLocation(BalloonTip.TriangleTipLocation.NORTHEAST);
		balloonClassName.setIcon(balloonIcon);
		balloonClassName.setText("Class name can't be empty!");
		balloonClassName.setIconTextGap(10);
		
		class KeyAdapterPlus extends KeyAdapter {
			private BalloonTip _balloonTip;
			public KeyAdapterPlus(BalloonTip _balloonTip) { this._balloonTip = _balloonTip; }
			// TODO 
			public void keyReleased(KeyEvent e) { _balloonTip.setVisible(false); }
		}
		
		studentName.addKeyListener(new KeyAdapterPlus(balloonStudentName));
		studentAge.addKeyListener(new KeyAdapterPlus(balloonStudentAge));
		entryYear.addKeyListener(new KeyAdapterPlus(balloonEntryYear));
		schoolName.addKeyListener(new KeyAdapterPlus(balloonSchoolName));
		studentGrade.addKeyListener(new KeyAdapterPlus(balloonStudentGrade));
		className.addKeyListener(new KeyAdapterPlus(balloonClassName));
	}
	
	private JButton buttonInit(String _title, ImageIcon _defaultIcon, ImageIcon _hoverIcon) {
		JButton _button = new JButton("<html><b><font color=white>" + _title + "</font></b></html>", _defaultIcon);
		_button.setRolloverIcon(_hoverIcon);
		_button.setHorizontalTextPosition(SwingConstants.CENTER);
		_button.setVerticalTextPosition(SwingConstants.CENTER);
		_button.setBorder(null);
		return _button;
	}

	private void setInfo(BasicStudent student) {
		studentNumber.setText(student.getStudentNumber());
		studentName.setText(student.getStudentName());
		studentSex.setSelectedItem(student.getStudentSex());
		studentAge.setText(student.getStudentAge());
		entryYear.setText(student.getEntryYear());
		schoolName.setText(student.getSchoolName());
		studentGrade.setText(student.getStudentGrade());
		className.setText(student.getClassName());
	}

	private boolean addItem() {
		String _studentNumber = studentNumber.getText();
		String _schoolName = schoolName.getText();
		String _studentSex = studentSex.getSelectedItem().toString();
		String _studentAge = studentAge.getText();
		String _entryYear = entryYear.getText();
		String _studentName = studentName.getText();
		String _studentGrade = studentGrade.getText();
		String _className = className.getText();

		if (_studentName.isEmpty()) { balloonStudentName.setVisible(true); return false; }
		if (_studentAge.isEmpty()) { balloonStudentAge.setVisible(true); return false; }
		if (_entryYear.isEmpty()) { balloonEntryYear.setVisible(true); return false; }
		if (_schoolName.isEmpty()) { balloonSchoolName.setVisible(true); return false; }
		if (_studentGrade.isEmpty()) { balloonStudentGrade.setVisible(true); return false; }
		if (_className.isEmpty()) { balloonClassName.setVisible(true); return false; }

		String tip = "Are you sure you want to add the following student information?\r\nStudent's ID Number: "
				+ _studentNumber + "\r\nStudent Name: " + _studentName + "\r\nStudent Sex: " + _studentSex
				+ "\r\nStudent Age: " + _studentAge + "\r\nEntry Year: " + _entryYear + "\r\nSchool Name: " + _schoolName
				+ "\r\nGrade: " + _studentGrade + "\r\nClass: " + _className;
		int responseInt = JOptionPane.showConfirmDialog(this, tip, "Question", JOptionPane.YES_NO_OPTION);
		if (responseInt == JOptionPane.NO_OPTION) { return false; }

		this.student = new BasicStudent(_studentNumber, _studentName, _studentSex, _studentAge, _entryYear,
				_schoolName, _studentGrade, _className);
		return true;
	}
	
	public BasicStudent getInfo() {
			return this.student;
	}
	
	public void actionPerformed(ActionEvent e) {
		String _studentNumber = studentNumber.getText();
		Object[] options = {"OK"};
		if (_studentNumber.isEmpty()) {
			JOptionPane.showOptionDialog(null, "Student's ID can not be empty!", "Warning", JOptionPane.YES_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			return;
		}
		
		if (e.getSource() == save) {
			if (addItem()) { this.dispose(); }
		}
	}
}
