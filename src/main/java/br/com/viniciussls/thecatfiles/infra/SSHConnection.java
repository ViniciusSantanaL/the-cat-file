package br.com.viniciussls.thecatfiles.infra;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnection {

    private final String user;
    private final String password;
    private final String host;
    private static final Integer PORT = 2222;

    private static Session session;

    public SSHConnection(String user, String password, String host) {
        this.user = user;
        this.password = password;
        this.host = host;
    }

    public boolean connect() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(this.user, this.host, PORT);
            session.setPassword(this.password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("- Session connected :) !! -");
            SSHConnection.session = session;
            return  true;
        } catch (JSchException ex) {
            ex.printStackTrace();
            System.out.println(" - Failed to Connect Server :( -");
        }
        return false;
    }

    public static void disconnect() {
        if(SSHConnection.session != null) {
            session.disconnect();
        }
    }
    public static Session getSession() {
        return session;
    }
}
