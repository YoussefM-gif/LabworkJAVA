import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;
import com.example.Main;

public class AppTest {

    @Test
    public void testExample() {
        assertTrue(true);
    }

    @Test
    public void testLoadData() {
        Main main = new Main();
        List<Map<String, Object>> data = main.loadData();
        assertNotNull(data);
        assertFalse(data.isEmpty(), "The data should not be empty");
    }
}