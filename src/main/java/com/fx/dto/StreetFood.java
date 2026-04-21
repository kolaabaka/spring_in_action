package com.fx.dto;

import lombok.Data;

import java.util.List;

@Data
public class StreetFood {
    private String name;
    private List<Ingridient> ingredient;
}
