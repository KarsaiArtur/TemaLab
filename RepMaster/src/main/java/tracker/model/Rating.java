package tracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int id;

    private double rating;
    private String comment;

    @ManyToOne
    private Exercise exercise;
    @ManyToOne
    private Workout workout;
    @ManyToOne
    private Gym gym;
    @ManyToOne
    private RegisteredUser registeredUser;
}

