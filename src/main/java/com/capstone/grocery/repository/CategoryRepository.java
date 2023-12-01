package com.capstone.grocery.repository;
import com.capstone.grocery.model.CategoryOfTree;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoryRepository extends MongoRepository<CategoryOfTree, Integer> {

}
