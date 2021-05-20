package com.university.coursework;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Painter painter = new Painter(List.of(
                new Edge(new Point(0, 0), new Point(5, 0)),
                new Edge(new Point(5, 0), new Point(5, 5)),
                new Edge(new Point(5, 5), new Point(0, 5)),
                new Edge(new Point(0, 5), new Point(0, 0))
        ));
        painter.paint();
    }
}
