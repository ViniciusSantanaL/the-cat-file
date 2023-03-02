package br.com.viniciussls.thecatfiles.controllers;

import br.com.viniciussls.thecatfiles.infra.SSHConnection;
import br.com.viniciussls.thecatfiles.service.FormatDateService;
import br.com.viniciussls.thecatfiles.service.SFTPService;
import br.com.viniciussls.thecatfiles.utils.SingleInstance;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.List;

public class HomeController {

    @FXML
    public Button connectSeverButton;

    @FXML
    public Button disconnectSeverButton;

    @FXML
    public Button downloadButton;
    @FXML
    public TextField userField;

    @FXML
    public TextField passwordField;

    @FXML
    public TextField hostField;

    @FXML
    public TextField outputDirField;
    @FXML
    public VBox downloadContainer;

    @FXML
    public TextField severFilesDirInput;

    @FXML
    public VBox errorMessage;

    private File outputDir;

    @FXML
    private void connectServer() {
        SSHConnection sshServer = new SSHConnection(userField.getText(), passwordField.getText(), hostField.getText());
        boolean connected = sshServer.connect();

        if(connected) {
            connectSeverButton.setVisible(false);
            connectSeverButton.setManaged(false);

            disconnectSeverButton.setVisible(true);
            disconnectSeverButton.setManaged(true);

            downloadContainer.setVisible(true);

            errorMessage.setVisible(false);
            errorMessage.setManaged(false);
        } else {
            errorMessage.setVisible(true);
            errorMessage.setManaged(true);
        }

    }

    @FXML
    private void disconnectSever() {
        SSHConnection.disconnect();
        disconnectSeverButton.setVisible(false);
        disconnectSeverButton.setManaged(false);

        connectSeverButton.setVisible(true);
        connectSeverButton.setManaged(true);

        downloadContainer.setVisible(false);
    }
    @FXML
    private void chooseOutputDir() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose output Folder");
        outputDir = directoryChooser.showDialog(SingleInstance.getInstance().getCurrentStage());
        if (outputDir != null) {
            outputDirField.setText(outputDir.getAbsolutePath());
            if(!severFilesDirInput.getText().isEmpty()) {
                System.out.println("adasda");
                downloadButton.setDisable(false);
                downloadButton.setStyle("-fx-background-color: linear-gradient(green, darkgreen)");
            }
        }
    }
    @FXML
    private void onSeverFilesDitInput() {
        if(!outputDirField.getText().isEmpty()) {
            downloadButton.setDisable(false);
            downloadButton.setStyle("-fx-background-color: linear-gradient(green, darkgreen)");
        }
    }
    @FXML
    private void downloadFiles() {
        downloadButton.setStyle("-fx-background-color: gray");
        downloadButton.setDisable(true);
        new Thread(() -> {
            SFTPService service = new SFTPService(SSHConnection.getSession());
            List<String> filesName = FormatDateService.filterStringsHaveTodayDate(service.getFileNames(severFilesDirInput.getText()));
            System.out.println(filesName.toString());
            service.downloadMultipleFiles(filesName, severFilesDirInput.getText(),outputDir.getPath());
            downloadButton.setStyle("-fx-background-color: linear-gradient(green, darkgreen)");
            downloadButton.setDisable(false);
        }).start();
    }
}