package muehle;

import java.util.ArrayList;
import java.util.List;

public class Player {
    MuehleColor color;
    List<Figure> figures;
    public Player(MuehleColor playerColor) {
        this.color = playerColor;
        this.figures = new ArrayList<>();
        for (int i=1;i<=9;i++){
            figures.add(new Figure(color));
        }
    }

    public MuehleColor getColor() {
        return color;
    }

    public void placeFigureOnField(Field f){
        f.setFigure(figures.remove(figures.size()-1));
    }

    public boolean hasFiguresLeft() {
        return !figures.isEmpty();
    }
}
