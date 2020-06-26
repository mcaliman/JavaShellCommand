package com.trueprogramming.shellcommand;

/**
 *
 * @author Massimo Caliman
 */
public interface ShellCommand {

    void execute() throws ShellCommandException;

    int getExitValue();

    StringBuilder getOutput();
}
