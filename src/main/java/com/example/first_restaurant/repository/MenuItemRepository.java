package com.example.first_restaurant.repository;

import com.example.first_restaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface handles all database operations for MenuItems.
 * By extending JpaRepository, we get basic CRUD operations for free.
 * Spring automatically implements this interface, eliminating the need for manual database code.
 * The types <MenuItem, Long> specify which entity we're managing and its ID type.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    // All these methods come automatically:
    // save()
    // findById()
    // findAll()
    // delete()
    // And many more!
}