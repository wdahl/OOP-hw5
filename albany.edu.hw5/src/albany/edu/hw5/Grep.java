package albany.edu.hw5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {
	public static void main(String args[]) throws IOException {
		FileReader file = new FileReader(args[2]);
		BufferedReader buffer = new BufferedReader(file);

		String line = buffer.readLine();
		int lineCount = 1;
		long startTime = System.nanoTime();
		while(line != null) {
			Pattern p = Pattern.compile(args[1]);
			Matcher m = p.matcher(line);
			if(m.find()) {
				System.out.println(lineCount + ":" + line);
			}
			
			line = buffer.readLine();
			lineCount++;
		}
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Time in Nano Seconds: " + totalTime);
		
		buffer.close();		
	}
}