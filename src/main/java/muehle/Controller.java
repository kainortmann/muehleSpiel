package muehle;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import muehle.enums.RectanglePosition;
import muehle.enums.XPosition;
import muehle.enums.YPosition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class Controller extends Application{

    Pane root;
    ResizableRectangle outerRectangle;
    ResizableRectangle midRectangle;
    ResizableRectangle innerRactangle;
    public static DoubleProperty figureSizeProperty;
    ResizableRectangle selectionHighlightingRectangle;
    List<ResizableRectangle> optionHighlightingRectangles;
    List<Field> currentMoveOptions;
    Field currentlySelectedField;
    List<Player> players;
    Player currentPlayer;
    boolean gameInitialiiziationDone;
    private boolean currentPlayerHasThreeInARow;

    @Override public void init() {
        players = new ArrayList<>();
        players.add(new Player(MuehleColor.WHITE));
        players.add(new Player(MuehleColor.BLACK));
        currentPlayer = players.get(0);
        gameInitialiiziationDone = false;
    }

    @Override
    public void start(Stage primaryStage) {

        int outerRectanglePadding = 100;
        root = new Pane();
        currentMoveOptions = new ArrayList<>();

        //add 4 rectangles, since the maximum amount of move options is 4
        optionHighlightingRectangles = new ArrayList<>();
        for (int i=1; i<= 24; i++){
            ResizableRectangle resizableRectangle = createRactangle();
            optionHighlightingRectangles.add(resizableRectangle);
            resizableRectangle.setVisible(false);
            root.getChildren().add(resizableRectangle);
            resizableRectangle.toBack();
        }

        outerRectangle = createRactangle(RectanglePosition.OUTSIDE);
        outerRectangle.widthProperty().bind(root.widthProperty().subtract(outerRectanglePadding));
        outerRectangle.heightProperty().bind(outerRectangle.widthProperty());
        outerRectangle.setX(outerRectanglePadding/2);
        outerRectangle.setY(outerRectanglePadding/2);
        root.getChildren().add(outerRectangle);

        figureSizeProperty = new SimpleDoubleProperty();
        figureSizeProperty.bind(outerRectangle.widthProperty().divide(10));

        selectionHighlightingRectangle = new ResizableRectangle();
        selectionHighlightingRectangle.widthProperty().bind(figureSizeProperty);
        selectionHighlightingRectangle.heightProperty().bind(figureSizeProperty);
        selectionHighlightingRectangle.setFill(Color.TRANSPARENT);
        selectionHighlightingRectangle.setStroke(Color.RED);
        selectionHighlightingRectangle.setStrokeWidth(2.0);
        selectionHighlightingRectangle.setVisible(false);
        root.getChildren().add(selectionHighlightingRectangle);

        midRectangle = createRactangle(RectanglePosition.MIDDLE);
        midRectangle.widthProperty().bind(outerRectangle.widthProperty().multiply(0.6666));
        midRectangle.heightProperty().bind(outerRectangle.heightProperty().multiply(0.6666));
        midRectangle.xProperty().bind(outerRectangle.xProperty().add(outerRectangle.widthProperty().divide(6)));
        midRectangle.yProperty().bind(outerRectangle.yProperty().add(outerRectangle.heightProperty().divide(6)));
        root.getChildren().add(midRectangle);

        innerRactangle = createRactangle(RectanglePosition.INSIDE);
        innerRactangle.widthProperty().bind(outerRectangle.widthProperty().multiply(0.3333));
        innerRactangle.heightProperty().bind(outerRectangle.heightProperty().multiply(0.3333));
        innerRactangle.xProperty().bind(outerRectangle.xProperty().add(outerRectangle.widthProperty().divide(3)));
        innerRactangle.yProperty().bind(outerRectangle.yProperty().add(outerRectangle.heightProperty().divide(3)));
        root.getChildren().add(innerRactangle);


        Line lineHorizontalLeft = new Line();
        lineHorizontalLeft.startXProperty().bind(outerRectangle.xProperty());
        lineHorizontalLeft.startYProperty().bind(outerRectangle.yProperty().add(outerRectangle.heightProperty().divide(2)));
        lineHorizontalLeft.endXProperty().bind(innerRactangle.xProperty());
        lineHorizontalLeft.endYProperty().bind(innerRactangle.yProperty().add(innerRactangle.heightProperty().divide(2)));
        root.getChildren().add(lineHorizontalLeft);

        Line lineHorizontalRight = new Line();
        lineHorizontalRight.startXProperty().bind(outerRectangle.xProperty().add(outerRectangle.widthProperty()));
        lineHorizontalRight.startYProperty().bind(outerRectangle.yProperty().add(outerRectangle.heightProperty().divide(2)));
        lineHorizontalRight.endXProperty().bind(innerRactangle.xProperty().add(innerRactangle.widthProperty()));
        lineHorizontalRight.endYProperty().bind(innerRactangle.yProperty().add(innerRactangle.heightProperty().divide(2)));
        root.getChildren().add(lineHorizontalRight);

        Line verticalLineTop = new Line();
        verticalLineTop.startXProperty().bind(outerRectangle.xProperty().add(outerRectangle.widthProperty().divide(2)));
        verticalLineTop.startYProperty().bind(outerRectangle.yProperty());
        verticalLineTop.endXProperty().bind(innerRactangle.xProperty().add(innerRactangle.widthProperty().divide(2)));
        verticalLineTop.endYProperty().bind(innerRactangle.yProperty());
        root.getChildren().add(verticalLineTop);

        Line verticalLineBottom = new Line();
        verticalLineBottom.startXProperty().bind(outerRectangle.xProperty().add(outerRectangle.widthProperty().divide(2)));
        verticalLineBottom.startYProperty().bind(outerRectangle.yProperty().add(outerRectangle.heightProperty()));
        verticalLineBottom.endXProperty().bind(innerRactangle.xProperty().add(innerRactangle.widthProperty().divide(2)));
        verticalLineBottom.endYProperty().bind(innerRactangle.yProperty().add(innerRactangle.heightProperty()));
        root.getChildren().add(verticalLineBottom);

        for (Field field : Board.getInstance().getAllFields()){
            bindFieldToPosition(field);
            root.getChildren().add(field);
            field.toFront();
        }


        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Hello World Example");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private ResizableRectangle createRactangle(RectanglePosition position){
        ResizableRectangle rectangle = new ResizableRectangle(position);
        rectangle.setArcWidth(5);
        rectangle.setArcHeight(5);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2.0);

        return rectangle;
    }

    private ResizableRectangle createRactangle(){
        ResizableRectangle rectangle = new ResizableRectangle();
        rectangle.setArcWidth(0);
        rectangle.setArcHeight(0);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2.0);

        return rectangle;
    }

    private Field bindFieldToPosition(Field field){


        RectanglePosition rectanglePosition = field.getParentRectangle();
        YPosition yPosition = field.getYPosition();
        XPosition xPosition = field.getXPosition();

        //field.setImage(new Image(this.getClass().getResource("dame_schwarz.png").toString()));
        field.fitWidthProperty().bind(figureSizeProperty);
        field.fitHeightProperty().bind(figureSizeProperty);

        ResizableRectangle rectangleToPositionOn;
        if(rectanglePosition == RectanglePosition.INSIDE) {
            rectangleToPositionOn = innerRactangle;
        }else if (rectanglePosition == RectanglePosition.MIDDLE) {
            rectangleToPositionOn = midRectangle;
        }else {
            rectangleToPositionOn = outerRectangle;
        }
        switch (xPosition){
            case RIGHT:
                field.xProperty().bind(rectangleToPositionOn.xProperty().add(rectangleToPositionOn.widthProperty()).subtract(field.fitWidthProperty().divide(2)));
                break;
            case MIDDLE:
                field.xProperty().bind(rectangleToPositionOn.xProperty().add(rectangleToPositionOn.widthProperty().divide(2)).subtract(field.fitWidthProperty().divide(2)));
                break;
            case LEFT:
                field.xProperty().bind(rectangleToPositionOn.xProperty().subtract(field.fitWidthProperty().divide(2)));
                break;
        }
        switch (yPosition){
            case TOP:
                field.yProperty().bind(rectangleToPositionOn.yProperty().subtract(field.fitHeightProperty().divide(2)));
                break;
            case MIDDLE:
                field.yProperty().bind(rectangleToPositionOn.yProperty().add(rectangleToPositionOn.heightProperty().divide(2)).subtract(field.fitHeightProperty().divide(2)));
                break;
            case BOTTOM:
                field.yProperty().bind(rectangleToPositionOn.yProperty().add(rectangleToPositionOn.heightProperty()).subtract(field.fitHeightProperty().divide(2)));
        }


        field.setOnMouseClicked(event -> {
            if(gameInitialiiziationDone) {
                if(!field.isEmpty() && currentPlayerHasThreeInARow){
                    MuehleColor opponentOfCuurentPlayerColor = (currentPlayer.getColor()== MuehleColor.WHITE) ? MuehleColor.BLACK : MuehleColor.WHITE;
                    if(field.getFigure().getColor() == opponentOfCuurentPlayerColor){
                        field.removeFigure();
                        currentMoveOptions.clear();
                        optionHighlightingRectangles.forEach(rect -> rect.setVisible(false));
                        currentPlayerHasThreeInARow=false;
                        togglePlayer();
                    }
                }else if (!field.isEmpty() && field.getFigure().getColor().equals(currentPlayer.getColor())) {
                    selectionHighlightingRectangle.xProperty().bind(field.xProperty());
                    selectionHighlightingRectangle.yProperty().bind(field.yProperty());
                    selectionHighlightingRectangle.setVisible(true);

                    currentlySelectedField = field;
                    if(Board.getInstance().getAllFields().stream().filter(f -> !f.isEmpty()).filter(f -> f.getFigure().getColor() == field.getFigure().getColor()).count() <4){
                        currentMoveOptions = Board.getInstance().getAllFields().stream().filter(f -> f.isEmpty()).collect(Collectors.toList());
                    }else {
                        currentMoveOptions = Util.getMoveOptions(field);
                    }
                    highlightMoveOptions();

                } else if (currentMoveOptions.contains(field)) {
                    currentlySelectedField.moveFigureTo(field);
                    currentMoveOptions.clear();
                    if (Util.checkForThreeInARow(field) == true){
                        currentPlayerHasThreeInARow = true;
                        currentMoveOptions = Board.getInstance().getAllFields().stream().filter(f -> !f.isEmpty()).collect(Collectors.toList());
                        currentMoveOptions = currentMoveOptions.stream().filter(f -> !f.getFigure().getColor().equals(currentPlayer.getColor())).collect(Collectors.toList());
                        highlightMoveOptions();
                        selectionHighlightingRectangle.setVisible(false);
                        currentlySelectedField = null;
                    }else {
                        currentlySelectedField = null;
                        optionHighlightingRectangles.forEach(rect -> rect.setVisible(false));
                        selectionHighlightingRectangle.setVisible(false);
                        togglePlayer();
                    }

                }
            }
            else if(field.isEmpty()) {
                if(currentPlayer.hasFiguresLeft() && !currentPlayerHasThreeInARow) {
                    currentPlayer.placeFigureOnField(field);
                    if(Util.checkForThreeInARow(field) == true){
                        currentPlayerHasThreeInARow = true;
                        currentMoveOptions = Board.getInstance().getAllFields().stream().filter(f -> !f.isEmpty()).collect(Collectors.toList());
                        currentMoveOptions = currentMoveOptions.stream().filter(f -> !f.getFigure().getColor().equals(currentPlayer.getColor())).collect(Collectors.toList());
                        highlightMoveOptions();
                    }else {
                        togglePlayer();
                    }
                }
                boolean allPlayerOutOfFigures = true;
                for (Player player : players) {
                    allPlayerOutOfFigures &= !player.hasFiguresLeft();
                }
                if (allPlayerOutOfFigures){
                    gameInitialiiziationDone = true;
                }

            }
            else if(!field.isEmpty() && field.getFigure().getColor() != currentPlayer.getColor() && currentPlayerHasThreeInARow){
                if (currentMoveOptions.contains(field)) {
                    currentMoveOptions.clear();
                    field.removeFigure();
                    currentPlayerHasThreeInARow = false;
                    optionHighlightingRectangles.forEach(rect -> rect.setVisible(false));
                    togglePlayer();
                }
            }

        });

        return field;
    }

    private void checkForThreeInARow() {
    }

    private void togglePlayer(){
        currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % 2);
    }

    private void highlightMoveOptions(){
        Iterator<ResizableRectangle> rectangleIterator = optionHighlightingRectangles.iterator();
        optionHighlightingRectangles.forEach(rect -> rect.setVisible(false));
        for (Field f : currentMoveOptions){
            ResizableRectangle resizableRectangle = rectangleIterator.next();
            resizableRectangle.xProperty().bind(f.xProperty());
            resizableRectangle.yProperty().bind(f.yProperty());
            resizableRectangle.widthProperty().bind(figureSizeProperty);
            resizableRectangle.heightProperty().bind(figureSizeProperty);
            resizableRectangle.setVisible(true);
        }

    }

    @Override public void stop() {
    }

    public static void main(String[] argv){
        launch(argv);
    }
}
