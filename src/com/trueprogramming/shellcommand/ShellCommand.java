package com.trueprogramming.shellcommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Massimo Caliman
 */
public class ShellCommand {

    private static final Logger LOG = Logger.getLogger(ShellCommand.class.getName());

    private final String command;
    private int exitValue;

    public ShellCommand(String command) {
        this.command = command;
    }

    public void execute() throws ShellCommandException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", this.command);
        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            this.exitValue = process.waitFor();
            if (this.exitValue == 0) {
                LOG.log(Level.INFO, "Success!");
                LOG.log(Level.INFO, output.toString());
            } else {
                throw new ShellCommandException(this.command, this.exitValue, "", null);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "this.exitVal" + this.exitValue + " Exception:" + e.getMessage());
            throw new ShellCommandException(this.command, this.exitValue, "", e);
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, "this.exitVal" + this.exitValue + " Exception:" + e.getMessage());
            throw new ShellCommandException(this.command, this.exitValue, "", e);
        }
    }

    public static void main(String... args) {
        try {
            String command = "ls /home/mcaliman/";
            LOG.log(Level.INFO, "Execute: " + command);
            ShellCommand cmd = new ShellCommand(command);
            cmd.execute();
        } catch (ShellCommandException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

}
