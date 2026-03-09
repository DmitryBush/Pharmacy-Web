package com.bush.search.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@Getter
public class FilteredResponse<T, A> extends RepresentationModel<FilteredResponse<T, A>> {
    private PagedModel<EntityModel<T>> pageResponse;
    private A filterAggregation;
}
