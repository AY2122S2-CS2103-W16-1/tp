package unibook.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /**
     * Starts the UI (and the App).
     */
    void start(Stage primaryStage);

    /**
     * Display the list of Modules instead of People.
     */
    void seeModuleList();

    /**
     * Display the list of Persons instead of Modules.
     */
    void seePersonList();

}
