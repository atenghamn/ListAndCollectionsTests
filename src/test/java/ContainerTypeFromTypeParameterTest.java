import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainerTypeFromTypeParameterTest {

    @Test
    public void givenContainerClassWithGenericType_whenTypeParameterUsed_thenReturnsClassType(){
        var stringContainer = new ContainerTypeFromTypeParameter<>(String.class);

        assertEquals(String.class, stringContainer.getClazz());
    }
}
