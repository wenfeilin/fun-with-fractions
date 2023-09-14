import java.math.BigInteger;

/**
 * A simple implementation of BigFractions.
 * 
 * @author Samuel A. Rebelsky
 * @author Wenfei Lin, Madel Sibal
 */
public class BigFraction {
  // +------------------+---------------------------------------------
  // | Design Decisions |
  // +------------------+
  /*
   * (1) Denominators are always positive. Therefore, negative fractions are represented 
   * with a negative numerator. Similarly, if a fraction has a negative numerator, it 
   * is negative.
   * 
   * (2) BigFractions are not necessarily stored in simplified form. To obtain a fraction 
   * in simplified form, one must call the `simplify` method.
   */

  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The numerator of the fraction. Can be positive, zero or negative. */
  BigInteger num;

  /** The denominator of the fraction. Must be non-negative. */
  BigInteger denom;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Builds a new fraction with numerator `_num` and denominator `_denom`.
   * 
   * @pre _denom > 0
   */
  public BigFraction(BigInteger _num, BigInteger _denom) {
    this.num = _num;
    this.denom = _denom;
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Builds a new fraction with numerator `_num` and denominator `_denom`.
   * 
   * @pre _denom > 0
   */
  public BigFraction(int _num, int _denom) {
    this.num = BigInteger.valueOf(_num);
    this.denom = BigInteger.valueOf(_denom);
  } // BigFraction(int, int)

  /**
   * Builds a new fraction by parsing a string.
   * 
   * @pre str only contains numeric characters and an optional "/"
   */
  public BigFraction(String str) {
    if (str.indexOf("/") == -1) { // If str is not written as a fraction, 
      //then then it must be an integer
      // Set the integer to the numerator and 1 to the denominator
      this.num = BigInteger.valueOf(Integer.valueOf(str));
      this.denom = BigInteger.ONE;
    } else { // Otherwise, str is written as a fraction
      int slashIndex = str.indexOf("/");
      
      // Take the characters before the slash, convert that to an integer then a BigInteger, 
      // and save it as the new numerator
      this.num = 
          BigInteger.valueOf(Integer.valueOf(str.substring(0, slashIndex)));
      // Take the characters after the slash, convert that to an integer then a BigInteger, 
      // and save it as the new denominator
      this.denom = 
          BigInteger.valueOf(Integer.valueOf(str.substring(slashIndex + 1, str.length())));
    }
  } // BigFraction(String)
        
  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Gets the denominator of this fraction.
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()
  
  /**
   * Gets the numerator of this fraction.
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Adds the fraction `addMe` to this fraction.
   * 
   * @pre this.denom and addMe.denom > 0
   */
  public BigFraction add(BigFraction addMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the product of this fraction's denominator
    // and addMe's denominator
    resultDenominator = this.denom.multiply(addMe.denom);
    // The numerator is the sum of this fraction's numerator multiplied by addMe's denominator
    // and addMe's numerator multiplied by this fraction's denominator
    resultNumerator = (this.num.multiply(addMe.denom)).add(addMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // add(BigFraction)

  /**
   * Subtracts the fraction `subtractMe` from this fraction.
   * 
   * @pre this.denom and subtractMe.denom > 0
   */
  public BigFraction subtract(BigFraction subtractMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the product of this fraction's denominator
    // and subtractMe's denominator
    resultDenominator = this.denom.multiply(subtractMe.denom);
    // The numerator is the difference of this fraction's numerator multiplied by subtractMe's
    // denominator and subtractMe's numerator multiplied by this fraction's denominator
    resultNumerator = 
        (this.num.multiply(subtractMe.denom)).subtract(subtractMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // subtract(BigFraction)
    
  /**
   * Multiplies this fraction to the fraction `multiplyMe`.
   * 
   * @pre this.denom and multiplyMe.denom > 0
   */
  public BigFraction multiply(BigFraction multiplyMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // Multiply the numerators of the two fractions
    resultNumerator = this.num.multiply(multiplyMe.num);
    // Multiply the denominators of the two fractions
    resultDenominator = this.denom.multiply(multiplyMe.denom);

    // Return the computed product
    return new BigFraction(resultNumerator, resultDenominator);
  } // multiply(BigFraction)

  /**
   * Divides this fraction by the  fraction `divideMe`.
   * 
   * @pre this fraction and multiplyMe > 0
   */
  public BigFraction divide(BigFraction divideMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // Multiply this fraction's numerator by divideMe's denominator
    resultNumerator = this.num.multiply(divideMe.denom);
    // Multiply this fraction's denominator by divideMe's numerator
    resultDenominator = this.denom.multiply(divideMe.num);

    // Return the computed product
    return new BigFraction(resultNumerator, resultDenominator);
  } // divide(BigFraction)

  /**
   * Simplifies this fraction.
   * 
   * @pre this.denom > 0
   */
  public BigFraction simplify() {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    if (!this.denom.equals(BigInteger.ZERO)) { // As long as the fraction isn't undefined
      // Find the greatest common divisor of this fraction's numerator
      // and this fraction's denominator to be able to simplify 
      BigInteger gcd = this.num.gcd(this.denom);

      // Simplify the numerator of this fraction by the GCD
      resultNumerator =  this.num.divide(gcd);
      // Simplify the denominator of this fraction by the GCD
      resultDenominator = this.denom.divide(gcd);

      // Return the computed product
      return new BigFraction(resultNumerator, resultDenominator);
    }

    return new BigFraction(this.num, this.denom);
  } // simplify()

  /**
   * Converts this fraction to a string for ease of printing.
   */
  public String toString() {
    if (this.denom.equals(BigInteger.ZERO)) { // Special case: It's undefined
      return "undefined";
    } else if (this.num.equals(BigInteger.ZERO)) { // Special case: It's zero
      return "0";
    }

    if (this.denom.equals(BigInteger.ONE)) { // Special case: It's an integer
      return String.valueOf(this.num);
    }

    // Lump together the string represention of the numerator, a slash, and the string 
    // representation of the denominator return 
    // this.num.toString().concat("/").concat(this.denom.toString());
    return this.num + "/" + this.denom;
  } // toString()
} // class BigFraction