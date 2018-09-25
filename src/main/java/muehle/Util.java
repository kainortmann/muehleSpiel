package muehle;

import muehle.enums.Direction;
import muehle.enums.RectanglePosition;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        final int searchX = field.getXPositionOnImaginaryGrid();
        final int searchY = field.getYPositionOnImaginaryGrid();

        List<Field> possibleXNeighbours = Board.getInstance().getAllFields().stream().filter(f -> f.getYPositionOnImaginaryGrid() == searchY && f.getXPositionOnImaginaryGrid() != searchX).collect(Collectors.toList());
        List<Field> possibleYNeighbours = Board.getInstance().getAllFields().stream().filter(f -> f.getXPositionOnImaginaryGrid() == searchX && f.getYPositionOnImaginaryGrid() != searchY).collect(Collectors.toList());


        switch (direction) {
            case LEFT:
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
                break;
            case RIGHT:
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
                break;

            case DOWN:
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
                break;
            case UP:
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
                break;
        }
        return null;
    }

}
