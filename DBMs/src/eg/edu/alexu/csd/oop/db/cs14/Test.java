package eg.edu.alexu.csd.oop.db.cs14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
	
		
String[] strings = {"id" , " fd" , "jkbv"};
//System.out.println((Arrays.toString(Arrays.copyOfRange(strings, 1,
//		strings.length  ))));
//		
StringBuilder st = new StringBuilder();
for (int i = 0; i < strings.length; i++) {

	st.append(strings[i]);
}
System.out.println(st.toString());

	}

}
