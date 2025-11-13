import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ListToStringTest {

    @Test
    public void whentStringJoinWithStringList_thenPrintCustom(){
        List<String> strList = Arrays.asList("one", "two", "three");
        System.out.println(String.join(" : ", strList));
    }

    @Test
    public void whenStringJoinWithNonStringList_thenPrintCustom() {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<String> strList = intList.stream().map(String::valueOf).collect(Collectors.toList());
        System.out.println(String.join(" : ", strList));
    }

    @Test
    public void whenCollectorsJoining_thenPrintCustom(){
        List<Integer> intList = Arrays.asList(1, 2, 3);
        String result = intList.stream()
                .map(String::valueOf)
.collect(Collectors.joining(" : "));
    }
}
