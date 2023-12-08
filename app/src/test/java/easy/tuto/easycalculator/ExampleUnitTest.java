package easy.tuto.easycalculator;

import org.junit.Test;

import static org.junit.Assert.*;

import android.view.View;

/**

 Example local unit test, which will execute on the development machine (host).*
 @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    //power test
    public void numeratorIsZeroAndPowerIsZero() {
        MathFunction math = new MathFunction();
        assertEquals("Undefined", math.power(0, 0));
    }
}
