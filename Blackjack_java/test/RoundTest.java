import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void sumUp() {
//        Round.testMethod();
        assertEquals(21, Round.sumUp(Round.test1));
    }

    @Test
    void getNames() {
//        Round.testMethod();
        List<String> s = new ArrayList<>();
        s.add("HEART_2");
        assertEquals(s,Round.getNames(Round.test2));
    }

    @Test
    void hasACE() {
        Round.testMethod();
        assertTrue(Round.hasACE(Round.test1));
        assertFalse(Round.hasACE(Round.test2));
    }

    @BeforeEach
    public void init(){
        Round.testMethod();
    }

    @AfterEach
    public void clear(){
        Round.test1.clear();
        Round.test2.clear();
    }
}