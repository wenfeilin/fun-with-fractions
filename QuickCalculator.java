import java.io.PrintWriter;

public class QuickCalculator {
  public static void main (String[] args) {
    BFCalculator Calculator = new BFCalculator();
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);

     if (args.length == 0) {
      errorPrinter.printf("No input given.\n" + //
        "To evaluate an expression, make sure each expression is a \"sequence of values (integers, fractions, or register names) separated by spaces and operators.\"\n" + //
        "Each expression must also be surrounded in quotations on the command line.\n" + //
        "To store a value, enter \"STORE register\" where register is a letter of the alphabet.\n");
      System.exit(5);
    }

    for (int i = 0; i < args.length; i++){
      String[] exprParts = args[i].split(" ");
  
      if (exprParts[0].equals("STORE")) { // Storing 5
        storeAValue(Calculator, errorPrinter, exprParts);
      } else { // Evaluation
        evaluateExpression (Calculator, errorPrinter, pen, exprParts, args, i);
      }
    }
    pen.close();
  } // main (String[])

  public static void storeAValue(BFCalculator Calculator, PrintWriter errorPrinter, String[] exprParts) {
    String registerStr = exprParts[1];

    if (registerStr.length() == 1) {
      Calculator.store(registerStr.charAt(0));
      if (Calculator.result.toString().equals("undefined")) {
        errorPrinter.println("There is nothing to be stored right now. Calculate an expression that does not evaluate to an undefined value first, then try storing again.");
        System.exit(3);
      }
    } else {
      errorPrinter.println(registerStr + " is not a valid register. The register must be a letter of the alphabet.");
      System.exit(1);
    }
  } // storeAValue(BFCalculator, PrintWriter, String[])

  public static void evaluateExpression (BFCalculator Calculator, PrintWriter errorPrinter, PrintWriter pen, String[] exprParts, String[] args, int i)  {
    int firstOperandIndex = 0;
        int operatorIndex = 1;
        int secondOperandIndex = 2;

        if (exprParts.length == 1 && !(exprParts[0].equals("QUIT"))) {
          String expr = exprParts[firstOperandIndex];
          Calculator.evaluate(expr);
          pen.println(Calculator.result);
        } else if (exprParts.length % 2 == 0) { 
          errorPrinter.println("Missing operator or operand.");
          System.exit(6);
        } else {
          while (secondOperandIndex <= exprParts.length - 1) {
            if (operatorIndex == 1) {
              String expr = exprParts[firstOperandIndex] + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
              Calculator.evaluate(expr);
            } else {
              String expr = Calculator.result + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
              Calculator.evaluate(expr);
            }
            operatorIndex += 2;
            secondOperandIndex += 2;
          }
          pen.println(args[i] + " = " + Calculator.result);
        }
  } // evaluateExpression (BFCalculator, PrintWriter, PrintWriter, String[], String[], int)
}