/*Author - Parth Nimbalkar
 * Date - 12/1/2023
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Sorting {

	Scanner consoleInput = new Scanner(System.in);
	String input;
	Scanner fileInput;
	int[] inputArray;
	long startTime;
	
	public Sorting() {
		System.out.println("Enter a number for the input file.");
		System.out.println("1: input1.txt  2:input2.txt  3:input3.txt  4:input4.txt");
		input = consoleInput.nextLine();
		if (input.length()!= 1 && input.charAt(0)!= '1' && input.charAt(0)!= '2'
				&& input.charAt(0)!= '3' && input.charAt(0)!= '4') {
			System.out.println("Enter 1, 2, 3, or 4");
			while(input.length()!=1 && input.charAt(0)!= '1' && input.charAt(0)!= '2' 
					&& input.charAt(0)!= '3' && input.charAt(0)!= '4') {
				input = consoleInput.nextLine();
			}
		}
		try {
			fileInput = new Scanner(new File("input" + input.charAt(0)+ ".txt"));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		String infile = fileInput.nextLine();
		String [] inputStringArray = infile.split (",");
		inputArray = new int[inputStringArray.length];
		for (int i = 0; i < inputStringArray.length; i++) {
			inputArray[i] = Integer.parseInt(inputStringArray[i]);
			System.out.println(inputArray[i]);
		}
		System.out.println("Enter a number for the input file.");
		System.out.println("1:Bubble  2:Selection  3:Table  4:Quick");
		input = consoleInput.nextLine();
		if (input.length()!= 1 && input.charAt(0)!= '1' && input.charAt(0)!= '2'
				&& input.charAt(0)!= '3' && input.charAt(0)!= '4') { //
			System.out.println("Enter 1, 2, 3, or 4");
			while(input.length()!=1 && input.charAt(0)!= '1' && input.charAt(0)!= '2' 
					&& input.charAt(0)!= '3' && input.charAt(0)!= '4') {
				input = consoleInput.nextLine();
			}
		}
		
		startTime = System.currentTimeMillis();
		if(input.equals("1")) {
			inputArray = bubbleSort(inputArray);
		}
		if (input.equals ("2")) {
			inputArray = selectionSort (inputArray);
		}
		if (input.equals ("3")) {
			inputArray = tableSort (inputArray);
		}
		if (input.equals ("4")) {
			quicksort(inputArray, 0, inputArray.length - 1);
		}
		long totalTime = System.currentTimeMillis() - startTime; //Shows the amount of time(milliseconds) it took to sort the array
		System.out.println("Total time:" + totalTime);
		
		try {
			PrintWriter pw = new PrintWriter(new FileWriter( new File("output.txt")));
			String output = "";
			for (int i = 0; i < inputArray.length; i++) {
				output += inputArray[i] + ",";
			}
			output += "\nTotal Time:" + totalTime;
			pw.write(output);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	//compare each pair of numbers and move the larger to the right
	int[] bubbleSort (int[] array) {
		for (int j = 0; j < array.length; j++) {
		
			for (int i = 0; i < array.length - 1; i++) {
				//if the one on the left is larger
				if (array[i] > array [i+1]) {
					//swap
					int temp = array[i];
					array[i] = array[i+1];
					array[i+1] = temp;
					
					}
				}
			}
			return array;
	}
	
	int[] selectionSort(int[] array) {
		for (int j = 0; j < array.length; j++) {
			int smallestNumber = array[j];
			int smallestIndex = j;
			for (int i = j; i < array.length; i++) {
				if (array[i] < smallestNumber) {
					smallestNumber = array[i];
					smallestIndex = i;
				}
			}	
			int temp = array[smallestIndex];
			array[smallestIndex] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	//Tally how often you see each number, print out that number of times
	int[] tableSort(int[] array) {
		
		int[] tally = new int[1001];
		for (int i = 0; i < array.length; i++) {
			tally[array[i]]++;
		}
		
		int count = 0;
		//i keeps track of the actual number
		for (int i = 0; i < tally.length; i++) {
			//j keeps track of how many times we've seen that number
			for (int j = 0; j < tally[i]; j++) {
				array[count] = i;
				count++;
			}
		}
		
		return array;
	}
	public static void quicksort(int[] arr, int lowIndex, int highIndex) {
		
		if (lowIndex < highIndex) {
			int partition = findPartition(arr, lowIndex, highIndex);
			
			//quicksort on the left side
			quicksort(arr, lowIndex, partition - 1);
			
			//quicksort on the right side
			quicksort(arr, partition + 1, highIndex);
		}
	}
	
	private static int findPartition(int[] arr, int lowIndex, int highIndex) {
		int pivot = arr[highIndex]; //pivot is taken as the highIndex value
		
		int i = (lowIndex - 1); //index of the smaller element
		
		for (int j = lowIndex; j < highIndex; j++) {//This will go over the each index value when the j value is less than the highIndex because the highIndex is the pivot value
			// if current element is smaller than the pivot
			if (arr[j] <= pivot) {
				i++; //if j is greater then the pivot then it move to the next element and i will swap with j
				
				//swap arr[i] and arr[j]
				swap(arr, i, j);
			}
		}

		
		swap(arr, i + 1, highIndex);
		/*System.out.println(i + 1);*/
		
		return i + 1;
	}
	
	


	
	private static void swap(int[] arr, int i, int j) {//swap for i and j
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	public static void main(String[] args) {
		new Sorting();

	}

}
