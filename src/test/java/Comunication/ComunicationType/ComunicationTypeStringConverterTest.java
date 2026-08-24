package Comunication.ComunicationType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

public class ComunicationTypeStringConverterTest 
{
    @ParameterizedTest
    @EnumSource(ComunicationType.class)
    void testBidirectionalConversion(ComunicationType type) 
    {
        String typeString = ComunicationTypeStringConverter.comunicationTypeToString(type);
        assertEquals(type.name(), typeString);

        ComunicationType convertedType = ComunicationTypeStringConverter.stringToComunicationType(typeString);
        assertEquals(type, convertedType);
    }

    @Test
    void testStringToComunicationTypeInvalidValue() 
    {
        assertThrows(IllegalArgumentException.class, () -> {
            ComunicationTypeStringConverter.stringToComunicationType("INVALID_TYPE_VALUE");
        });
    }

    @Test
    void testStringToComunicationTypeNull() 
    {
        assertThrows(NullPointerException.class, () -> {
            ComunicationTypeStringConverter.stringToComunicationType(null);
        });
    }

    @Test
    void testComunicationTypeToStringNull() 
    {
        assertThrows(NullPointerException.class, () -> {
            ComunicationTypeStringConverter.comunicationTypeToString(null);
        });
    }
}