package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private RestaurantCategoryDao restaurantCategoryDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryEntity> getAllCategories(){
        return categoryDao.getAllCategories();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryItemEntity> getCategoryItemsById(String categoryUuid) throws CategoryNotFoundException {

        if(categoryUuid == null){
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        }

        List<CategoryItemEntity> categoryItemEntities = categoryDao.getCategoryItemsById(categoryUuid);

        if(categoryItemEntities.isEmpty()){
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }

        return categoryItemEntities;
    }


    public List<CategoryEntity> getCategoriesByRestaurant(RestaurantEntity restaurant) {

        List<RestaurantCategoryEntity> restaurantCategoryEntities = restaurantCategoryDao.getCategoryByRestaurantId(restaurant);

        List<CategoryEntity> categoryEntities = new ArrayList<CategoryEntity>();

        for (RestaurantCategoryEntity restaurantCategoryEntity : restaurantCategoryEntities) {
            categoryEntities.add(restaurantCategoryEntity.getCategory());
        }

        return categoryEntities;
    };
}
