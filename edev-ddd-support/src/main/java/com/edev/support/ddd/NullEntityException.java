package com.edev.support.ddd;

public class NullEntityException extends RuntimeException{
    public NullEntityException() { super("The entity is null!"); }
}
