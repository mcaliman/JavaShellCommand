package com.trueprogramming.shellcommand;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.fail;

/**
 *
 * @author Massimo Caliman
 */
public class RemoteShellCommandTest {

    private final static String HOST = "192.168.1.73";
    private final static int PORT = 22;
    
    /**
     * Test of main method, of class RemoteShellCommand.
     */
    @Test
    public void testMain() {
        RemoteShellCommand cmd = new RemoteShellCommand(HOST, PORT, "mcaliman", "", "ls -la /home/mcaliman/Scrivania");
        try {
            cmd.execute();
            int exit = cmd.getExitValue();
            System.out.println("Exit Status:" + exit);
            System.out.println("Output:\n" + cmd.getOutput().toString());
        } catch (ShellCommandException ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
        assertEquals(0, 0);
    }

    /**
     * Test of getExitValue method, of class RemoteShellCommand.
     */
    @Test
    public void testGetExitValue() {
        System.out.println("getExitValue");
        RemoteShellCommand instance = new RemoteShellCommand("", 0, "", "", "");
        int expResult = 0;
        int result = instance.getExitValue();
        assertEquals(0, result);
    }

    /**
     * Test of getOutput method, of class RemoteShellCommand.
     */
    @Test
    public void testGetOutput() {
        System.out.println("getOutput");
        RemoteShellCommand instance = new RemoteShellCommand("", 0, "", "", "");
        StringBuilder expResult = null;
        StringBuilder result = instance.getOutput();
        assertEquals(null, result);

    }

}
