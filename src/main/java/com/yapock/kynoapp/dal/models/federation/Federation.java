package com.yapock.kynoapp.dal.models.federation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.Builder;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Federation {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    @NotNull
    @NotBlank
    private String name;
    @NotBlank
    @NotNull
    private String country;
    private String url;


}
