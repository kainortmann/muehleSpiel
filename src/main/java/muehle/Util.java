package muehle;

import muehle.enums.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {




    public static List<Field> getMoveOptions(Field field){
        List<Field> neighbours = new ArrayList<>();
        List<Direction> searchDirections = Arrays.asList(Direction.UP,Direction.DOWN,Direction.LEFT,Direction.RIGHT);
        searchDirections.stream().forEach(direction -> {
            Field possibleNeighbour = getNeighbourOfField(field,direction);
            if(possibleNeighbour != null && possibleNeighbour.isEmpty()){
                neighbours.add(possibleNeighbour);
            }
        });
        return neighbours;
    }

    private static Field getNeighbourOfField(Field field, Direction direction){
        switch (direction) {
            case LEFT:
                return getLeftNeighbour(field);
            case RIGHT:
                return getRightmNeighbour(field);
            case DOWN:
                return getBottomNeighbour(field);
            case UP:
                return getUpperNeighbour(field);
        }
        return null;
    }


    public static Field getUpperNeighbour(Field field){

        final int searchX = field.getXPositionOnImaginaryGrid();
        final int searchY = field.getYPositionOnImaginaryGrid();

        List<Field> possibleYNeighbours = Board.getInstance().getAllFields().stream().filter(f -> f.getXPositionOnImaginaryGrid() == searchX && f.getYPositionOnImaginaryGrid() != searchY).collect(Collectors.toList());

        for (int i = searchY; i >= 0; i--) {
            if(i==3 && searchX==3){ //do not search across the center of the field
                break;
            }
            for (Field f : possibleYNeighbours) {
                if (f.getYPositionOnImaginaryGrid() == i) {
                    return f;
                }
            }
        }
        return null;
    }

    public static Field getBottomNeighbour(Field field){

        final int searchX = field.getXPositionOnImaginaryGrid();
        final int searchY = field.getYPositionOnImaginaryGrid();

        List<Field> possibleYNeighbours = Board.getInstance().getAllFields().stream().filter(f -> f.getXPositionOnImaginaryGrid() == searchX && f.getYPositionOnImaginaryGrid() != searchY).collect(Collectors.toList());

        for (int i = searchY; i <= 6; i++) {
            if(i==3 && searchX==3){ //do not search across the center while searching
                break;
            }
            for (Field f : possibleYNeighbours) {
                if (f.getYPositionOnImaginaryGrid() == i) {
                    return f;
                }
            }
        }
        return null;
    }

    public static Field getRightmNeighbour(Field field){

        final int searchX = field.getXPositionOnImaginaryGrid();
        final int searchY = field.getYPositionOnImaginaryGrid();

        List<Field> possibleXNeighbours = Board.getInstance().getAllFields().stream().filter(f -> f.getYPositionOnImaginaryGrid() == searchY && f.getXPositionOnImaginaryGrid() != searchX).collect(Collectors.toList());


        for (int i = searchX; i <= 6; i++) {
            if(i==3 && searchY==3){ //do not search across the center of the field
                break;
            }
            for (Field f : possibleXNeighbours) {
                if (f.getXPositionOnImaginaryGrid() == i) {
                    return f;
                }
            }
        }
        return null;
    }
    public static Field getLeftNeighbour(Field field){

        final int searchX = field.getXPositionOnImaginaryGrid();
        final int searchY = field.getYPositionOnImaginaryGrid();


        List<Field> possibleXNeighbours = Board.getInstance().getAllFields().stream().filter(f -> f.getYPositionOnImaginaryGrid() == searchY && f.getXPositionOnImaginaryGrid() != searchX).collect(Collectors.toList());


        for (int i = searchX; i >= 0; i--) {
            if(i==3 && searchY==3){ //do not search across the center of the board
                break;
            }
            for (Field f : possibleXNeighbours) {

                if (f.getXPositionOnImaginaryGrid() == i) {

                    return f;
                }
            }
        }
        return null;
    }

    public static boolean checkForThreeInARow(Field f) {

        MuehleColor figureColorOnField = f.getFigure().getColor();
        List<Direction> directions = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        Field neighbourField;

        for (Direction direction : directions) {
            neighbourField = getNeighbourOfField(f,direction);
            if ( neighbourField!= null && !neighbourField.isEmpty() && neighbourField.getFigure().getColor() == figureColorOnField) {
                neighbourField = getNeighbourOfField(neighbourField, direction);
                if (neighbourField != null && !neighbourField.isEmpty() && neighbourField.getFigure().getColor() == figureColorOnField) {
                    return true;
                }
            }
        }

        neighbourField = getNeighbourOfField(f,Direction.UP);
        if (neighbourField != null && !neighbourField.isEmpty() && neighbourField.getFigure().getColor() == figureColorOnField) {
            neighbourField = getNeighbourOfField(f, Direction.DOWN);
            if (neighbourField != null && !neighbourField.isEmpty() && neighbourField.getFigure().getColor() == figureColorOnField) {
                return true;
            }
        }
        neighbourField = getNeighbourOfField(f,Direction.LEFT);
        if ( neighbourField != null && !neighbourField.isEmpty() && neighbourField.getFigure().getColor() == figureColorOnField) {
            neighbourField = getNeighbourOfField(f,Direction.RIGHT);
            if (neighbourField != null && !neighbourField.isEmpty() && neighbourField.getFigure().getColor() == figureColorOnField) {
                return true;
            }
        }
        return false;
    }

}
