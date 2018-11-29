package src;

import java.util.*;
import java.io.*;

/**
 * Class QuizBowl that uses a file to play the quiz
 * @author Raghavi Sakpal, Monika Sobolewska, additions by Leif Christensen
 * @version Autumn 2018
 */

public class QuizBowl   {

	/** Prompts a user for a name and number of questions. 
	 * Pulls questions from sample.txt and asks for responses. 
	 * User responses are checked against an answer bank and the results are displayed.
	 * @param args contains the file name to override the default of sample.txt
	 * @throws FileNotFoundException If sample.txt or filename provided in args is not found.
	 */
	public static void main(String[] args) throws FileNotFoundException  {
                    
		Scanner console = new Scanner(System.in);                    // Scanner to read user input from console
		int numQues;			// number of questions in the file
		int playNumQues = 0;		// number of questions a player wants to play
		String filename = "sample.txt";
		
		// Grabs new filename if args is populated
		if(args.length > 0) {
			filename = args[0];
		}
		
		ArrayList<AbstractQuestion> questionBank = fillQuestionBank(filename);  // ArrayList of questions to be read from file

		//System.out.print(questionBank); // verify the read
		
		// Sets maximum number of questions
		numQues = questionBank.size();
		
		// Read first and last name of player
		String firstName = "";
		String lastName = "";
		
		System.out.println("What is your first name?");
		firstName = console.nextLine();
		System.out.println("What is your last name?");
		lastName = console.nextLine();
		
		Player player = new Player(firstName, lastName);

		

		// Accept number of questions player wants to play		
		do {
			System.out.println("How many questions would you like? (out of " + numQues + ")");
			int inputNum = -1;
			while(!console.hasNextInt()) {  // tests if input is an int
				System.out.println("Sorry, that is not valid. Please enter an integer");
				console.next(); // Consumes input queue to clear for next user input
				
			}
			
			inputNum = console.nextInt();  // grabs the next int. 
			
			if(inputNum <=0 ) { // Tests negative nums
				System.out.println("Please enter a positive number");
			}
			if(inputNum > numQues) { //Tests out of bounds
				System.out.println("Too many questions, select maximum of " + numQues);
			}
			
			playNumQues = inputNum;
			console.nextLine(); // Clears console queue
			
		} while (playNumQues < 0 || playNumQues > numQues);
		

		// Method Call: Play the quiz
		playQuizBowl(console,questionBank,playNumQues, player);
		
		// Calculate total score percent
		int totalPossible = 0;
		for( int i = 0; i < playNumQues; i++) {  // Gets total possible points for each question in question bank up to the number of questions selected.
			totalPossible += questionBank.get(i).getNumPoints();
		}
		double scorePercent = (double) player.getPoints() / totalPossible;
		
		// Print results
		System.out.println(player.getFirstName() + " " + player.getLastName() + ", your game is over!");
		System.out.println("Your final score is " + player.getPoints() + " points.");
		if(scorePercent < 0) 
			System.out.println("Hmmm, are you sure you want to be on the team?");
		else if(scorePercent < 0.60) 
			System.out.println("Not bad, but you need to study more.");
		else if(scorePercent >= 0.90) 
			System.out.println("Way to go! You are a true QuizBowler!");
		else
			System.out.println("Good Progress");
	}
	
	



	/** Read questions from the file and store in ArrayList.
	 * @param filename is the name of the file to read from
	 * @return ArrayList with questions read from the file
	 * @throws FileNotFoundException if there are problems with the file
	 */
	public static ArrayList<AbstractQuestion> fillQuestionBank(String filename) throws FileNotFoundException  {

		// Scanner to read from the file
		Scanner input = new Scanner(new File(filename));
		// Read the total number of questions from the file
		int numQues = input.nextInt();
		ArrayList<AbstractQuestion> qBank = new ArrayList<AbstractQuestion>(numQues);
		
		// Loop till the end of the file
		while(input.hasNextLine()) {
			String quesType =  input.next();
			int quesPoints = input.nextInt(); 
			input.nextLine();       // Discard the rest of the line 
			String question = input.nextLine();
			
			if(quesType.equals("SA")) {
				String answer = input.next();
				qBank.add(new QShortAnswer(question,quesPoints,answer));
			}
			else {
				int numChoices = input.nextInt();
				input.nextLine();       // Discard the rest of the line
				ArrayList<String> ansChoices = new ArrayList<String>(numChoices);
				// Loop through all answer choices
				for(int i = 1; i <= numChoices; i++)  {
					ansChoices.add(input.nextLine());
				}
				String answer = input.next();
				if(quesType.equals("MC"))
					qBank.add(new QMultipleChoice(question,quesPoints,answer,ansChoices));
				else if(quesType.equals("MA"))
					qBank.add(new QMultipleAnswer(question,quesPoints,answer,ansChoices, ansChoices));
				else if(quesType.equals("MD"))
					qBank.add(new Q2DropDowns(question,quesPoints,answer,ansChoices));
				else
					qBank.add(new QMatching(question,quesPoints,answer,ansChoices));
			}
		}
		return qBank;
	}


	/** Plays the quiz.
	 * @param input is the console ready for input
	 * @param qBank contains all the questions
	 * @param playNumQues is the number of questions to ask > 0
	 */
	public static void playQuizBowl(Scanner input, ArrayList<AbstractQuestion> qBank, int playNumQues, Player player)   {
		int quesNo;                                                 // Variable for question number

		// Loop till player number of questions
		for(int i = 0; i < playNumQues; i++)  {
			
			// Display the question and accept user answer
			AbstractQuestion q = qBank.get(i);
			System.out.println(q.getQuestion());
			String playerAns = input.nextLine();

			if(playerAns.equals("SKIP")) { // Skips question without modifying points or checking for correct answer.
				System.out.println("You have elected to skip that question.");
			}
			else if(q.isCorrect(playerAns)) {   // Check if the user answer is correct
				System.out.println("Correct! You get " + q.getNumPoints() + " points.");
				player.incrementPoints(q.getNumPoints()); // Increments total points of player
			}
			else {      // if the user answer is incorrect
				System.out.println("Incorrect! You lose " + q.getNumPoints() + " points.");
				player.incrementPoints(q.getNumPoints() * -1); // Decrements total points of player
			}

			System.out.println();
		} 
	}
}