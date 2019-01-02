package albany.edu.hw5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

/************************************************************************************************************
 * 
 * @author will
 * 
 * The time that my Multithreaded program ran in was: in the range of 2000000 to 3000000 nano seconds
 * The time that my non-Paralle grep prgram ran in was also in the range of 2000000 to 3000000 nano seconds
 * The output i got when i ran the command to get the time for the linux grep was:
 * 		0.00user 0.00system 0:00.00elapsed 100%CPU (0avgtext+0avgdata 2196maxresident)k
 *		0inputs+0outputs (0major+94minor)pagefaults 0swaps
 *
 *
 *************************************************************************************************************/

public class MultiThreadedGrep extends Thread{
	public static void main(String args[]) throws IOException, InterruptedException {
		FileReader file = new FileReader(args[2]);
		BufferedReader buffer = new BufferedReader(file);
		ArrayList<String> section1 = new ArrayList<>();
		ArrayList<String> section2 = new ArrayList<>();
		int numLines = 0;
		while(buffer.readLine() != null) {
			numLines++;
		}

		buffer.close();		
		FileReader file2 = new FileReader(args[2]);
		BufferedReader buffer2 = new BufferedReader(file2);
		for(int i=0; i<numLines/2; i++) {
			section1.add(buffer2.readLine());
		}
		
		String line;
		while((line = buffer2.readLine()) != null) {
			section2.add(line);
		}
		
		buffer2.close();
		
		GrepThread thread1 = new GrepThread(args[1], section1, 1);
		GrepThread thread2 = new GrepThread(args[1], section2, numLines/2+1);
		
		long startTime = System.nanoTime();
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("Time in Nano Seconds: " + totalTime);
		
		thread1.print();
		thread2.print();
	}
}

class GrepThread extends Thread{
	private String regex;
	private ArrayList<String> section = new ArrayList<>();
	private ArrayList<Node> matched = new ArrayList<>();
	private int offset;
	
	public GrepThread(String regex, ArrayList<String> section, int offset) {
		this.regex = regex;
		this.section = section;
		this.offset = offset;
	}
	
	public void run() {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		for(int i=0; i<section.size(); i++) {
			matcher = pattern.matcher(section.get(i));
			if(matcher.find()) {
				Node match = new Node();
				match.line = section.get(i);
				match.lineNum = i+offset;
				matched.add(match);
			}
		}
	}
	
	public void print() {
		for(int i=0; i<matched.size(); i++) {
			System.out.println(matched.get(i));
			
		}
	}
	
	class Node{
		String line;
		int lineNum;
		
		public String toString() {
			return lineNum + ":" + line;
		}
	}
}