package triangle.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;
//import org.hibernate.search.annotations.Field;

@Entity // This tells Hibernate to make a table out of this class
//@Table(name = "triangle",indexes = @Index(name = "idx_triangle_state",columnList = "state"))
public class Triangle extends TimestampEntity {
    private static final Logger log = LoggerFactory.getLogger(Triangle.class);


    private static final AtomicInteger count = new AtomicInteger(0);


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@Column(name ="edgeA" )
    private Integer edgeA;
    //@Column(name = "edgeB")
    private Integer edgeB;
    //@Column(name = "hypotenuse")
    private Double hypotenuse;


    //@Column(name = "state")
    private String state;

    //default constructor
    public Triangle() {
        log.debug("Triangle default constructor ");
        id = count.getAndIncrement();
        state = State.PRE_CALC.name();
    }

    //copy constructor
    public Triangle(Triangle triangle) {
        super(triangle);
        log.debug("Triangle copy constructor");
        this.id = triangle.getId();
        this.edgeA = triangle.edgeA;
        this.edgeB = triangle.edgeB;
        this.hypotenuse = calculateHypotenuse(edgeA, edgeB);
        state = State.POST_CALC.name();
    }


    public Integer getId() {
        return id;
    }

    public Integer getEdgeA() {
        return edgeA;
    }

    public Integer getEdgeB() {
        return edgeB;
    }

    public Double getHypotenuse() {
        return hypotenuse;
    }

    public String getState() {
        return state;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triangle triangle = (Triangle) o;

        if (Double.compare(triangle.hypotenuse, hypotenuse) != 0) return false;
        if (id != null ? !id.equals(triangle.id) : triangle.id != null) return false;
        if (edgeA != null ? !edgeA.equals(triangle.edgeA) : triangle.edgeA != null) return false;
        if (edgeB != null ? !edgeB.equals(triangle.edgeB) : triangle.edgeB != null) return false;
        return state != null ? state.equals(triangle.state) : triangle.state == null;
    }
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (edgeA != null ? edgeA.hashCode() : 0);
        result = 31 * result + (edgeB != null ? edgeB.hashCode() : 0);
        temp = Double.doubleToLongBits(hypotenuse);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "id=" + id +
                ", edgeA=" + edgeA +
                ", edgeB=" + edgeB +
                ", hypotenuse=" + hypotenuse +
                ", state='" + state + '\'' +
                '}';
    }


    ////Triangle utils
    private static Double calculateHypotenuse(double edgeA, double edgeB) {
        return Math.sqrt(Math.pow(edgeA, 2) + Math.pow(edgeB, 2));

    }

    public static boolean validateTriangleFromClient(Triangle triangle) {
        if (triangle == null
                || triangle.getId() == null || triangle.getId() < 1
                || triangle.getEdgeA() == null || triangle.getEdgeA() < 1
                || triangle.getEdgeB() == null || triangle.getEdgeB() < 1
                || triangle.getHypotenuse() != null
                || triangle.getState() == null || !(triangle.getState().equals(State.PRE_CALC.name()))) {
            log.debug("validateTriangleFromClient ALERT for this triangle from client:" + triangle);

            return false;
        } else {
            return true;
        }
    }



}

