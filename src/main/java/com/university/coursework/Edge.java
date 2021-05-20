package com.university.coursework;

import lombok.Getter;

@Getter
public class Edge {
    private final Point first;
    private final Point second;

    public Edge(Point first, Point second) {
        this.first = first.getY() < second.getY() ? first : first.getY() > second.getY() ? second : first.getX() <= second.getX() ? first : second;
        this.second = first.getY() < second.getY() ? second : first.getY() > second.getY() ? first : first.getX() <= second.getX() ? second : first;
    }
}
