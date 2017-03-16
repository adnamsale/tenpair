package com.dixonnet.tenpair;

import java.util.Comparator;

class Position {
    private String cells;
    private int moveCount;
    private int nonEmptyCount;
    private Position parent;

    static Position InitialPosition(Game g)
    {
        return new Position(g);
    }

    // Create the initial position from a game spec
    private Position(Game g)
    {
        char[] chars = new char[3 * g.getWidth()];
        for (int i = 0; i < chars.length; ++i)
        {
            chars[i] = '1';
        }
        for (int i = 0; i < g.getWidth(); ++i)
        {
            chars[i] = (char)('0' + (i + 1));
            chars[g.getWidth() + 1 + 2 * i] = (char)('0' + (i + 1));
        }
        cells = new String(chars);
        moveCount = 0;
        nonEmptyCount = chars.length;
    }

    // Create the position after dealing from the given parent
    Position(Position parent) {
        moveCount = parent.moveCount;
        cells = parent.cells + parent.cells.replace(" ", "");
        nonEmptyCount = 2 * parent.nonEmptyCount;
        this.parent = parent;

    }

    // Create the position specified by cells with the given parent
    Position(Position parent, String cells, int nonEmptyCount) {
        this.moveCount = parent.moveCount + 1;
        this.cells = cells;
        this.nonEmptyCount = nonEmptyCount;
        this.parent = parent;
    }

    int heuristic()
    {
        return moveCount + (1 + nonEmptyCount) / 2;
    }

    int getNonEmptyCount() {
        return nonEmptyCount;
    }

    Position getParent() {
        return parent;
    }

    String getCells() {
        return cells;
    }

    private static class HeuristicComparer implements Comparator<Position>
    {
        public int compare(Position x, Position y)
        {
            int answer = (int)Math.signum(x.heuristic() - y.heuristic());
            if (0 != answer)
            {
                return answer;
            }
            answer = (int)Math.signum(x.getNonEmptyCount() - y.getNonEmptyCount());
            if (0 != answer) {
                return answer;
            }
            return x.toString().compareTo(y.toString());
        }
    }

    static Comparator<Position> HEURISTIC_COMPARER = new HeuristicComparer();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Position && cells.equals(((Position)obj).cells);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return cells;
    }
}
