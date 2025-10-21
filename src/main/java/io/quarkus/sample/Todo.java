package io.quarkus.sample;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
public class Todo extends PanacheEntity {

    @NotBlank
    @Column(unique = true)
    public String title;

    @Schema(description = "Detailed description of the todo item")
    @Column(length = 2048)
    public String description;

    public boolean completed;

    @Column(name = "ordering")
    public int order;

    public String url;

    @Schema(description = "Category of the todo item")
    public String category;

    public static List<Todo> findNotCompleted() {
        return list("completed", false);
    }

    public static List<Todo> findCompleted() {
        return list("completed", true);
    }

    public static long deleteCompleted() {
        return delete("completed", true);
    }

    public static Todo findByTitle(String title) {
        return find("title", title).firstResult();
    }
}
