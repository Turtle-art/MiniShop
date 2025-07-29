package com.dariel.minishop.mapper;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelMapper<TModel, TDto> {

    public abstract TDto mapFrom(TModel model);

    public List<TDto> mapFrom(Iterable<TModel> models) {
        var result = new ArrayList<TDto>();
        for (var model : models) {
            result.add(mapFrom(model));
        }
        return result;
    }

    public abstract TModel mapTo(TDto dto);

    public List<TModel> mapTo(List<TDto> dtos) {
        var result = new ArrayList<TModel>();
        for (var dto : dtos) {
            result.add(mapTo(dto));
        }
        return result;
    }

    public void mapTo(TModel source, TModel destination)
    {
    }

}
