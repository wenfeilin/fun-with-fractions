//import java.io.PrintWriter;

public class BFCalculator {

  // fields
  BigFraction result;
  BigFraction[] storeArr;

  // constructor
  public BFCalculator() {
    this.result = new BigFraction(0, 0);
    this.storeArr = new BigFraction[26];
  }

  // methods
  public void evaluate (String exp) {
    String[] expressionParts = exp.split(" ");
    
    BigFraction operand1;
    BigFraction operand2;
    String operation;
    //PrintWriter pen = new PrintWriter(System.out, true);
    
    operand1 = new BigFraction(expressionParts[0]);
    operation = expressionParts[1];
    operand2 = new BigFraction(expressionParts[2]);
    //System.out.println("this?");

    System.out.println(operand1 + " " + operation + " " + operand2);

    //System.out.println("hey");
    switch (operation) {
      case "+" :
        this.result = operand1.add(operand2);
        //System.out.println("hey");
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
        System.out.println("error");
    }

    this.result = this.result.simplify();

  }

  public void store (char register) {
    
  }

}