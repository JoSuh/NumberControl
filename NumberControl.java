import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * This class contains the dynamically allocated array and it's processing
 * Name: Jo Suh
 *
 */
public class NumberControl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// to test the code wrote in the Numbers class
		// uses the helper method to display the menu and should loop until the user wishes to quit
		// Inside the loop it should ask user how many assessments in the course – read in the due dates for those assessments and then print them;
		// then add one to each of the assessment dates, and print them. Add code to process all items in the menu.
		
		Scanner input = new Scanner(System.in);
		
		NumberControl sample = new NumberControl();
		Numbers numbers = new Numbers();
		

		String textFileRegex =   "^([^\\s]+)(\\.(?i)txt)$";
		String PATH = "C:\\Users\\User\\eclipse-workspace\\NumberControl\\src\\";
		
		
		// regex information referenced from https://www.vogella.com/tutorials/JavaRegularExpressions/article.html
		/*
			 $ = checks if a line end follows
			 [^\\s]+ = any characters except whitespace
			 \\. = dot
			 (?i) = ignore case checking for the following
			 \w = any word character = [a-zA-Z]
			 + = finds one or several
			 {x} = occurs x times only
		 */
		
		
		//display the main menu console while the user has not exitted
		program: while(true) {
			int userOption=0;
			
			//show options
			sample.displayMainMenu();
			
			//get user input
			try {
				//check the string for whitespaces
				String userInput =  input.nextLine(); //use nextLine to get the spaces
				
				if (userInput.isEmpty()) {
					throw new InputMismatchException(); //invalid number
				}
				userOption = Integer.valueOf(userInput);
				
				//check if the number is in the correct range
				int maxOption = 10, minOption = 1;
				if (userOption > maxOption || userOption < minOption) {
					throw new InputMismatchException(); //invalid number
				}
			} catch (InputMismatchException x) {
				System.out.println("Please enter a valid integer");
				continue program;
			} catch (NumberFormatException x) {
				System.out.println("Please enter an integer");
				continue program;
			}
			
			
			// if acceptable input, perform the corresponding actions
			switch(userOption) {
			
			case 1:
				//initialize a default array
				numbers = new Numbers();
				break;
				
				
			case 2:
				//specify the max size of the array
				//get size
				Integer initialSize;
		
				System.out.print("Enter new size of array: ");
				//get user input
				try {
					//check the string for whitespaces
					String userInput =  input.nextLine(); //use nextLine to get the spaces
					if (!(new File(PATH)).exists()) {
						throw new java.util.NoSuchElementException();
					}
					if (userInput.isEmpty()) {
						throw new InputMismatchException(); //invalid number
					}
					initialSize = Integer.valueOf(userInput);
					//check if the number is in the correct range
					if (initialSize<=0 || initialSize.equals(null)) {
						throw new InputMismatchException(); //invalid number
					}
					numbers = new Numbers(initialSize);
				} catch (InputMismatchException x) {
					System.out.println("Please enter a valid integer bigger than 1");
				} catch (NumberFormatException x) {
					System.out.println("Please enter an integer");
				} catch (java.util.NoSuchElementException x) {
					System.out.println("Invalid file path");
				}
				
				break;
				
				
			case 3:
				// Add value to the array
				numbers.addValues(input, false);
				break;
				
				
			case 4:
				// Display values in the array
				System.out.println("Numbers are: \n" + numbers.toString());
				break;
					
				
			case 5:
				// Display the average of the values
				System.out.println("Average is: " + numbers.calcAverage());
				break;
				
			case 6:
				//Enter multiple values
				numbers.addValues(input, true);
				break;
				
			case 7:
				//Read values from file
				System.out.println("Name of the file to read from: ");
				
				try {
					String userInput = input.nextLine();
							
					//check valid text file format
					if ( !Pattern.matches(textFileRegex, userInput) ) {
						throw new InputMismatchException();
					}else {
						// addValues(Scanner keyboard, boolean addingMultiple, String ...fileName)
						numbers.addValues(input, (PATH + userInput));
						
					}	
					
					
				} catch (InputMismatchException x) {
					//may occur if the file is missing or does not have access to
					System.out.println("Invalid text file format");
				} catch (java.util.NoSuchElementException x) {
					//invalid file
					System.out.println("Invalid file");
				}catch (NumberFormatException x) {
					x.printStackTrace();
					System.out.println("An error occurred.");
				}
				
			/*
					lab2.txt
				change the method so that it takes in a boolean (true/false) parameter to
				specify when it is true that you are reading from a Scanner file
				(and in this case don’t display the messages)
				or when it is false, that you want the messages displayed.
				*/
				  
				break;
				
			case 8:
				//Save values to file
				System.out.println("Name of the file to save to: ");
				try {
					String userInput = input.nextLine();
							
					//check valid text file format
					if ( !Pattern.matches(textFileRegex, userInput) ) {
						throw new InputMismatchException();
					}else {
						File outputTextFile = new File(PATH + userInput);

						if (!outputTextFile.exists()) {
							outputTextFile.createNewFile();
							System.out.println("Creating file...");
						}else {
							System.out.println("Adding to file...");
						}
						
						//open the output file
						FileWriter writer = new FileWriter(outputTextFile, true);
						PrintWriter fileOutput = new PrintWriter(writer, true); //set true for append mode
						
						
						int numberOfFloats = 0; //by default; if empty file
						Float[] savedFloats = new Float[0];

						
						//construct a new Scanner that produces values scanned from the specified file
						BufferedReader readFileOutput = new BufferedReader(new FileReader(outputTextFile));

						if(outputTextFile.length()> 0) {
							//if file already has components
							numberOfFloats= Integer.valueOf(readFileOutput.readLine());

					        savedFloats= new Float[numberOfFloats];

					        //copy the floats in the file to an array
					        for (int i=0; i<numberOfFloats; i++) {
								//since the first value will be the integer of the number of floats, emit out
					        	savedFloats[i]= Float.valueOf(readFileOutput.readLine());
					        }
					        readFileOutput.close();
						}

						
						String currentFloatString = numbers.toString().trim();
						String[] currentFloats;
						
						if(currentFloatString.isEmpty()) {
							//check if the string is empty
							currentFloats= new String[0];
						}else {
							currentFloats= currentFloatString.split("\n");
						}

						//write how many floats are in the file
						//This replaces all the other texts in the file
						fileOutput.println( (numberOfFloats+currentFloats.length));
						
						//add in the saved data first
						for (int i=0; i<numberOfFloats; i++) {
							fileOutput.println(savedFloats[i]);
				        }
						//Then add the current data
						for (int i=0; i<currentFloats.length; i++) {
							fileOutput.println(currentFloats[i]);
				        }
						
						//close the output file
						if ( outputTextFile != null ) {
							//need to check for not null so that no exceptions are thrown
							//from a result of an attempt to perform a method on a null value
							fileOutput.close();
						}
					}
					System.out.println("Saved  file successfully");
				
				}catch (InputMismatchException x) {
					System.out.println("Invalid text file format");
				}catch (Exception x) {
					x.printStackTrace();
					System.out.println("Error ocurred");
				}
				break;
			
			case 9:
				// Sort Array
				numbers.sortArray();
				break;
				

			case 10:
				// Exit
				System.out.println("Exiting...");
				break program;
				
			}
			
		}
	}
	
	public void displayMainMenu() {
	// Outputs the main menu to the console (standard output)
			/*
			Please select one of the following:
			1: Initialize a default array
			2: To specify the max size of the array
			3: Add value to the array
			4: Display values in the array
			5: Display the average of the values
			6: To Exit
			> 
			*/
		
		System.out.print("Please select one of the following:" + "\n"
							+ "1: Initialize a default array" + "\n"
							+ "2: To specify the max size of the array" + "\n"
							+ "3: Add value to the array" + "\n"
							+ "4: Display values in the array" + "\n"
							+ "5: Display the average of the values" + "\n"
							+ "6: Enter multiple values" + "\n"
							+ "7: Read values from file" + "\n"
							+ "8: Save values to file" + "\n"
							+ "9: Sort array" + "\n"
							+ "10: To Exit" + "\n"
							+ "> ");
		
	}

}

