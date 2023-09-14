import java.io.PrintWriter;

/**
 * Evaluates expressions and store values like a regular calculator but specializing
 * in integer and fraction computations.
 *
 * @author Wenfei Lin
 */
public class BFCalculator {
  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The result from the most recent calculation of an expression. Can be positive, zero, 
   * negative, or undefined. 
   */
  BigFraction result;
  /** The record of all 52 (for A-Z and a-z) possible registers and any stored values in them. */
  BigFraction[] storeArr;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Builds a new calculator.
   */
  public BFCalculator() {
    // Start with an undefined result to signify that no calculations have been made yet.
    this.result = new BigFraction(0, 0);
    // Initialize size of whole store array for all possible registers.
    this.storeArr = new BigFraction[52];
  } // BFCalculator()

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Evaluates an expression of three elements.
   * 
   * @pre exp.length() == 3 or exp.length() == 1
   */
  public void evaluate(String exp) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    // Separate the expression's operands, operators, and registers
    String[] expressionParts = exp.split(" "); 
    String strOperand1 = expressionParts[0];

    if (expressionParts.length == 1) { // If there is only one element in the expression,
      if (checkValidOperand(strOperand1)) { // and if that element is an operand (not an operator),
        // then determine the numeric value of the operand, particularly if it's a register, 
        // and give that as the result for the computation of the expression
        this.result = determineOperandValue(strOperand1);
      } else { // otherwise, must be an operator or some other invalid input
        // Then print an error message and exit the program
        errorPrinter.println(strOperand1 + " is an invalid expression.");
        System.exit(7);
      }
    } else { // Otherwise, the expression contains multiple elements
      String strOperand2 = expressionParts[2]; 

      // There should be two operands (and one operator) in a short expression with three elements
      if (checkValidOperand(strOperand1) && checkValidOperand(strOperand2)) {
        // Check that both operands have an associated numeric value
        BigFraction operand1 = determineOperandValue(strOperand1);
        BigFraction operand2 = determineOperandValue(strOperand2);
        String operator = expressionParts[1];

        switch (operator) { // Depending on the operator,
          case "+" : 
            // Add the two operands 
            this.result = operand1.add(operand2);
            break;
          case "-" :
            // Subtract the first operand by the second operand 
            this.result = operand1.subtract(operand2);
            break;
          case "/" :
            // Divide the first operand by the second operand 
            this.result = operand1.divide(operand2);
            break;
          case "*" :
            // Multiply the first operand and the second operand 
            this.result = operand1.multiply(operand2);
            break;
          default : // In case the operator is not one of the four basic operators from above,
            // Print an error message and exit the program
            errorPrinter.println(operator + " is an invalid operator.");
            System.exit(2);
        }
      } else { // If one of the two operands is an operator, then this means two operators appear
        // in a row, and the form of the expression is incorrect
        // Print an error message and exit the program
        errorPrinter.println("Incorrect form: Two operators in a row.");
        System.exit(4);
      }
    }
    // Always simplify the result of the expression after obtaining it
    this.result = this.result.simplify();
  } // evaluate(String)

  /**
   * Stores the last result in the register `register`.
   */
  public void store(char register) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    int index = findRegisterIndex(register);

    if (index == -1) { // If the index is not a valid one, then it implies the register is not an 
      // alphabetic letter
      // Print the error to the user and exit the program
      errorPrinter.println(register + " is not a valid register. " + //
          "The register must be a letter of the alphabet.");
      System.exit(1);
    }
    // Otherwise, find the register in the stored values array and store the last computed result
    // in it
    this.storeArr[index] = this.result;
  } // store(char)

  /**
   * Determines if the register `register` is a capital letter.
   */
  public boolean isUpperRegister(char register) {
    return register >= 'A' && register <= 'Z';
  } // isUpperRegister(char)

  /**
   * Determines if the register `register` is a lowercase letter.
   */
  public boolean isLowerRegister(char register) {
    return register >= 'a' && register <= 'z';
  } // isLowerRegister(char)

  /**
   * Calculates the index of the register `register` in the array of stored values.
   */
  public int findRegisterIndex(char register) {
    int rebaseCapitalLetter = 65; 
    int rebaseLowerCaseLetter = 97; 
    int storeArrHalfWayPt = 26; 

    if (isUpperRegister(register)) { // If register is a capital letter,
      // To rebase capital letters to 0, subtract the ASCII code by 65
      return (int) register - rebaseCapitalLetter;
    } else if (isLowerRegister(register)) { // If register is a lowercase letter,
      // To rebase lowercase letters to 0, subtract the ASCII code by 97
      // To rebase lowercase letters to come after capital letters, rebase the lowercase letters
      // again but to 26
      return (int) register - rebaseLowerCaseLetter + storeArrHalfWayPt;
    } else { // If register is not an alphabetic letter,
      // Return an invalid index, -1
      return -1;
    }
  } // findRegisterIndex(char)
  
  /**
   * Determines what the numeric value of the operand `operand` should be for ease of calculation.
   * 
   * @pre operand should be a letter of the alphabet, an integer, or a fraction
   */
  public BigFraction determineOperandValue(String operand) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    // The first character of operand could be an integer (as part of an integer or a fraction) or
    // a register
    char possibleRegister = operand.charAt(0);

    // If the operand is a register, that needs to be replaced with the register's stored value 
    if (isUpperRegister(possibleRegister)) { // If possibleRegister is a valid register and is a 
      // capital letter,
      if (!(this.storeArr[findRegisterIndex(possibleRegister)] == null)) { // and if the register
        // has a value stored inside it,
        // Retrieve and return the value stored in the register
        return this.storeArr[findRegisterIndex(possibleRegister)]; 
      } else {
        // Print the error to the user and exit the program
        errorPrinter.println(possibleRegister + " does not have a value stored inside it. " + // 
            "Store a value inside the register first.");
        System.exit(3);
      }
    } else if (isLowerRegister(possibleRegister)) { // If possibleRegister is a valid register and
      // is a lowercase letter,
      if (!(this.storeArr[findRegisterIndex(possibleRegister)] == null)) { // and if the register
        // has a value stored inside it,
        // Retrieve and return the value stored in the register
        return this.storeArr[findRegisterIndex(possibleRegister)];
      } else {
        // Print the error to the user and exit the program
        errorPrinter.println(possibleRegister + " does not have a value stored inside it. " + //
            "Store a value inside the register first.");
        System.exit(3);
      }
    } 

    // If the operand is not a register, then maintain its numeric value 
    return new BigFraction(operand);
  } // determineOperandValue(String)

  /**
   * Checks if the operand `operand` is one of the four basic operators.
   * 
   * @pre operand.length() == 1
   */
  public boolean checkValidOperand(String operand) {
    String[] operators = {"+", "-", "/", "*"};

    // Return false if the operand is one of the four basic operators,
    // "+", "-", "/", "*"
    for (int i = 0; i < operators.length; i++) {
      if (operand.equals(operators[i])) {
        return false;
      }
    }

    // Return true if the operand is not an operator
    return true;
  } // checkValidOperand(String)
} // class BFCalculator