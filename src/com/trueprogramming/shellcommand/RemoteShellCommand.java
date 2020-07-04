package com.trueprogramming.shellcommand;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.logging.Level;

public class RemoteShellCommand implements ShellCommand {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(RemoteShellCommand.class.getName());

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String command;

    private int exitValue;
    private StringBuilder output;

    public RemoteShellCommand(String host, int port, String user, String password, String command) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.command = command;
    }

    @Override
    public void execute() throws ShellCommandException {
        try {
            JSch jsch = new JSch();

            Session session = jsch.getSession(this.user, this.host, this.port);
            session.setUserInfo(new RemoteUserInfo(this.password));
            session.connect();

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(this.command);
            channel.setInputStream(null);

            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            this.output = new StringBuilder();
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    String text = new String(tmp, 0, i);
                    this.output.append(text);
                    //System.out.println(text);
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    this.exitValue = channel.getExitStatus();
                    //System.out.println("exit-status: " + this.exitValue);
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                    LOG.log(Level.SEVERE, "", ee);
                }
            }

            channel.disconnect();

            session.disconnect();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "", e);
        }
    }

    @Override
    public int getExitValue() {
        return this.exitValue;
    }

    @Override
    public StringBuilder getOutput() {
        return this.output;
    }

    public static class RemoteUserInfo implements UserInfo {

        private final String password;

        public RemoteUserInfo(String password) {
            this.password = password;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public boolean promptPassword(String string) {
            return true;
        }

        @Override
        public boolean promptPassphrase(String string) {
            return true;
        }

        @Override
        public boolean promptYesNo(String string) {
            return true;
        }

        @Override
        public void showMessage(String string) {
            System.out.println(string);
        }

    }

}
