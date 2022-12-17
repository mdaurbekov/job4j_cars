package ru.job4j.cars.repository;


import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;


    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    public void update(User user) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "UPDATE User SET login = :flogin, password = :fpassword WHERE id = :fId")
                .setParameter("flogin", user.getLogin())
                .setParameter("fpassword", user.getPassword())
                .setParameter("fId", user.getId())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удалить пользователя по id.
     *
     * @param userId ID
     */
    public void delete(int userId) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                        "DELETE User WHERE id = :fId")
                .setParameter("fId", userId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User", User.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User as u where u.id = :fid", User.class);
        query.setParameter("fid", id);
        Optional<User> result = query.uniqueResultOptional();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User as u where u.login LIKE :key", User.class);
        query.setParameter("key", key);
        session.getTransaction().commit();
        List<User> result = query.list();
        session.close();
        return result;
    }

    /**
     * Найти пользователя по login.
     *
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query<User> query = session.createQuery(
                "from User as u where u.login = :login", User.class);
        query.setParameter("login", login);
        session.getTransaction().commit();
        Optional<User> result = query.uniqueResultOptional();
        session.close();
        return result;
    }
}