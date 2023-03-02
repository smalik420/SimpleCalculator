import java.util.List;		// used by expression evaluator

/**
 *	<Description goes here>
 *
 *	@author	
 *	@since	
 */
public class SimpleCalc {
	
	private ExprUtils utils;	// expression utilities
	
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack


	private boolean codeRunning; //false when user hits q
	
	// constructor	
	public SimpleCalc() 
	{
		utils = new ExprUtils();
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		
		codeRunning = true;
	}
	
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!");
		
		while (codeRunning) 
		{
			runCalc();
		}
		
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() 
	{
		String userInput = Prompt.getString("\n");
		if (userInput.equalsIgnoreCase("h") )			printHelp();
		else if (userInput.equalsIgnoreCase("q") )	codeRunning = false;
		else
		{	
			List<String> expressionTokens = utils.tokenizeExpression(userInput);
			double answer = evaluateExpression(expressionTokens);
			System.out.println(answer);
		}
					
	}
	
	public boolean isStringDigit(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (Character.isDigit(str.charAt(i)) == false)
				return false;
		}
		return true;
	}
	
	
	
	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) 
	{
		double value = 0;
		
		int tPos = 0;
		for (int i = 0; i < tokens.size(); i++)
		{
			
			
		}
		
		
		
		while ( !operatorStack.isEmpty() )
		{
			String current = tokens.get(tPos);
			
			if (isStringDigit(current) )
			{
				valueStack.push(Double.parseDouble(current));
				tPos++;
			}
			else
			{
				if (operatorStack.isEmpty() )
				{
					operatorStack.push(current);
					tPos++;
				}
				else
				{
					//if true, then peek has higher or same precedence
					boolean peekPrecedence = hasPrecedence(current, operatorStack.peek());
					
					if (peekPrecedence)
					{
						double double1 = valueStack.pop();
						double double2 = valueStack.pop();
						String operatorStr = operatorStack.peek();
						operatorStack.pop();
						double answer = runMathEquation(double1, current, double2);
						System.out.println(answer);
						value += answer;
						
						tPos++;
					}
					
					operatorStack.push(current);
					tPos++;
					
					
				}
			}
		}

		return value;
	}
	
//	for (int i = 0; i < expressionTokens.size(); i++) {
//				System.out.println(expressionTokens.get(i));
//	}
	
	public double runMathEquation(double val1, String operate, double val2)
	{
		if (operate.equals("+") )
		{
			return val1 + val2;
		}
		else if (operate.equals("-") )
		{
			return val1 - val2;
		}
		else if (operate.equals("*") )
		{
			return val1 * val2;
		}
		else if (operate.equals("/") )
		{
			return val1 / val2;
		}
		else
		{
			System.out.println("ERROR OCCURED check runMathEquation");
			return 0;
		}
	}
	
	
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
	 
}
