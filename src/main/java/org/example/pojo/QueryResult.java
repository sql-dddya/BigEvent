package org.example.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class QueryResult<T> {
    private Long total;
    private Long page;
    private List<T> items;
}
