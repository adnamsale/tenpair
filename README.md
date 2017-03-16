# TenPair
A simple solver for [TenPair](http://www.logicgamesonline.com/tenpair/).

## Usage
```
./gradlew run -Dexec.args="-w9 -d"
```

where w is the puzzle width (i.e., w9 means that the puzzle contains digits 1-9 as in the example linked above). If the
d option is specified, solutions are allowed to deal at any time. Without that option, solutions can only deal when no
other moves remain.

## Brief Analysis
The game is trivial for even widths as corresponding cells can be matched with no need for any additional deals. Since there 
are *3w* cells initially (3 rows, each w wide) and each move removes two cells, the puzzle can be solved in *3w/2* moves.

Odd widths are more complicated since the 'middle' number prevents the simple solution for even widths. If deals are allowed
at any time then there is a simple solution by immediately dealing and then clearing the left and right columns and matching
up the the remaining cells. As there are initially *3w* cells and dealing does not count as a move, a solution exists with *3w*
moves. In general we can do better - for w9 the optimal solution has 25 moves - although experimentation suggests we can't do
much better with *3w - 1* being a typical optimal solution. Solutions are clearly longer if deals are only permitted once all other
moves are exhausted. I don't have a bound on that yet.
