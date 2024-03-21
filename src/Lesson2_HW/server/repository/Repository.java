package Lesson2_HW.server.repository;

public interface Repository<T> {
    void save(T text);
    T load();
}