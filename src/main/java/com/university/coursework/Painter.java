package com.university.coursework;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Painter {
    private final List<Edge> edges;
    private int minY = Integer.MAX_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int currentY;
    private int criticalY;
    private final List<ActiveEdgeMetaData> activeEdgeList = new ArrayList<>();

    public Painter(List<Edge> edges) {
        this.edges = edges;
    }

    public void paint() {
        sortEdges();
        initLimits();
        currentY = minY;
        criticalY = minY;
        while (currentY <= maxY) {
            if (currentY == criticalY) {
                activeEdgeList.forEach(md -> {
                    if (md.getY2() == currentY
                            && activeEdgeList.get(activeEdgeList.indexOf(md) + 1).getY2() != currentY) {
                        activeEdgeList.remove(md);
                    }
                });

                edges.forEach(edge -> {
                    if (edge.getFirst().getY() == currentY
                            && edge.getFirst().getY() != edge.getSecond().getY()) {
                        activeEdgeList.add(new ActiveEdgeMetaData(
                                edge.getFirst().getX(),
                                getDeltaX(edge),
                                edge.getSecond().getY()
                        ));
                    }
                });

                sortEdges();

                edges.forEach(edge -> {
                    if (edge.getFirst().getY() == edge.getSecond().getY()
                            && edge.getFirst().getY() == currentY) {
                        printLine(edge.getFirst().getX(), edge.getSecond().getX(), edge.getSecond().getY());
                    }
                });
            }

            printLinesFromAEL();

            if (currentY == criticalY) {
                activeEdgeList.forEach(md -> {
                    if (md.getY2() == currentY
                            && activeEdgeList.get(activeEdgeList.indexOf(md) + 1).getY2() == currentY) {
                        activeEdgeList.remove(md);
                        activeEdgeList.remove(activeEdgeList.indexOf(md) + 1);
                    }
                });

                Set<Integer> collect = edges.stream()
                        .map(edge -> edge.getFirst().getY())
                        .collect(Collectors.toSet());
                collect.addAll(activeEdgeList.stream()
                                       .map(ActiveEdgeMetaData::getY2)
                                       .collect(Collectors.toSet()));
                Optional<Integer> min = collect.stream().min(Integer::compareTo);
                criticalY = min.get();
            }

            activeEdgeList.forEach(md -> md.setX(md.getX() + md.getDeltaX()));
        }
    }

    private void printLinesFromAEL() {
        for (int i = 0; i < activeEdgeList.size(); i += 2) {
            printLine(activeEdgeList.get(i).getX(), activeEdgeList.get(i + 1).getX(), currentY);
        }
    }

    private void printLine(int x, int x1, int y) {
        System.out.println(MessageFormat.format("paint line x: {0} x2: {1} line(y): {2}", x, x1, y));
    }


    private int getDeltaX(Edge edge) {
        return (edge.getSecond().getX() - edge.getFirst().getX()) / (edge.getSecond().getY() - edge.getFirst().getY());
    }

    private void sortEdges() {
        activeEdgeList.sort(Comparator.comparingInt(ActiveEdgeMetaData::getDeltaX).thenComparingInt(ActiveEdgeMetaData::getX));
    }

    private void initLimits() {
        edges.forEach(e -> {
            if (e.getFirst().getY() < minY) {
                minY = e.getFirst().getY();
            }
            if (e.getSecond().getY() > maxY) {
                maxY = e.getSecond().getY();
            }
        });
    }
}
