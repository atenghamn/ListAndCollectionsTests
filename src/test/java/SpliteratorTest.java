import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

public class SpliteratorTest {

    @Test
    public void givenAStreamOfArticles_whenProcessedInSequencetiallyWithSpliterator_ProducessRightOutput(){
        List<Article> articles = Stream.generate(() -> new Article( "An article"))
                .limit(1000000)
                .toList();

        Spliterator<Article> spliterator = articles.spliterator();
        while (spliterator.tryAdvance(a -> a.setName(a.getName().concat(" about Java"))));

        articles.forEach(article -> assertEquals("An article about Java", article.getName()));
    }

    @Test
    public void givennAStreamOfArticles_whenProcessedUsingTrySplit_thenSplitIntoEqualHalf(){
        var articles = Stream.generate(() -> new Article( "An article"))
                .limit(1000000)
                .toList();
        Spliterator<Article> split1 = articles.spliterator();
        Spliterator<Article> split2 = split1.trySplit(); // returns a new spliterator with the second half

        List<Article> articlesListOne = new ArrayList<>();
        List<Article> articlesListTwo = new ArrayList<>();

        split1.forEachRemaining(articlesListOne::add);
        split2.forEachRemaining(articlesListTwo::add);

        assertEquals(500000, articlesListOne.size());
        assertEquals(500000,articlesListTwo.size());
        assertFalse(articlesListOne.containsAll(articlesListTwo));
    }

    @Test
    public void givenAStreamOfIntegers_whenProcessedSequentialCustomSpliterator_countProducesRightOutput(){
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        CustomSpliterator customSpliterator = new CustomSpliterator(numbers);
        AtomicInteger sum = new AtomicInteger();

        customSpliterator.forEachRemaining(sum::addAndGet);
        assertEquals(15, sum.get());
    }

    @Test
    public void givenAStreamOfIntegers_whenProcessedInParallelWithCustomSpliterator_countProducesRightOutput(){
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        CustomSpliterator customSpliterator = new CustomSpliterator(numbers);

        // ForkJoinPool for parallel processing
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        AtomicInteger sum = new AtomicInteger(0);

        // Process in parallel
        forkJoinPool.submit(() -> {
            Stream<Integer> parallelStream = StreamSupport.stream(customSpliterator, true);
            parallelStream.forEach(sum::addAndGet);

        }).join();
        assertEquals(15, sum.get());
    }
}
