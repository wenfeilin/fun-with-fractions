import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Evaluates expressions and store values (integers and fractions), like a calculator, 
 * interactively.
 *
 * @author Wenfei Lin
 */
public class InteractiveCalculator {
  public static void main (String[] args) {
    BFCalculator Calculator = new BFCalculator();
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner sc = new Scanner(System.in);
    String line = "";

    while ((!line.equals("QUIT"))) { // Keep running the program as long as QUIT is not inputted
      // Take in a line of user input
      line = sc.nextLine(); 
      // Separate the expression's operands, operators, and registers
      String[] exprParts = line.split(" "); 

      // If nothing is inputted or there was extra whitespace at the beginning on an expression,
      if (exprParts[0].equals("")) { 
        // Print an error message and the prerequisites for input format, then exit the program
        errorPrinter.printf("No input given or incorrect use of whitespace.\n" + //
            "To evaluate an expression, make sure the expression is a \"sequence of values " + //
                "(integers, fractions, or register names) separated by " + // 
                "spaces and operators.\"\n" + //
            "To store a value, enter `STORE register` where `register` is a " + // 
                "letter of the alphabet.\n" + //
            "To quit the program, enter `QUIT`.");
        System.exit(5);
      } else if (exprParts[0].equals("STORE")) { // Storing a value in a register
        storeAValue(Calculator, errorPrinter, exprParts);
      } else { // Evaluating an expression
        evaluateExpression (Calculator, errorPrinter, pen, exprParts, line);
      }
    } 
    // Close both open streams as they're no longer used
    pen.close();
    sc.close();
  } // main(String[])

  /**
   * Stores the last result in a specified register.
   * 
   * @pre exprParts is not empty, exprParts.length == 2, and exprParts[0] = "STORE"
   */
  public static void storeAValue(
        BFCalculator Calculator, PrintWriter errorPrinter, String[] exprParts) {
    // The second element of a STORE expression will always be the register
    String registerStr = exprParts[1];

    if (registerStr.length() == 1) { // If the register is a character,
      // store the last result in the register
      Calculator.store(registerStr.charAt(0));
      if (Calculator.result.toString().equals("undefined")) {
        // Storing an undefined value is not allowed, so print an error message
        // and exit the program
        errorPrinter.println("There is nothing to be stored right now. " + //
            "Calculate an expression that does not evaluate to an undefined value first, " + //
            "then try storing again.");
        System.exit(3);
      }
    } else { // Otherwise, the register is a word, which is not a valid register
      // Print an error message and exit the program
      errorPrinter.println(registerStr + " is not a valid register. " + // 
          "The register must be a letter of the alphabet.");
      System.exit(1);
    }
  } // storeAValue(BFCalculator, PrintWriter, String[])

  /**
   * Evaluates an expression of any number of elements
   * 
   * @pre line is not empty and exprParts is not empty
   * @post prints output of expression given in line
   */
  public static void evaluateExpression(
        BFCalculator Calculator, PrintWriter errorPrinter, 
        PrintWriter pen, String[] exprParts, String line)  {
    // Start from the beginning of the expression
    int firstOperandIndex = 0;
    int operatorIndex = 1;
    int secondOperandIndex = 2;

    // If the expression has only one element, and that is not QUIT,
    if (exprParts.length == 1 && !(exprParts[0].equals("QUIT"))) {
      // Evaluate that one element on its own and print the result
      String expr = exprParts[firstOperandIndex];
      Calculator.evaluate(expr);
      // If the element was a valid operand, then the result printed will either be
      // the stored value of the register or the original fraction or integer
      pen.println(Calculator.result);
    } else if (exprParts.length % 2 == 0) { 
      // If the number of elements is even, then there must be a missing operator 
      // or operand (incorrect form), then print the error message and exit the 
      // program
      errorPrinter.println("Missing operator or operand.");
      System.exit(6);
    } else { 
      // Otherwise, the number of elements in the expression is at least 3 and is odd and
      // while the end of the expression has not been reached,
      while (secondOperandIndex <= exprParts.length - 1) {
        // Break the expression up into expressions with three elements
        if (operatorIndex == 1) { // Starting from the first three-elements expression
          // Remake those elements into a string to be evaluated
          String expr = exprParts[firstOperandIndex] + " " + exprParts[operatorIndex] + " " + 
              exprParts[secondOperandIndex];
          Calculator.evaluate(expr);
        } else { // For the latter three-elements expression(s), if there are any,
          // the implicit first operand is the result of the very first three-element expression,
          // so rebuild this next expression into a string to be evaluated
          String expr = Calculator.result + " " + exprParts[operatorIndex] + " " + 
              exprParts[secondOperandIndex];
          Calculator.evaluate(expr);
        }
        // Increment the indices for the operator and second operand to cover all 
        // of the smaller expressions in the whole expression being calculated
        operatorIndex += 2;
        secondOperandIndex += 2;
      }
      
      // As long as the user doesn't quit the program,
      if (!line.equals("QUIT")) {
        // Print the result of each expression 
        pen.println(Calculator.result);
      }
    }
  } // evaluateExpression(BFCalculator, PrintWriter, PrintWriter, String[], String)
} // class InteractiveCalculator