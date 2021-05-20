package com.university.coursework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ActiveEdgeMetaData {
    private  double x;
    private final double deltaX;
    private final double y2;
}
