package Server.Engine.Interfaces;

import Comunication.Reply.Interfaces.ReplyInterface;

public interface EngineInterface
{
    public ReplyInterface handleRequest();
}