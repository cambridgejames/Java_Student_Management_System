package cn.compscosys.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import cn.compscosys.io.StudentMapIO;
import cn.compscosys.extend.balloontip.BalloonTip;
import cn.compscosys.extend.borders.TextBorderUtlis;
import cn.compscosys.objects.BasicStudent;

public class JEditCrudDlg extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6196906175117214289L;
	
	private String[] columnNames = {"ID", "Name", "Sex", "Age", "Entry Year", "School Name", "Grade", "Class Name"};
	
    @SuppressWarnings("serial")
	private DefaultTableModel studentTableModel = new DefaultTableModel(new String[][] {}, columnNames) {
		public boolean isCellEditable(int row, int column) { return false; }
    };
    
	private JTable studentTable = new JTable(studentTableModel);
	private JScrollPane tableScrollPanel = new JScrollPane(studentTable);
	
	private JLabel studentNumberTip = new JLabel("Student ID:");
	private JTextField studentNumber = new JTextField();
   
    private BalloonTip balloonTip;
	
	private JButton insertItem;
	private JButton deleteItem;
	private JButton updateItem;
	private JButton selectItem;
	
	HashMap<String, BasicStudent> studentMap;
	
	public JEditCrudDlg(String title) {
		this.setTitle(title);
		this.setSize(998, 596);					// Set width and height of window.
		this.setLocationRelativeTo(null);		// Display the window in center.
		this.setResizable(false);
		this.setVisible(true);
		this.setIconImage(new ImageIcon(this.getClass().getResource("/cn/compscosys/images/main_frame_icon.png")).getImage());
		
		new JLoginDlg(this, true);
		
		init();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				saveInformations();
				System.exit(0);
			}
		} );
		
		try {
			this.studentMap = StudentMapIO.inputStudentMap(".information");
			studentTableInit(studentMap);
		} catch (Exception e) {
			// TODO: Write errors to the log.
			JOptionPane.showMessageDialog(this, "Error while reading information.", "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}
	
	private void init() {
		ImageIcon loginDefault = new ImageIcon(JEditCrudDlg.class.getResource("/cn/compscosys/images/site_buttons_bg_default.png"));
		ImageIcon loginHover = new ImageIcon(JEditCrudDlg.class.getResource("/cn/compscosys/images/site_buttons_bg_hover.png"));
		insertItem = buttonInit("INSERT", loginDefault, loginHover);
		deleteItem = buttonInit("DELETE", loginDefault, loginHover);
		updateItem = buttonInit("UPDATE", loginDefault, loginHover);
		selectItem = buttonInit("SELECT", loginDefault, loginHover);

		Color colorBackground = Color.WHITE;
		TextBorderUtlis borderText = new TextBorderUtlis(new Color(46, 125, 225), 1, true);
		studentNumber.setBackground(colorBackground);
		studentNumber.setBorder(borderText);
		tableScrollPanel.getViewport().setBackground(colorBackground);
		tableScrollPanel.setBorder(borderText);

		studentTable .getTableHeader().setReorderingAllowed(false);
		studentTable.setRowHeight(30);
		studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		studentNumber.addKeyListener(new KeyAdapter() {public void keyReleased(KeyEvent e) { balloonTip.setVisible(false); }});
		insertItem.addActionListener(this);
		deleteItem.addActionListener(this);
		updateItem.addActionListener(this);
		selectItem.addActionListener(this);
		
		studentTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int selectedRow = studentTable.getSelectedRow();
				Object _studentNumber = studentTableModel.getValueAt(selectedRow, 0);
				studentNumber.setText(_studentNumber.toString());
			}
		});
		
		this.setLayout(null);
		
		add(studentNumberTip);
		add(studentNumber);
		add(tableScrollPanel);
		add(insertItem);
		add(deleteItem);
		add(updateItem);
		add(selectItem);
	
		studentNumberTip.setBounds(20, 20, 100, 32);
		studentNumber.setBounds(120, 20, 200, 32);
		insertItem.setBounds(412, 20, 122, 32);
		deleteItem.setBounds(554, 20, 122, 32);
		updateItem.setBounds(696, 20, 122, 32);
		selectItem.setBounds(838, 20, 122, 32);
		
		tableScrollPanel.setBounds(20, 75, 940, 460);
		
		balloonTip = BalloonTip.createRoundedBalloonTip(studentNumber, BalloonTip.Alignment.LEFT_ALIGNED_BELOW, Color.BLACK,
													new Color(255, 255, 225), 10, 15, 20, 7, 7, true);
        balloonTip.setTriangleTipLocation(BalloonTip.TriangleTipLocation.AUTOMATIC);
        balloonTip.setIcon(new ImageIcon(JEditCrudDlg.class.getResource("/cn/compscosys/images/frameicon.png")));
        balloonTip.setIconTextGap(10);
	}
	
	private JButton buttonInit(String _title, ImageIcon _defaultIcon, ImageIcon _hoverIcon) {
		JButton _button = new JButton("<html><b><font color=white>" + _title + "</font></b></html>", _defaultIcon);
		_button.setRolloverIcon(_hoverIcon);
		_button.setHorizontalTextPosition(SwingConstants.CENTER);
		_button.setVerticalTextPosition(SwingConstants.CENTER);
		_button.setBorder(null);
		return _button;
	}
	
	private void studentTableInit(HashMap<String, BasicStudent> _studentMap) {
		Iterator<Entry<String, BasicStudent>> iter = _studentMap.entrySet().iterator();
		BasicStudent student;
		while (iter.hasNext()) {
			student = (BasicStudent)iter.next().getValue();
			studentTableModel.addRow(new String[] {student.getStudentNumber(), student.getStudentName(), student.getStudentSex(),
											student.getStudentAge(), student.getEntryYear(), student.getSchoolName(),
											student.getStudentGrade(), student.getClassName()});
		}
	}
	
	private void addItem(String _studentNumber) {
		JInfoEditDlg dlg = new JInfoEditDlg(this, true, _studentNumber);
		dlg.setVisible(true);
		
		BasicStudent student = dlg.getInfo();
		if (student == null) return;

		studentTableModel.addRow(new String[] {_studentNumber, student.getStudentName(), student.getStudentSex(),
										student.getStudentAge(), student.getEntryYear(), student.getSchoolName(),
										student.getStudentGrade(), student.getClassName()});
		studentMap.put(_studentNumber, student);
		saveInformations();
	}
	
	private void updateItem(BasicStudent _student) {
		JInfoEditDlg dlg = new JInfoEditDlg(this, true, _student);
		dlg.setVisible(true);
		
		BasicStudent student = dlg.getInfo();
		if (student == null) return;
		
		int rowIndex = getRowFromTable(student.getStudentNumber());
		studentTableModel.setValueAt(student.getStudentName(), rowIndex, 1);
		studentTableModel.setValueAt(student.getStudentSex(), rowIndex, 2);
		studentTableModel.setValueAt(student.getStudentAge(), rowIndex, 3);
		studentTableModel.setValueAt(student.getEntryYear(), rowIndex, 4);
		studentTableModel.setValueAt(student.getSchoolName(), rowIndex, 5);
		studentTableModel.setValueAt(student.getStudentGrade(), rowIndex, 6);
		studentTableModel.setValueAt(student.getClassName(), rowIndex, 7);
		
		studentMap.put(student.getStudentNumber(), student);
		saveInformations();
	}
	
	private void selectItem(String _studentNumber) {
		int rowIndex = getRowFromTable(_studentNumber);
		studentTable.setRowSelectionInterval(rowIndex, rowIndex);
		studentTable.scrollRectToVisible(studentTable.getCellRect(rowIndex, 0, true));
		studentTable.setRowSelectionInterval(rowIndex, rowIndex);
	}
	
	private void saveInformations() {
		try {
			StudentMapIO.outputStuedntMap(this.studentMap, ".information");
		} catch (Exception e) {
			// TODO: Write errors to the log.
			JOptionPane.showMessageDialog(this, "Error while saving information.", "Error", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}
	
	private int getRowFromTable(String _studentNumber) {
		boolean isSelected = false;
		int rowCount = studentTable.getRowCount(), i = 0;
		for (i = 0; i < rowCount; i++) {
			String _studentNumberC = studentTableModel.getValueAt(i, 0).toString();
			if (_studentNumberC.equals(_studentNumber)) {
				isSelected = true;
				break;
			}
		}
		return isSelected ? i : -1;
	}

	public void actionPerformed(ActionEvent e) {
		String _studentNumber = studentNumber.getText();
		if (_studentNumber.isEmpty()) {
			balloonTip.setText("Student's ID can not be empty!");
			balloonTip.setVisible(true);
			return;
		}
		
		if (e.getSource() == insertItem) {
			BasicStudent student = studentMap.get(_studentNumber);

			if (student != null) {
				balloonTip.setText("The information of student " + _studentNumber + " already exists.");
				balloonTip.setVisible(true);
				return;
			}
			
			addItem(_studentNumber);
		}
		else if (e.getSource() == deleteItem) {
			if (studentMap.get(_studentNumber) == null) {
				balloonTip.setText("The information of student " + _studentNumber + " was not found.");
				balloonTip.setVisible(true);
				return;
			}
			
			int responseInt = JOptionPane.showConfirmDialog(this, "Are you sure to delete the information of student "
					+ _studentNumber + "?", "Question", JOptionPane.YES_NO_OPTION);
			if (responseInt == JOptionPane.NO_OPTION) { return; }
			
			studentTableModel.removeRow(getRowFromTable(_studentNumber));
			studentMap.remove(_studentNumber);
			saveInformations();
			JOptionPane.showMessageDialog(null, "The information of student " + _studentNumber + " has been deleted successfully.");
		}
		else if (e.getSource() == updateItem) {
			BasicStudent student = studentMap.get(_studentNumber);

			if (student == null) {
				balloonTip.setText("The student " + _studentNumber + " was not found.");
				balloonTip.setVisible(true);
				return;
			}
			else { updateItem(student); }
		}
		else if (e.getSource() == selectItem) {
			BasicStudent student = studentMap.get(_studentNumber);

			if (student == null) {
				balloonTip.setText("The student " + _studentNumber + " was not found.");
				balloonTip.setVisible(true);
				return;
			}
			else { selectItem(_studentNumber); }
		}
	}
}
