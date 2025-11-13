import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CopyOnWriteArrayListTest {

    @Test
    public void createASnapShotOfListWhenIterating(){
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);
        List<Integer> result = new LinkedList<>();
        iterator.forEachRemaining(result::add);

        assertFalse(result.contains(10));

        Iterator<Integer> iterator2 = numbers.iterator();
        List<Integer> result2 = new LinkedList<>();
        iterator2.forEachRemaining(result2::add);

        assertTrue(result2.contains(10));
    }

    @Test
    public void whenIteratingOverItAndTryToRemoveElement_thenShouldThrowException(){
        CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();

       assertThrows(UnsupportedOperationException.class, () -> {
           while(iterator.hasNext()){
               iterator.remove();
           }
       });
    }
}
