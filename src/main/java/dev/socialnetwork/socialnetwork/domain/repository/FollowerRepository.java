package dev.socialnetwork.socialnetwork.domain.repository;

import dev.socialnetwork.socialnetwork.domain.model.Followers;
import dev.socialnetwork.socialnetwork.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Followers> {
    public boolean follower(User follower, User user){
        var params = Parameters.with("follower",follower).and("user",user).map();
        PanacheQuery<Followers> query =  find("follower = :follower and user = :user",params);
        Optional<Followers> result = query.firstResultOptional();
        return result.isPresent();
    }

    public List<Followers> findByUser(Long userId){
        PanacheQuery<Followers> query = find("user.id",userId);
        return query.list();
    }
}
