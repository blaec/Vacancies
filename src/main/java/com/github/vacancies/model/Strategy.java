package com.github.vacancies.model;

import com.github.vacancies.vo.Vacancy;

import java.util.List;

public interface Strategy {
    public List<Vacancy> getVacancies(String searchString);
}
