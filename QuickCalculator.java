import java.io.PrintWriter;

public class QuickCalculator {
  public static void main (String[] args) {
    BFCalculator Calculator = new BFCalculator();
    PrintWriter pen = new PrintWriter(System.out, true);
    String[] exprParts = args[0].split(" ");

    if (args[0].equals("STORE")) {
      //System.out.println("meep");
    } else { // Evaluation

      int firstOperandIndex = 0;
      int operatorIndex = 1;
      int secondOperandIndex = 2;

      while (secondOperandIndex <= exprParts.length - 1) {
        if (operatorIndex == 1) {
          //System.out.println(firstOperandIndex + " " + operatorIndex + " " + secondOperandIndex);
          //System.out.println(exprParts[firstOperandIndex] + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex]);
          String expr = exprParts[firstOperandIndex] + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
          Calculator.evaluate(expr);
        } else {
          //System.out.println(firstOperandIndex + " " + operatorIndex + " " + secondOperandIndex);
          String expr = Calculator.result + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
          Calculator.evaluate(expr);
        }
        operatorIndex += 2;
        secondOperandIndex += 2;
      }

      pen.println(Calculator.result);
    }
  }
}