/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trueprogramming.shellcommand;

/**
 *
 * @author mcaliman
 */
public class ShellCommandException extends Exception {

    private String command = "";
    private int exitValue = 0;
 


    public ShellCommandException(String command, int exitValue,String message, Throwable cause) {
        super(message, cause);
        this.command = command;
        this.exitValue = exitValue;
        
    }

  public String getMessage() {
        return  "Error when exec shell command: '"+this.command+"' exit value: "+this.exitValue+" case :" + this.getCause();
        //return detailMessage;
    }
}
