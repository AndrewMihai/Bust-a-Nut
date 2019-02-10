import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import javax.xml.soap.Text;

public class controller implements Initializable {

    private ObservableList<String> oblist = FXCollections.observableArrayList();

    private boolean isClear;

    private FilteredList<String> filteredWords;

    @FXML
    private Button clear;

    @FXML
    private Button clearSearch;

    @FXML
    private ListView<String> levelList;

    @FXML
    private TextField gangstaWord;

    @FXML
    private TextField sentence;

    @FXML
    private TextField finalSentence;

    @FXML
    private TextField searchInput;


    public controller() {
        filteredWords = new FilteredList<>(oblist, (s -> true));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        setupSearchListener();
        gangstaWord.setText("Select a gangsta word my g");
        isClear = true;
    }

    private void loadData() {
        oblist.clear();
        String[] words = {"b", "bills", "blaze", "Brougham", "brrt", "burner", "bushes", "bust", "burst",
                "clock a grip", "cop", "crucial", "cut", "g", "gang!", "gram", "hard", "hit",
                "lib", "Ms", "pack", "purp", "reg", "straight up", "trees", "whip", "wrong blunt", "9"};
        oblist.addAll(words);
        levelList.setItems(filteredWords);
    }

    @FXML
    public void selectLevel(MouseEvent event) {
        String level = levelList.getSelectionModel().getSelectedItem();
        if (level == null || level.isEmpty()) {
            gangstaWord.setText("Select a gangsta word my g");
        }
        else {
            gangstaWord.setText(level);

        }
    }

    public void addGangstaWordToFinalSentence(ActionEvent event) {
        String word = " ";
        if (isClear) {
            word = "";
            isClear = false;
        }
        word = word  + levelList.getSelectionModel().getSelectedItem();
        finalSentence.appendText(word);

    }

    public void addToFinalSentence(ActionEvent event) {
        String phrase = sentence.getText();
        String newPhrase = " ";
        if (isClear || isPunctuation(phrase)) {
            newPhrase = "";
            isClear = false;
        }
        newPhrase = newPhrase + phrase;
        finalSentence.appendText(newPhrase);
        sentence.clear();
    }

    public void clear(ActionEvent event) {
        finalSentence.clear();
        gangstaWord.clear();
        isClear = true;
    }

    private boolean isPunctuation(String text) {
        return text.startsWith(".") || text.startsWith("!") || text.startsWith("?");
    }

    private void setupSearchListener() {
        searchInput.textProperty().addListener((observable) -> {
            String filter = searchInput.getText();
            if (filter != null && filter.length() != 0) {
                filteredWords.setPredicate((s) -> s.contains(filter));
            } else {
                filteredWords.setPredicate((s) -> true);
            }
        });
    }

    public void clearSearchAction(ActionEvent event) {
        searchInput.setText("");
    }

}
