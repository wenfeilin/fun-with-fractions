import java.math.BigInteger;

/**
 * A simple implementation of BigFractions.
 * 
 * @author Samuel A. Rebelsky
 * @author Wenfei Lin, Madel Sibal
 * @version 1.2 of August 2023
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
   * Build a new fraction with numerator num and denominator denom.
   * 
   * Warning! Not yet stable.
   */
  public BigFraction(BigInteger _num, BigInteger _denom) {
    this.num = _num;
    this.denom = _denom;
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new fraction with numerator num and denominator denom.
   * 
   * Warning! Not yet stable.
   */
  public BigFraction(int _num, int _denom) {
    this.num = BigInteger.valueOf(_num);
    this.denom = BigInteger.valueOf(_denom);
  } // BigFraction(int, int)

  /**
   * Build a new fraction by parsing a string.
   */
  public BigFraction(String str) {
    if (str.indexOf("/") == -1) {
      this.num = BigInteger.valueOf(Integer.valueOf(str));
      this.denom = BigInteger.ONE;
    } else {
      int slashIndex = str.indexOf("/");
      
      // Take the characters before the slash, convert that to an integer then
      // a BigInteger, and save it as the new numerator
      this.num = BigInteger.valueOf(Integer.valueOf(str.substring(0, slashIndex)));
      // Take the characters after the slash, convert that to an integer then
      // a BigInteger, and save it as the new denominator
      this.denom = BigInteger.valueOf(Integer.valueOf(str.substring(slashIndex + 1, str.length())));
    }
  } // BigFraction(String)

  // public BigFraction(int wholeNumber) {
  //   this.num = BigInteger.valueOf(wholeNumber);
  //   this.denom = BigInteger.ONE;
  // }
        
  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Express this fraction as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Add the fraction `addMe` to this fraction.
   */
  public BigFraction add(BigFraction addMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the
    // product of this object's denominator
    // and addMe's denominator
    resultDenominator = this.denom.multiply(addMe.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(addMe.denom)).add(addMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // add(BigFraction)

  /**
   * Subtract the fraction `addMe` from this fraction.
   */
  public BigFraction subtract(BigFraction subtractMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the
    // product of this object's denominator
    // and subtractMe's denominator
    resultDenominator = this.denom.multiply(subtractMe.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(subtractMe.denom)).subtract(subtractMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  } // subtract(BigFraction)

  /**
   * Get the denominator of this fraction.
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()
  
  /**
   * Get the numerator of this fraction.
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()
  
  /**
   * Convert this fraction to a string for ease of printing.
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0";
    } // if it's zero

    if (this.denom.equals(BigInteger.ONE)) {
      return String.valueOf(this.num);
    }
    // Lump together the string represention of the numerator,
    // a slash, and the string representation of the denominator
    // return this.num.toString().concat("/").concat(this.denom.toString());
    return this.num + "/" + this.denom;
  } // toString()
    
  /**
  * Multiply this fraction to another fraction.
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
  * Divide this fraction by another fraction.
  */
  public BigFraction divide(BigFraction divideMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // Multiply this numerator by divide's denominator
    resultNumerator = this.num.multiply(divideMe.denom);
    // Multiply this denominators by divide's numerator
    resultDenominator = this.denom.multiply(divideMe.num);

    // Return the computed product
    return new BigFraction(resultNumerator, resultDenominator);
  } // divide(BigFraction)

  public BigFraction fractional() {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // Compute what is left over in the numerator after the whole
    // number is taken off
    resultNumerator = this.num.remainder(this.denom);
    // The denominator stays the same as the improper fraction's
    // denominator
    resultDenominator = this.denom;

    // Return the computed fractional 
    return new BigFraction(resultNumerator, resultDenominator);
  } // multiply(BigFraction)

  /**
  * Simplify this fraction.
  */
  public BigFraction simplify() {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // Find the greatest common divisor to be able to simplify 
    BigInteger gcd = this.num.gcd(this.denom);

    // Simplify the numerator of this fraction
    resultNumerator =  this.num.divide(gcd);
    // Simplify the denominator of this fraction
    resultDenominator = this.denom.divide(gcd);

    // Return the computed product
    return new BigFraction(resultNumerator, resultDenominator);
  } // simplify()
} // class BigFraction