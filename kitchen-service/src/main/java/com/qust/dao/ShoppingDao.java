package com.qust.dao;

import com.qust.entity.ShoppingCart;
import com.qust.entity.ShoppingCartPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ShoppingDao extends JpaRepository<ShoppingCart, ShoppingCartPk> {

    @Query(value = "select s.food,s.count,f.name,f.price from shopping_cart as s join food_menu as f on s.food = f.id where s.restaurant = ?1 and s.client = ?2 ", nativeQuery = true)
    List<Object[]> getCart (Long restaurant,Long cif);

    @Transactional
    @Modifying
    @Query(value = "delete from shopping_cart  where restaurant = ?1 and client = ?2  and food = ?3", nativeQuery = true)
    void deleteByfood (Long restaurant,Long cif,Long food);

    @Transactional
    @Modifying
    @Query(value = "update shopping_cart  set count = ?4 where restaurant = ?1 and client = ?2  and food = ?3", nativeQuery = true)
    void updateNum (Long restaurant,Long cif,Long food,int count);

    @Query(value = "select count(*) from  shopping_cart where restaurant = ?1 and client = ?2 and food = ?3 ", nativeQuery = true)
    int grtFoodC (Long restaurant,Long cif,Long food);

    @Transactional
    @Modifying
    @Query(value = "update shopping_cart  set count = count + 1 where restaurant = ?1 and client = ?2  and food = ?3", nativeQuery = true)
    void addCount (Long restaurant,Long cif,Long food);
}
