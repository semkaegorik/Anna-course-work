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
    private double minY = Integer.MAX_VALUE;
    private double maxY = Integer.MIN_VALUE;
    private double currentY;
    private double criticalY;
    private final List<ActiveEdgeMetaData> activeEdgeList = new ArrayList<>();

    /*
        конструктор, принимающий список ребер фигуры
    */
    public Painter(List<Edge> edges) {
        this.edges = edges;
    }

    /*
        основной метод, с которого начинает свою работу алгоритм по закрашиваню фигур
    */
    public void paint() {
        initLimits();
        currentY = minY;
        criticalY = minY;
        while (currentY <= maxY) {
            if (currentY == criticalY) {
                deleteNotPairLines();
                addNewEdgesInAEL();
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
                deletePairLines();
                updateCriticalValue();
            }

            activeEdgeList.forEach(md -> md.setX(md.getX() + md.getDeltaX()));
            currentY++;
        }
    }

    /*
        метод, который обновляет Y критическое
    */
    private void updateCriticalValue() {
        Set<Double> collect = edges.stream()
                .map(edge -> edge.getFirst().getY())
                .filter(y -> y > currentY)
                .collect(Collectors.toSet());
        collect.addAll(activeEdgeList.stream()
                               .map(ActiveEdgeMetaData::getY2)
                               .collect(Collectors.toSet()));
        Optional<Double> min = collect.stream().min(Double::compareTo);
        criticalY = min.orElseGet(() -> criticalY);
    }

    /*
        метод, удаляющий парные отрезки из списка активных ребер
    */
    private void deletePairLines() {
        for (int i = 0; i < activeEdgeList.size() - 1; i++) {
            if (activeEdgeList.get(i).getY2() == currentY
                    && activeEdgeList.get(i + 1).getY2() == currentY) {
                activeEdgeList.remove(i);
                activeEdgeList.remove(i);
                i--;
            }
        }
    }

    /*
        метод, добавляющий в список активных ребер новые ребра
    */
    private void addNewEdgesInAEL() {
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
    }

    /*
        метод, удаляющий непарные отрезки из списка активных ребер
    */
    private void deleteNotPairLines() {
        for (int i = 0; i < activeEdgeList.size(); i++) {
            if ((activeEdgeList.get(i).getY2() == currentY)
                    && ((i == activeEdgeList.size()-1) || (activeEdgeList.get(i + 1).getY2() != currentY))
                    && ((i == 0) || (activeEdgeList.get(i - 1).getY2() != currentY))) {
                activeEdgeList.remove(i);
                i--;
            }
        }
    }

    /*
        метод, который зарисовывает линии из списка активных ребер
    */
    private void printLinesFromAEL() {
        for (int i = 0; i < activeEdgeList.size() - 1; i += 2) {
            printLine(activeEdgeList.get(i).getX(), activeEdgeList.get(i + 1).getX(), currentY);
        }
    }

    private void printLine(double x, double x1, double y) {
        System.out.println(MessageFormat.format("paint line x: {0} x2: {1} line(y): {2}", x, x1, y));
    }

    /*
        метод, который высчитывает знаение дельта X для каждого активного ребра
    */
    private double getDeltaX(Edge edge) {
        return (edge.getSecond().getX() - edge.getFirst().getX()) / (edge.getSecond().getY() - edge.getFirst().getY());
    }

    /*
        метод, который сортирует активные ребра по первому значению, если же они равны, то по третьему
    */
    private void sortEdges() {
        activeEdgeList.sort(Comparator.comparingDouble(ActiveEdgeMetaData::getX).thenComparingDouble(ActiveEdgeMetaData::getDeltaX));
    }

    /*
        метод, который инициализирует интервал для работы алгоритма
        (макимальное и минимальное значения для y)
    */
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
