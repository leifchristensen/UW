import java.util.Scanner;

// email to bbyrd227@uw.edu by 7:50pm on Oct 5


public class LetterGrade {

	public static void main(String[] args) {
		
		boolean continueToInput = true;
				
		while(continueToInput) {
			giveIntro();
			Student student = inputPrompt(); //Parses user input into Student object
			student.calculateFinalGrade(student.calculateFinalScore()); //Calculates grade letter
			student.displayStudentGrade();
			
			continueToInput = promptToContinue();
		}
		
		System.out.println("Goodbye.");
	}
	


	private static Student inputPrompt() {
		String LastName = new String();
		String FirstName = new String();
		double AvgExam = 0.0;
		double AvgProject = 0.0;
		double AvgLab = 0.0;
		double AvgHW = 0.0;
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Last Name: ");
		LastName = input.next();
		System.out.print("First Name: ");
		FirstName = input.next();
		System.out.print("Exam Avg.: ");
		AvgExam = getInputAvg(input,"Exam Avg.: ");
		System.out.print("Project Avg.: ");
		AvgProject = getInputAvg(input,"Project Avg.: ");
		System.out.print("Lab Avg.: ");
		AvgLab = getInputAvg(input,"Lab Avg.: ");
		System.out.print("Homework Avg.: ");
		AvgHW = getInputAvg(input,"Homework Avg.: ");
		
		Student student = new Student();
		student.LastName = LastName;
		student.FirstName = FirstName;
		student.AvgExam = AvgExam;
		student.AvgProject = AvgProject;
		student.AvgLab = AvgLab;
		student.AvgHW = AvgHW;
		
		return student;
	}

	private static double getInputAvg(Scanner input, String prompt) {
		double value = isDouble(input, prompt);
		while (value < 0 || value >= 100) {
			System.out.println("Out of range; try again.");
			value = isDouble(input, prompt);
		}
		return value;
	}
	
	private static double isDouble (Scanner input, String prompt) {
		while (!input.hasNextDouble()) {
			input.next();
			System.out.println("Not an valid score; please try again.");
			System.out.print(prompt);
		}
		return input.nextDouble();
	}

	/**
	 * Displays an introduction message to the user.
	 * 
	 */
	private static void giveIntro() {
		System.out.println("FINAL GRADE CALCULATION");
		System.out.println("Please enter the folowing information:");
		System.out.println();
	}
	
	private static boolean promptToContinue() {
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.println("Do you want to enter another student? /n Press y for yes or n for no");
			
			String choice = input.next();
			
			switch (choice) {
			case "y":
			case "Y":
				return true;
			case "n":
			case "N":
				return false;
			default:
				System.out.println("Input not recognized.");
				break;
			} 
		}
		
	}

}

class Student {
	String LastName;
	String FirstName;
	double AvgExam;
	double AvgProject;
	double AvgLab;
	double AvgHW;
	double score;
	char Grade;
	
	final double WEIGHT_EXAM = 0.5;
	final double WEIGHT_PROJECT = 0.25;
	final double WEIGHT_LAB = 0.15;
	final double WEIGHT_HW = 0.10;
	
	public Student() {
		LastName = new String();
		FirstName = new String();
		AvgExam = 0.0;
		AvgProject = 0.0;
		AvgLab = 0.0;
		AvgHW = 0.0;
	}
	
	double calculateFinalScore() {
		double total = 	this.AvgExam 	* WEIGHT_EXAM +
						this.AvgProject * WEIGHT_PROJECT +
						this.AvgLab		* WEIGHT_LAB +
						this.AvgHW		* WEIGHT_HW;
		this.score = total;
		return this.score;
	}
	char calculateFinalGrade(double weightedAverage) {	
		if (weightedAverage >= 93) {
			this.Grade = 'A';
		} else
		if (weightedAverage >= 83) {
			this.Grade = 'B';
		} else
		if (weightedAverage >= 73) {
			this.Grade = 'C';
		} else
		if (weightedAverage >= 63) {
			this.Grade = 'D';
		} else {
			this.Grade = 'F';
		}
		return this.Grade;
	}
	
	void displayStudentGrade() {
		System.out.println(
				"Last Name /t/t First Name /t/t Final Score /t/t Letter Grade"
				);
		System.out.println(
				this.LastName + "/t/t" + this.FirstName + "/t/t" + this.score + "/t/t" + this.Grade
				);
	}
}
