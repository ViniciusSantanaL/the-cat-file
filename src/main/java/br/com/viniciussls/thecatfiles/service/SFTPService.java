package br.com.viniciussls.thecatfiles.service;

import com.jcraft.jsch.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SFTPService {

    private final Session session;

    private Channel channel;

    public SFTPService(Session session) {
        this.session = session;
    }

    public Channel createChannel() {
        try {
            Channel channel = session.openChannel("sftp");
            channel.connect();
            return channel;
        } catch (JSchException ex) {
            ex.printStackTrace();
            System.out.println("- Failed to open channel - ");
        }
        return null;
    }

    public void downloadFile(String sourcePath, String destinationPath) {
        try {
            Channel channel = createChannel();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.get(sourcePath, destinationPath);
            channel.disconnect();
        } catch (SftpException ex) {
            System.out.println("- Failed to download source - ");
        }
    }
    public List<String> getFileNames(String path) {
        Channel channel = createChannel();
        List<String> fileNames = new ArrayList<String>();
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        try {
            List<ChannelSftp.LsEntry> list = sftpChannel.ls(path);
            list.forEach(item -> fileNames.add(item.getFilename()));
            System.out.println(fileNames.toString());
            channel.disconnect();
        } catch (SftpException e) {
            e.printStackTrace();
            System.out.println("- Failed to list folders - ");
        }
        return fileNames;
    }

    public void downloadMultipleFiles(List<String> fileNames, String path,String destinationPath) {
        try {
            Channel channel = createChannel();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            for(String fileName : fileNames) {
                String sourcePath = path + "/" + fileName;
                sftpChannel.get(sourcePath, destinationPath);
            }
            sftpChannel.exit();
        } catch (SftpException ex) {
            System.out.println("- Failed to download source - ");
        }
    }


}
