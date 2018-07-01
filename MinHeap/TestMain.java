
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Runs specified test suites.
 * @author Zeehasham Rasheed
 */
public class TestMain 
{
    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(
                TestMinHeap.class,
                TestOrderedArrayMinPQ.class
        );
        for (Failure failure : result.getFailures())
        {
            System.out.println(failure.toString());
        }
    }
}
