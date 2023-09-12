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
    String[] expressionParts = exp.split(" ");
    BigFraction operand1 = new BigFraction("0/0");
    String operation = "";
    BigFraction operand2 = new BigFraction("0/0");

    char possibleRegister1 = expressionParts[0].charAt(0);
    char possibleRegister2 = expressionParts[2].charAt(0);

    if (possibleRegister1 >= 'A' && possibleRegister1 <= 'Z') {
      if (!this.storeArr[(int) possibleRegister1 - 65].equals(null)) {
        operand1 = this.storeArr[(int) possibleRegister1 - 65];
      }
    } else if (possibleRegister1 >= 'a' && possibleRegister1 <= 'z') {
      if (!this.storeArr[(int) possibleRegister1 - 97 + 26].equals(null)) {
        operand1 = this.storeArr[(int) possibleRegister1 - 97 + 26];
      }
    } else {
      operand1 = new BigFraction(expressionParts[0]);
    }
    
    if (possibleRegister2 >= 'A' && possibleRegister2 <= 'Z') {
      if (!this.storeArr[(int) possibleRegister2 - 65].equals(null)) {
        operand2 = this.storeArr[(int) possibleRegister2 - 65];
      }
    } else if (possibleRegister2 >= 'a' && possibleRegister2 <= 'z') {
      if (!this.storeArr[(int) possibleRegister2 - 97 + 26].equals(null)) {
        operand2 = this.storeArr[(int) possibleRegister2 - 97 + 26];
      }
    } else {
      operand2 = new BigFraction(expressionParts[2]);
    }
    
    operation = expressionParts[1];

    switch (operation) {
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
        System.out.println("Invalid operator");
        System.exit(2);
    }

    this.result = this.result.simplify();

  }

  public void store (char register) {
    int index = -1;

    if (register >= 'A' && register <= 'Z') {
      index = (int) register - 65;
    } else if (register >= 'a' && register <= 'z') {
      index = (int) register - 97 + 26;
    } else {
      PrintWriter errorPrinter = new PrintWriter(System.out, true);
      errorPrinter.println("The register must be a letter of the alphabet");
      System.exit(1);
    }
    this.storeArr[index]= this.result;
  }

}