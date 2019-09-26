import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class contains the dynamically allocated array and it's processing
 * Name: Jo Suh
 *
 */
public class Numbers {
	/**
	 * Stores Float values.
	 */
	private Float [] numbers;
	
	/**
	 * Store the number of items currently in the array.
	 */
	private int numItems;
	/**
	 * Whether the array of values has a set limit or not
	 */
	private boolean setSize;

	/**
	 * Default Constructor
	 */
	public Numbers() {
		// initial constructor
		numItems= 0;
		setSize= false;
		numbers = new Float[numItems];
	}

	/**
	 * Constructor that initializes the numbers array.
	 * @param size - Max size of the numbers array
	 */
	public Numbers(int size) {
		// initial constructor using an int parameter to set size of the array
		numItems= 0;
		setSize= true;
		numbers = new Float[size];
	}
	

	
	/**
	 * Adds values in the array
	 * @param keyboard - Scanner object to use for input
	 * @param addingMultiple - boolean to check if the user is entering in multiple values
	 */
	public void addValues(Scanner keyboard, boolean addingMultiple) {
		// prompt the user to specify how many values they want to enter in
		// If there is an incorrect input on the 2nd, 3rd... number,
		// the correct values entered before it would still be placed inside the array
		
		int amountOfNumbers= 1; //by default, 1
		
		
		try {
			//check if the array is full
			if (setSize && numItems == numbers.length ) {
				//there has to be a set size, more than 1 item then check if the last item is full
				System.out.println("Array full");
				return;
			}
			//if space left in the array
			if (addingMultiple) {
				System.out.print("How many values do you wish to add? ");
				//check the string for whitespaces
				String userInput =  keyboard.nextLine(); //use nextLine to get the spaces
				if (userInput.isEmpty() || Integer.valueOf(userInput)<1) {
					throw new InputMismatchException(); //invalid number
				}
				amountOfNumbers = Integer.valueOf(userInput);
				
				if (setSize && (numItems + amountOfNumbers) > numbers.length) {
					//if there is no room
					System.out.println("No room in array to add all values");
					return;
				}
			}

			for( int i=0; i<amountOfNumbers; i++) {
				addValue(keyboard);
			}
				
		}catch (InputMismatchException x){
			System.out.println("Please enter a valid number");
		} catch (NumberFormatException x) {
			System.out.println("Please enter a number");
		}
	}
	/**
	 * Adds values in the array
	 * @param keyboard - Scanner object to use for input
	 * @param fileName - A file name input
	 */
	public void addValues(Scanner keyboard, String fileName) {
		// prompt the user to specify how many values they want to enter in
		// If there is an incorrect input on the 2nd, 3rd... number,
		// the correct values entered before it would still be placed inside the array
		
		int amountOfNumbers;
		BufferedReader readInput = null; //set to default start null
		try {
			//adding values from the text file
			File inputTextFile = new File(fileName);
		
			if (!inputTextFile.exists() || inputTextFile.length()==0) {
				System.out.println("Error: File does not exist or is empty");
				return;
			}
			
			//open the input file
			
			readInput = new BufferedReader(new FileReader(inputTextFile));

			// The first value will be the number of floats stored in the file
			// followed by float values in each line

			
			amountOfNumbers= Integer.valueOf(readInput.readLine());
			if (setSize && (numItems + amountOfNumbers) > numbers.length) {
				//if there is no room
				System.out.println("No room in array to add all values");
				throw new InputMismatchException();
			}else if (amountOfNumbers<0) {
				//if negative number of floats in file
				System.out.println("Invalid file data");
				throw new InputMismatchException();
			}else{
				for (int i=0; i< amountOfNumbers; i++) {
					// read next line
					Float eachNum = Float.valueOf(readInput.readLine());
					addValue(keyboard, new Float[] {eachNum}); //add as an array of float
				}
			}
			
			System.out.println("Successfully read values from input");
			
		}catch (InputMismatchException x){
			x.printStackTrace();
			System.out.println("Error reading from file");
		}catch (IOException x){
			x.printStackTrace();
			System.out.println("Error finding file");
		} 
		
		try {
			readInput.close();
		} catch (IOException x) {
			System.out.println("Error closing file");
		}
	}
	
	/**
	 * Adds a value in the array
	 * @param keyboard - Scanner object to use for input
	 */
	public void addValue(Scanner keyboard, Float ...nextFileData) throws InputMismatchException, NumberFormatException{
		// will prompt the user to enter float values to fill the array.
		
		Float enteredVal;


		//if (nextFileData[0]==null) {
		if (nextFileData.length==0) {
			//adding value from console
			System.out.print("Enter value: ");
			//check the string for whitespaces
			String userInput =  keyboard.nextLine(); //use nextLine to get the spaces
			if (userInput.isEmpty()) {
				throw new InputMismatchException(); //invalid number
			}
			enteredVal = Float.valueOf(userInput);
		}else {
			//reading values from file
			enteredVal= nextFileData[0];
			if (enteredVal.equals(null)) {
				throw new InputMismatchException(); //invalid number
			}
		}
		
		if (setSize) {
			numbers[numItems] = enteredVal;
		}else {
			//need to increase the array size
			Float[] oldArray = numbers;
			numbers= new Float[numItems+1];
			
			//add the old array's values into the new one
			for (int i=0; i < numItems; i++) {
				numbers[i]= oldArray[i];
			}
			//finally add the last value
			numbers[numItems]= enteredVal;
		}
		numItems+=1; //if successful, increase the number of floats by 1 each
	}
	
	/**
	 * Calculates the average of all the values in the numbers array.
	 * @return float value that represents the average
	 */
	public float calcAverage() {
		// will return a float which is a average of the values in the array
		
		//check if empty array
		if (numItems == 0) {
			//just return a zero value
			return (float) 0.0;
		}
		
		float sum = 0;
		for (float each : numbers) {
			sum+=each;
		}
		
		float avg = (float) (sum/numItems);
		return avg;
	}
	
	
	/**
	 * sorts the array through insertion sort
	 */
	public void sortArray() {
		// sorts the array through insertion sort
		// which is a horrible method but this is just for learning purposes

		if (numItems<2) {
			//no need to sort
			return;
		}
		
		try {
			// Move every element in array to correct position
			for (int currentIndex=1; currentIndex<numItems; currentIndex++) {
				// count back from the index and compare each position from greatest to least

				Float eachElement = numbers[currentIndex]; // save a copy of the value
	            int indexBefore = currentIndex-1;  
	            while ( (indexBefore >= 0) && (eachElement < numbers[indexBefore]) ){  
	            	//for every element before the current element, if the current element is less
	            	//move up the elements
	            	numbers[indexBefore+1] = numbers[indexBefore];  
	            	indexBefore--;  
	            }  
	            numbers[indexBefore+1] = eachElement;  //finally add the original element to correct index
			}
			System.out.println("Sorted the array successfully");
			
		} catch (NullPointerException x) {
			System.out.println("Error in array: cannot sort");
		}
		
		
		
	}
	

	@Override
	public String toString() {
		// will return a String of the values in the array
		String values= "";
		
		for (int each=0; each<numbers.length; each++) {
			if (numbers[each].equals(null)) {
				//if there is nothing in the array, don't add anything to the string
				//break out of for loop
				break;
			}
			values += String.valueOf(numbers[each]) + "\n";
		}
		
		return values;
	}
	
}
