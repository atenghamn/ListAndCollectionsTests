import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeTokenTest {

    @Test
    public void givenContainerClassWithGenericType_whenTypeTokenUsed_thenReturnsClassType(){
        class ContainerTypeToken extends TypeToken<List<String>>{}

        var container = new ContainerTypeToken();
        ParameterizedType type = (ParameterizedType) container.getType();
        Type actualTypeArgument = type.getActualTypeArguments()[0];

        assertEquals(String.class, actualTypeArgument);
    }
}
