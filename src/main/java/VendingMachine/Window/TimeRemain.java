package VendingMachine.Window;

import VendingMachine.Processor.UserProcessor;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimeRemain {

    private Stage stage;
    private AnchorPane pane;
    private Timeline timeline;

    public TimeRemain(Stage stage, AnchorPane pane, int x, int y) {
        this.pane = pane;
        this.stage = stage;
        initTime(x, y);
    }

    private void initTime(int x, int y) {
        Text label = new Text();
        label.setLayoutY(y);
        label.setLayoutX(x);
        IntegerProperty i = new SimpleIntegerProperty(120);
        label.setText("Time remaining: " + i.get() + " seconds");
        pane.getChildren().add(label);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> timeUpdate(i, label))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void timeUpdate(IntegerProperty i, Text label) {
        i.set(i.get() - 1);
        label.setText("Time remaining: " + i.get() + " seconds");
        if(i.get() < 0) {
            try {
                UserProcessor.getInstance().getCurrentUser().cancelShopping("timeout.");
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Can't get the user processor.");
                alert.show();
            }
            stage.close();
            timeline.stop();
            MainWindow.getInstance().update();
        }
    }

    public void stopTime() {
        timeline.stop();
    }
}
