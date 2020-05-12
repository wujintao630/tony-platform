import com.tonytaotao.springboot.dubbo.test.TestApplication;
import com.tonytaotao.springboot.dubbo.test.tasknode.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class ApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    public void testTransaction(){

        try {
            testService.testWebNode();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
