import org.junit.Assert;
import org.junit.Test;

public class MyClassTests {

    @Test
    public void multiplication() {
        MyClass tester = new MyClass();

        Assert.assertEquals(0, tester.multiply(10, 0));
    }
}
