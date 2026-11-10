package org.iu3.documentviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;

public class FXMLController {

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    protected void onOpen(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выберите документ");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Документы", "*.docx", "*.doc", "*.pdf", "*.txt", "*.odt"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*")
        );

        Window window = htmlEditor.getScene().getWindow();
        File file = chooser.showOpenDialog(window);

        if (file != null) {
            try {
                DocumentProperty doc = new DocumentProperty(file.getAbsolutePath());
                String html = doc.getHTML();
                htmlEditor.setHtmlText(html);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Не удалось открыть файл");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }
}