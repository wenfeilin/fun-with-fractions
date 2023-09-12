import java.io.PrintWriter;

public class QuickCalculator {
  public static void main (String[] args) {
    BFCalculator Calculator = new BFCalculator();
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);

    for (int i = 0; i < args.length; i++){
      String[] exprParts = args[i].split(" ");
  
      if (exprParts[0].equals("STORE")) {
        String registerStr = exprParts[1];
        if (registerStr.length() == 1) {
          Calculator.store(registerStr.charAt(0));
        } else {
          errorPrinter.println("The register must be a letter of the alphabet");
          System.exit(1);
        }
      } else { // Evaluation
        
        int firstOperandIndex = 0;
        int operatorIndex = 1;
        int secondOperandIndex = 2;
  
        while (secondOperandIndex <= exprParts.length - 1) {
          if (operatorIndex == 1) {
            String expr = exprParts[firstOperandIndex] + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
            Calculator.evaluate(expr);
            pen.println(expr + " = " + Calculator.result);
          } else {
            String expr = Calculator.result + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
            Calculator.evaluate(expr);
            pen.println(expr + " = " + Calculator.result);
          }
          operatorIndex += 2;
          secondOperandIndex += 2;
        }
      }
    }
  }
}