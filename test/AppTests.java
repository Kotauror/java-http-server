import httpserver.App;
import org.junit.Assert;
import org.junit.Test;

public class AppTests {

    @Test
    public void multiplication() {
        App tester = new App();

        Assert.assertEquals(0, tester.multiply(10, 0));
    }
}
