import java.io.PrintWriter;

public class BFCalculator {

  // fields
  BigFraction result;
  BigFraction[] storeArr;

  // constructor
  public BFCalculator() {
    this.result = new BigFraction(0, 0);
    this.storeArr = new BigFraction[52];
  }

  // methods
  public void evaluate (String exp) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    String[] expressionParts = exp.split(" ");
    String strOperand1 = expressionParts[0];

    if (expressionParts.length == 1) {
      if (checkValidOperand(strOperand1)) {
        this.result = determineOperandValue(strOperand1);
      } else {
        errorPrinter.println(strOperand1 + " is an invalid expression.");
        System.exit(7);
      }
    } else {
      String strOperand2 = expressionParts[2];
      if (checkValidOperand(strOperand1) && checkValidOperand(strOperand2)) {
        BigFraction operand1 = determineOperandValue(strOperand1);
        BigFraction operand2 = determineOperandValue(strOperand2);
        String operator = expressionParts[1];

        switch (operator) {
          case "+" :
            this.result = operand1.add(operand2);
            break;
          case "-" :
            this.result = operand1.subtract(operand2);
            break;
          case "/" :
            this.result = operand1.divide(operand2);
            break;
          case "*" :
            this.result = operand1.multiply(operand2);
            break;
          default :
            errorPrinter.println(operator + " is an invalid operator.");
            System.exit(2);
        }
      } else {
        errorPrinter.println("Incorrect form: Two operators in a row.");
        System.exit(4);
      }
    }
    this.result = this.result.simplify();
  }

  public void store (char register) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    int index = findRegisterIndex(register);

    if (index == -1) {
      errorPrinter.println(register + " is not a valid register. The register must be a letter of the alphabet.");
      System.exit(1);
    }
    this.storeArr[index] = this.result;
  }

  public boolean isUpperRegister (char register) {
    return register >= 'A' && register <= 'Z';
  }

  public boolean isLowerRegister (char register) {
    return register >= 'a' && register <= 'z';
  }

  public int findRegisterIndex (char register) {
    int rebaseCapitalLetter = 65;
    int rebaseLowerCaseLetter = 97;
    int storeArrHalfWayPt = 26;

    if (isUpperRegister(register)) {
      return (int) register - rebaseCapitalLetter;
    } else if (isLowerRegister(register)) {
      return (int) register - rebaseLowerCaseLetter + storeArrHalfWayPt;
    } else {
      return -1;
    }
  }
  
  public BigFraction determineOperandValue (String operand) {
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    char possibleRegister = operand.charAt(0);

    if (isUpperRegister(possibleRegister)) {
      if (!(this.storeArr[findRegisterIndex(possibleRegister)] == null)) {
        return this.storeArr[findRegisterIndex(possibleRegister)];
      } else {
        errorPrinter.println(possibleRegister + " does not have a value stored inside it. Store a value inside the register first.");
        System.exit(3);
      }
    } else if (isLowerRegister(possibleRegister)) {
      if (!(this.storeArr[findRegisterIndex(possibleRegister)] == null)) {
        return this.storeArr[findRegisterIndex(possibleRegister)];
      } else {
        errorPrinter.println(possibleRegister + " does not have a value stored inside it. Store a value inside the register first.");
        System.exit(3);
      }
    } 

    return new BigFraction(operand);
  }

  public boolean checkValidOperand (String operand) {
    String[] operators = {"+", "-", "/", "*"};

    for (int i = 0; i < operators.length; i++) {
      if (operand.equals(operators[i])) {
        return false;
      }
    }
    return true;
  }

}