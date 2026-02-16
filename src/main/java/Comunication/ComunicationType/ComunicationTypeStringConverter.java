package Comunication.ComunicationType;

import java.util.*;

public class ComunicationTypeStringConverter
{
    public static ComunicationType stringToComunicationType(String type)
    {
        return ComunicationType.valueOf(type);
    }

    public static String comunicationTypeToString(ComunicationType type)
    {
        return type.name();
    }
}
