package com.university.coursework;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Painter painter = new Painter(List.of(
                new Edge(new Point(5, 1), new Point(1, 5)),
                new Edge(new Point(1, 5), new Point(4, 5)),
                new Edge(new Point(4, 5), new Point(5, 10)),
                new Edge(new Point(5, 10), new Point(7, 8)),
                new Edge(new Point(7, 8), new Point(7, 4)),
                new Edge(new Point(7, 4), new Point(9, 5)),
                new Edge(new Point(9, 5), new Point(9, 7)),
                new Edge(new Point(9, 7), new Point(11, 7)),
                new Edge(new Point(11, 7), new Point(11, 12)),
                new Edge(new Point(11, 12), new Point(14, 9)),
                new Edge(new Point(14, 9), new Point(18, 11)),
                new Edge(new Point(18, 11), new Point(17, 1)),
                new Edge(new Point(17, 1), new Point(14, 3)),
                new Edge(new Point(14, 3), new Point(12, 1)),
                new Edge(new Point(12, 1), new Point(9, 3)),
                new Edge(new Point(9, 3), new Point(7, 1)),
                new Edge(new Point(7, 1), new Point(5, 1))

        ));
        painter.paint();
    }
}
