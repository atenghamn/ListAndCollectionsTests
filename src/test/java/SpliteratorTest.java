import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

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
    public void givebAStreamOfArticles_whenProcessedUsingTrySplit_thenSplitIntoEqualHalf(){
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
}
