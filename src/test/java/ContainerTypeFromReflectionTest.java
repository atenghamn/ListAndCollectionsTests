import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerTypeFromReflectionTest {

    @Test
    public void givenContainerClassWithGenericType_whenReflectionUsed_thenReturnsClassType(){
     var stringContainer = new ContainerTypeFromReflection<>("Hello Java");
     Class<?> stringClazz = stringContainer.getClazz();
     assertEquals(String.class, stringClazz);

     var integerContainer = new ContainerTypeFromReflection<>(10);
     Class<?> integerClazz = integerContainer.getClazz();
     assertEquals(Integer.class, integerClazz);
    }
}
