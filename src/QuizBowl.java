package src;

import java.util.*;
import java.io.*;

/**
 * Class QuizBowl that uses a file to play the quiz
 * @author Raghavi Sakpal, Monika Sobolewska
 * @version Autumn 2018
 */

public class QuizBowl   {

	/** Read questions from the file and store in ArrayList.
	 * @param args contains the file name
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException  {
                    
		Scanner console = new Scanner(System.in);                    // Scanner to read user input from console
		int numQues;			// number of questions in the file
		int playNumQues;		// number of questions a player wants to play
		String filename = "sample.txt";
		
		ArrayList<AbstractQuestion> questionBank = fillQuestionBank(filename);  // ArrayList of questions to be read from file

		//System.out.print(questionBank); // verify the read

		System.out.println("How many questions would you like?");

		// Accept number of questions player wants to play 
		playNumQues = console.nextInt();
		console.nextLine();       // Discard the rest of the line

		// Method Call: Play the quiz
		playQuizBowl(console,questionBank,playNumQues);
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
	public static void playQuizBowl(Scanner input, ArrayList<AbstractQuestion> qBank, int playNumQues)   {
		int quesNo;                                                 // Variable for question number

		// Loop till player number of questions
		for(int i = 0; i < playNumQues; i++)  {
			
			// Display the question and accept user answer
			AbstractQuestion q = qBank.get(i);
			System.out.println(q.getQuestion());
			String playerAns = input.nextLine();

			if(q.isCorrect(playerAns)) {   // Check if the user answer is correct
				System.out.println("Correct! You get " + q.getNumPoints() + " points.");
			}
			else {      // if the user answer is incorrect
				System.out.println("Incorrect! You lose " + q.getNumPoints() + " points.");
			}

			System.out.println();
		} 
	}
}