package com.dixonnet.tenpair;

import java.util.ArrayList;
import java.util.List;

class Game {
    private int width;
    private boolean dealAnyTime;

    Game(int width, boolean dealAnyTime)
    {
        this.width = width;
        this.dealAnyTime = dealAnyTime;
    }

    int getWidth() {
        return width;
    }

    Iterable<Position> moves(Position p)
    {
        List<Position> answer = new ArrayList<>();
        int bound = bound();
        for (Position move : movesInternal(p) )
        {
            if (move.heuristic() <= bound)
            {
                answer.add(move);
            }
        }
        return answer;
    }

    private Iterable<Position> movesInternal(Position p)
    {
        List<Position> answer = new ArrayList<>();

        char[] chars = p.getCells().toCharArray();
        int count = chars.length;
        boolean foundMove = false;
        for (int i = 0; i < count - 1; ++i)
        {
            if (' ' == chars[i])
            {
                continue;
            }
            for (int delta = 1; delta <= width; delta += width - 1)
            {
                int match = canRemove(chars, i, delta);
                if (-1 != match)
                {
                    foundMove = true;
                    char[] newCells = p.getCells().toCharArray();
                    newCells[i] = ' ';
                    newCells[match] = ' ';
                    Position move = new Position(p, new String(newCells), p.getNonEmptyCount() - 2);
                    answer.add(move);
                }
            }
        }
        if (!foundMove || dealAnyTime)
        {
            answer.add(new Position(p));
        }
        return answer;
    }

    private int canRemove(char[] chars, int start, int delta)
    {
        int check = start + delta;
        while (check < chars.length)
        {
            if (chars[check] == chars[start] || '0' + '0' + width + 1 == chars[check] + chars[start])
            {
                return check;
            }
            if (' ' != chars[check])
            {
                break;
            }
            check += delta;
        }
        return -1;
    }

    private int bound() {
        if (dealAnyTime) {
            return width % 2 == 0 ? width * 3 / 2 : width * 3;
        }
        return 50;
    }
}
