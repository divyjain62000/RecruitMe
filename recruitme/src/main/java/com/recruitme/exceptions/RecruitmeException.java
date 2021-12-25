package com.recruitme.exceptions;

import java.util.HashMap;
import java.util.Map;

public class RecruitmeException extends Exception {
    private Map<String,String> exceptions;
    public RecruitmeException() {
        this.exceptions=new HashMap<>(); }

    public void addException(String property,String exception) { this.exceptions.put(property.trim(),exception.trim()); }

    public String getException(String property)
    {
        String exception=this.exceptions.get(property.trim());
        if(exception==null) exception="";
        return exception;
    }

    public Map<String,String> getExceptions()
    {
        Map<String,String> exceptions=new HashMap<>(this.exceptions);
        return exceptions;
    }
}
