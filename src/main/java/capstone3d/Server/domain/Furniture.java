package capstone3d.Server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "furniture_id")
    private Long id;

    private String category;

    private String furniture_url;

    private String furniture_img_url;

    private double x;

    private double y;

    private double z;
}