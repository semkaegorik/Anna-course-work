package com.university.coursework;

import lombok.Getter;

@Getter
public class Edge {
    private final Point first;
    private final Point second;

    public Edge(Point first, Point second) {
        this.first = first.getY() <= second.getY() ? first : second;
        this.second = second.getY() >= first.getY() ? second : first;
    }
}
