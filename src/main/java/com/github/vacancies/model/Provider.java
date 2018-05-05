package com.github.vacancies.model;

import com.github.vacancies.vo.Vacancy;

import java.util.Collections;
import java.util.List;

public class Provider{
    Strategy strategy;

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString) {
        if (this.strategy == null)
            return Collections.emptyList();
        return strategy.getVacancies(searchString);
    }
}
