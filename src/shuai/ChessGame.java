package shuai;

import java.util.List;

public class ChessGame {
    class Point {
        public final int x;
        public final int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    class Exit {
        Point start;
        Point end;
        public Exit(Point start, Point end){
            this.start = start;
            this.end = end;
        }
    }
    class Rectangle {
        Point origin;
        int size;
        public Rectangle(Point origin, int size){
            this.origin = origin;
            this.size = size;
        }
    }
    class Dashboard {
        public int column;
        public int row;
        public int[][] dashboard;
        public Exit exit;
        public List<Rectangle> rectangles;
        public Dashboard() {

        }

        public void init(int column, int row, Exit exit, List<Rectangle> rectangles) {
            this.column = column;
            this.row = row;
            this.dashboard = new int[row][column];
            this.exit = exit;
            // Assumption : No overlap for initial state, all rectangles are in range
            for(Rectangle r : rectangles) {
                Point origin = r.origin;
                for(int i = origin.x; i < origin.x + r.size; i++) {
                    for(int j = origin.y; j < origin.y + r.size; j++) {
                        dashboard[i][j] = -1;
                    }
                }
            }
        }

        public boolean move(Rectangle rec, int distance, boolean isHoritontalScroll) {
            int originX = rec.origin.x;
            int originY = rec.origin.y;
            int start, destination;
            if(isHoritontalScroll) {
                start = originX + rec.size;
                destination = start + distance;
                // check out of range
                if(destination >= column || destination < 0) {
                    return false;
                }
                // check overlap
                for(int i = start; i < destination; i++) {
                    for (int j = originY; j < originY + rec.size; j++) {
                        if (dashboard[i][j] == -1) {
                            return false;
                        }
                    }
                }

            } else {
                start = originY + rec.size;
                destination = start + distance;
                if(destination >= row || destination < 0) {
                    return false;
                }
                // check overlap
                for(int i = start; i < destination; i++) {
                    for (int j = originX; j < originX + rec.size; j++) {
                        if (dashboard[j][i] == -1) {
                            return false;
                        }
                    }
                }
            }

            // Can move

            // 1 clean up, 2 write to new origin

            for(int i = originX; i < originX + rec.size; i++) {
                for(int j = originY; i < originY + rec.size; j++) {
                    dashboard[i][j] = 0;
                }
            }
            if(isHoritontalScroll) {
                originX += distance;
            } else {
                originY += distance;
            }
            for(int i = originX; i < originX + rec.size; i++) {
                for(int j = originY; i < originY + rec.size; j++) {
                    dashboard[i][j] = -1;
                }
            }

            return true;
        }

    }
}
