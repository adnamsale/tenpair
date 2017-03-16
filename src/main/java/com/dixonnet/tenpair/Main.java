package com.dixonnet.tenpair;

import java.io.IOException;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class Main {
    public static void main(String[] args) {
        Game g = createGame(args);
        if (null == g) {
            return;
        }

        SortedSet<Position> queue = new TreeSet<>(Position.HEURISTIC_COMPARER);
        queue.add(Position.InitialPosition(g));
        Position solution = null;
        HashSet<Position> seen = new HashSet<>();
        mainLoop: while (!queue.isEmpty())
        {
            Position p = queue.first();
            queue.remove(p);
            if (0 == seen.size() % 1000)
            {
                System.out.println("Seen: " + seen.size() + ", Moves: " + p.heuristic());
                if (5000000 < seen.size())
                {
                    seen.clear();
                }
            }
            for (Position child : g.moves(p))
            {
                if (0 == child.getNonEmptyCount())
                {
                    solution = child;
                        break mainLoop;
                }
                if (!seen.contains(child))
                {
                    seen.add(child);
                    queue.add(child);
                }
            }
        }
        if (null != solution)
        {
            Position p = solution;
            while (null != p)
            {
                System.out.println(p);
                p = p.getParent();
            }
            System.out.println(solution.heuristic() + " moves");
        }
    }

    private static Game createGame(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<Integer> width = parser.accepts("w", "puzzle width").withRequiredArg().ofType(Integer.class).required();
        OptionSpec dealAnyTime = parser.accepts("d", "deal at any time");
        OptionSet options;
        try {
            options = parser.parse(args);

        }
        catch (Exception e) {
            try {
                parser.printHelpOn(System.out);
            }
            catch (IOException offs) { /**/ }
            return null;
        }
        return new Game(options.valueOf(width), options.has(dealAnyTime));
    }
}
