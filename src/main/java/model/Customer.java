package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class Customer {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate signedUpDate;
}
