package cn.compscosys.objects;

import java.io.Serializable;

public class BasicStudent implements Serializable {
	private static final long serialVersionUID = 7319648694009327955L;
	
	private String studentNumber;
	private String studentName;
	private String studentSex;
	private String studentAge;
	private String entryYear;
	private String schoolName;
	private String studentGrade;
	private String className;
	
	public BasicStudent() {}
	public BasicStudent(String studentNumber, String studentName, String studentSex, String studentAge,
			String entryYear, String schoolName, String studentGrade, String className) {
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.studentSex = studentSex;
		this.studentAge = studentAge;
		this.entryYear = entryYear;
		this.schoolName = schoolName;
		this.studentGrade = studentGrade;
		this.className = className;
	}
	public BasicStudent(BasicStudent student) {
		this(student.getStudentNumber(), student.getStudentName(), student.getStudentSex(), student.getStudentAge(),
				student.getEntryYear(), student.getSchoolName(), student.getStudentGrade(), student.getClassName());
	}
	
	public String getStudentNumber() { return this.studentNumber; }
	public String getStudentName() { return this.studentName; }
	public String getStudentSex() { return this.studentSex; }
	public String getStudentAge() { return this.studentAge; }
	public String getEntryYear() { return this.entryYear; }
	public String getSchoolName() { return this.schoolName; }
	public String getStudentGrade() { return this.studentGrade; }
	public String getClassName() { return this.className; }
	
	public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
	public void setStudentName(String studentName) { this.studentName = studentName; }
	public void setStudentSex(String studentSex) { this.studentSex = studentSex; }
	public void setStudentAge(String studentAge) { this.studentAge = studentAge; }
	public void setEntryYear(String entryYear) { this.entryYear = entryYear; }
	public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
	public void setStudentGrade(String studentGrade) { this.studentGrade = studentGrade; }
	public void setClassName(String className) { this.className = className; }
	
	public void setInfo(BasicStudent student) {
		setInfo(student.getStudentNumber(), student.getStudentName(), student.getStudentSex(), student.getStudentAge(),
				student.getEntryYear(), student.getSchoolName(), student.getStudentGrade(), student.getClassName());
	}
	public void setInfo(String studentNumber, String studentName, String studentSex, String studentAge,
			String entryYear, String schoolName, String studentGrade, String className) {
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.studentSex = studentSex;
		this.studentAge = studentAge;
		this.entryYear = entryYear;
		this.schoolName = schoolName;
		this.studentGrade = studentGrade;
		this.className = className;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		BasicStudent student = (BasicStudent) obj;
		return this.studentNumber.equals(student.getStudentNumber()) && this.studentName.equals(student.getStudentName())
				&& this.studentSex.equals(student.getStudentSex()) && this.studentAge.equals(student.getStudentAge())
				&& this.entryYear.equals(student.getEntryYear()) && this.schoolName.equals(student.getSchoolName())
				&& this.studentGrade.equals(student.getStudentGrade()) && this.className.equals(student.getClassName());
	}
	
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	public String toString() {
		return studentNumber + studentName + studentSex + studentAge + entryYear + schoolName + studentGrade + className;
	}
}
