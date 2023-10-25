//Author: Parth Nimbalkar
//Date: 9/18/2023

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BinaryTranslator {
	
	public BinaryTranslator() {
		System.out.println("Please enter \"file\" to enter a file or \"input\" to use console.");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		String numberInput = "";
		if (input.equals("file")){ //input from a file
			try {
			System.out.println("Enter your file name");
			input = scanner.nextLine();
			Scanner fileScanner = new Scanner (new File(input));
			numberInput = fileScanner.nextLine();
			} catch (IOException ex) {
				System.out.println("File not found.");
				scanner.close();
				System.exit(1);
			}
		}
		else { //input from the console
			numberInput = scanner.nextLine();	
		}
		System.out.println("If you are translatinng from decimal to binary, type \"dtb\".");
		System.out.println("If you are translatinng from binary to decimal, type \"btd\".");
		input = scanner.nextLine();
		
		if (input.equals("dtb")) {//decimal to binary
			//decimal number
			String b = "";
			int t = Integer.parseInt(numberInput);
			while (t>0)
			{
				int r = t%2;
				t = t/2;
				b = r+b;
			}
			System.out.println(b);
		}
		else { // binary to decimal 
				String binaryString = numberInput;
				int temp = Integer.parseInt(binaryString);
				System.out.println(temp);
				int power = 0;
				int decimalno = 0;
				while (temp != 0) {
					int remain = temp%10;
					decimalno = decimalno + (int)(remain * Math.pow(2, power));
					power++;
					temp = temp/10;
				}
				System.out.println(decimalno);
				
			}
		}
		
	

	public static void main(String[] args) {
		new BinaryTranslator();
	}

}


