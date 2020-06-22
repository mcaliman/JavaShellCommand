package com.trueprogramming.shellcommand;

/**
 *
 * @author Massimo Caliman
 */
public class ShellCommandException extends Exception {

    private String command = "";
    private int exitValue = 0;

    public ShellCommandException(String command, int exitValue, String message, Throwable cause) {
        super(message, cause);
        this.command = command;
        this.exitValue = exitValue;

    }

    @Override
    public String getMessage() {
        return "Error when exec shell command: '" + this.command + "' exit value: " + this.exitValue + " case :" + this.getCause();
    }
}
