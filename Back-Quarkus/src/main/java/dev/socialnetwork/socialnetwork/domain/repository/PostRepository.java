package dev.socialnetwork.socialnetwork.domain.repository;

import dev.socialnetwork.socialnetwork.domain.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
