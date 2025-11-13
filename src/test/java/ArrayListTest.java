import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {

    @Test
    public void canInitializeArrayList() {
        List<String> list = new ArrayList<>();
        assertTrue(list.isEmpty());
    }

    @Test
    public void canBeInitalizedAtCreation(){
        List<String> list = new ArrayList<>(List.of("a", "b", "c"));
        assertEquals(3, list.size());
    }

    @Test
    public void constructorAcceptsACollection(){
        List<String> list = new ArrayList<>(List.of("a", "b", "c"));
        List<String> list2 = new ArrayList<>(list);
        assertEquals(3, list2.size());
    }

    @Test
    public void constructorAcceptsCollectionOfPrimitives(){
        Collection<Integer> numbers = IntStream.range(0, 10).boxed().toList();
        List<Integer> list = new ArrayList<>(numbers);
        assertTrue(numbers.containsAll(list));
        assertEquals(10, list.size());
    }

    @Test
    public void canAddElement(){
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(10);
        assertEquals(2, list.size());
    }

    @Test
    public void canAddCollectionOfElements(){
        List<Long> list = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        LongStream.range(4, 10)
                .boxed()
                .collect(collectingAndThen(toCollection(ArrayList::new),
                        ys -> list.addAll(0, ys)));
        assertEquals(Arrays.asList(4L, 5L, 6L, 7L, 8L, 9L, 1L, 2L, 3L), list);
    }

    @Test
    public void canTraverseInBothDirectionsWithListIterator(){
        List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        // Here we set the index where to start traversing
        ListIterator<Integer> it = list.listIterator(list.size());
        List<Integer> result = new ArrayList<>(list.size());
        while (it.hasPrevious()) {
            result.add(it.previous());
        }
        Collections.reverse(list);
        assertEquals(result, list);
    }

    @Test
    public void canOnlyTraveseInOneDirectionsWithWithIterator(){
        List<Integer> list = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Iterator<Integer> it = list.iterator();
        List<Integer> result = new ArrayList<>(list.size());
        while (it.hasNext()) {
            result.add(it.next());
        }
        assertEquals(list, result);
    }

    @Test
    public void canSearchAnUnsortedList(){
        List<String> stringsToSearch = getUnsortedList();
        assertEquals(10, stringsToSearch.indexOf("a"));
        assertEquals(26, stringsToSearch.lastIndexOf("a"));
    }

    @Test
    public void canFindElementSatisfyingPredicate(){
        List<String> stringsToSearch = getUnsortedList();
        Set<String> matchingString = new HashSet<>(Arrays.asList("a", "c", "9"));

        List<String> result = stringsToSearch
                .stream()
                .filter(matchingString::contains)
                .toList();

        assertEquals(6, result.size());
    }

    @Test
    public void canSearchASortedList(){
        var unsortedList = getUnsortedList();
        List<String> copy = new ArrayList<>(unsortedList);
        Collections.sort(copy);
        int index = Collections.binarySearch(copy, "f");

        assertTrue(index >= 0);
    }

    @Test
    public void canRemoveElement(){
        List<Integer> list = IntStream.range(0, 10)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(list);

        list.remove(0);
        assertEquals(8, list.getFirst());

        list.remove(Integer.valueOf(0));
        assertFalse(list.contains(0));
    }

    @Test
    public void givenSequencedArrayListWhenGetFirstThenFirstElementIsReturned(){
        var list = new ArrayList<>(Arrays.asList(11, 22, 33));

        var expected = 11;
        assertEquals(expected, list.getFirst());
    }


    @Test
    public void givenSequencedArrayListWhenGetLastThenLastElementIsReturned(){
        var list = new ArrayList<>(Arrays.asList(11, 22, 33));

        var expected = 33;
        assertEquals(expected, list.getLast());
    }

    @Test
    public void canAddAnElementToTheStartOfTheList(){
        var list = new ArrayList<>(Arrays.asList(11, 22, 33));
        list.addFirst(0);
        assertEquals(0, list.getFirst());
    }

    @Test void canAddAnElementToTheEndOfTheList(){
        var list = new ArrayList<>(Arrays.asList(11, 22, 33));
        list.addLast(44);
        assertEquals(44, list.getLast());
    }

    @Test
    public void canRemoveAnElementFromTheStartOfTheList(){
        var list = new ArrayList<>(Arrays.asList(11, 22, 33));
        list.removeFirst();
        assertEquals(22, list.getFirst());
    }

    @Test
    public void canRemoveAnElementFromTheEndOfTheList(){
        var list = new ArrayList<>(Arrays.asList(11, 22, 33));
        list.removeLast();
        assertEquals(22, list.getLast());
    }


    public List<String> getUnsortedList(){
      List<String> list = LongStream.range(0, 16)
                .boxed()
                .map(Long::toHexString)
                .toList();

        List<String> stringsToSearch = new ArrayList<>(list);
        stringsToSearch.addAll(list);

        return stringsToSearch;
    }
}
