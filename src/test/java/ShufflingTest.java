import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShufflingTest {

    @Test
    public void whenShufflingList_thenAffectOriginalList(){
        var originalList = Arrays.asList("a", "c", "b", "d", "e");
        var copyList = new ArrayList<>(originalList);
        Collections.shuffle(originalList);
        assertNotEquals(originalList, copyList);
    }

    @Test
    public void whenShufflingList_thensubListIsShuffledAsWellSinceTheyHavetheSameReference(){
        var originalList = Arrays.asList("a", "c", "b", "d", "e");
        var copyList = originalList.subList(0, originalList.size());
        Collections.shuffle(originalList);
        assertEquals(copyList, originalList);
    }

    @Test
    public void whenShufflingList_WithCustomRandomSource_thenItBecomesDeterministic(){
        var students_1 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        var students_2 = Arrays.asList("Foo", "Bar", "Baz", "Qux");
        var seedValue = 10;

        Collections.shuffle(students_1, new Random(seedValue));
        Collections.shuffle(students_2, new Random(seedValue));

        assertEquals(students_1, students_2);
    }
}
