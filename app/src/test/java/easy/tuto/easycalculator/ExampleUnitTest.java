package easy.tuto.easycalculator;

import org.junit.Test;

import static org.junit.Assert.*;

import android.view.View;

/**

 Example local unit test, which will execute on the development machine (host).*
 @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    //power test
    @Test
    //power test
    public void numeratorIsZeroAndPowerIsZero() {
        MathFunction math = new MathFunction();
        assertEquals("Undefined", math.power(0, 0));
    }
    //Binary,Octal and Hex function test
    @Test
    public void testBinary(){
        MathFunction math = new MathFunction();
        assertEquals("101", math.decimalToBinary("5"));
    }

    @Test
    public void testOctal(){
        MathFunction math = new MathFunction();
        assertEquals("64",math.decimalToOctal("52"));
    }
    @Test
    public void testHex(){
        MathFunction math = new MathFunction();
        assertEquals("2E",math.decimalToHexadecimal("46"));
    }
    @Test
    public void numberIsNegative(){
        MathFunction math = new MathFunction();
        assertEquals("Please enter a non-negative decimal number.",math.decimalToBinary("-50"));
    }
    @Test
    public void numberIsNotInteger(){
        MathFunction math = new MathFunction();
        assertEquals("Please enter a decimal number that is an integer.",math.decimalToBinary("5.21"));
    }


}