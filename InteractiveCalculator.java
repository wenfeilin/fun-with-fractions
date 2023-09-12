import java.util.Scanner;
import java.io.PrintWriter;

public class InteractiveCalculator {
  public static void main (String[] args) {
    BFCalculator Calculator = new BFCalculator();
    PrintWriter errorPrinter = new PrintWriter(System.out, true);
    PrintWriter pen = new PrintWriter(System.out, true);
    Scanner sc = new Scanner(System.in);
    String line = "";

    
    // add error-checking in a way that tries not to throw errors so program doesn't
    // quit for users (so it's not annoying for users)


    while ((!line.equals("QUIT"))) {
      line = sc.nextLine();
      
      String[] exprParts = line.split(" ");
  
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
          } else {
            String expr = Calculator.result + " " + exprParts[operatorIndex] + " " + exprParts[secondOperandIndex];
            Calculator.evaluate(expr);
          }
          operatorIndex += 2;
          secondOperandIndex += 2;
          
        }
        if (!line.equals("QUIT")) {
          pen.println(Calculator.result);
        }
      }
    } 

    sc.close();
  }
}