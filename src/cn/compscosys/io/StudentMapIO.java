package cn.compscosys.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import cn.compscosys.objects.BasicStudent;

public class StudentMapIO {
	@SuppressWarnings("unchecked")
	public static HashMap<String, BasicStudent> inputStudentMap(String _filePath) throws Exception {
		File filePath = new File(_filePath);
		try {
			FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream oin = new ObjectInputStream(fin);
			HashMap<String, BasicStudent> studentMap = (HashMap<String, BasicStudent>)oin.readObject();
			oin.close();
			return studentMap;
		} catch (Exception e) {
			// TODO: Write errors to the log.
			return new HashMap<String, BasicStudent>();
		}
	}
	
	public static void outputStuedntMap(HashMap<String, BasicStudent> studentMap, String _filePath) throws Exception {
		File filePath = new File(_filePath);
		FileOutputStream fou = new FileOutputStream(filePath);
		ObjectOutputStream oou = new ObjectOutputStream(fou);
		oou.writeObject(studentMap);
		oou.close();
	}
}
