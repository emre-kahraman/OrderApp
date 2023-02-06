package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class Invoice {

    private Long id;
    private Company company;
    private BigDecimal price;
    private LocalDate date;
}
