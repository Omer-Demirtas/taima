package com.quartz.quartzexample.excetion;

public class JobFailedException extends RuntimeException
{
    public JobFailedException(String message)
    {
        super(message);
    }
}
