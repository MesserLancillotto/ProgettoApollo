package Comunication.Request.Interfaces;

import Comunication.Request.Interfaces.RequestInterface;
import Comunication.ComunicationType.ComunicationType;

public abstract class Request implements RequestInterface
{
    private ComunicationType comunicationType;
    
    public Request
    (
        ComunicationType comunicationType
    ) {
        this.comunicationType = comunicationType;
    }

    public abstract String toJSONString();
}