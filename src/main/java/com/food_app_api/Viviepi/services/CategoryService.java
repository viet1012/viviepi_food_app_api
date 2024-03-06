package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.CategoryDTO;
import com.food_app_api.Viviepi.entities.Category;
import com.food_app_api.Viviepi.exceptions.ObjectEmptyException;
import com.food_app_api.Viviepi.mapper.CategoryMapper;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;
import com.food_app_api.Viviepi.repositories.ICategoryRepository;
import com.food_app_api.Viviepi.util.UploadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryConverter;

    private final UploadLocalUtil uploadLocalUtil;


    @Autowired
    public CategoryService(final ICategoryRepository categoryRepository,
                           final CategoryMapper categoryConverter,
                           final UploadLocalUtil uploadLocalUtil) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.uploadLocalUtil = uploadLocalUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        if (categoryList.isEmpty()){
            log.info("List category is empty !");
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
        log.info("Get all category successfully !");
        return categoryConverter.toCategoryDTOList(categoryList);
    }

    @Override
    public ResponseOutput getAllCategory(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<CategoryDTO> categoryDTOList = categoryConverter.toCategoryDTOList(
                categoryRepository.findAll(pageable).getContent()
        );
        if (categoryDTOList.isEmpty()){
            log.info("List category is empty !");
            throw new ObjectEmptyException(
                    404, "List category is empty !"
            );
        }
        int totalItem = (int) categoryRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem/limit);
        log.info("Get all category successfully !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                categoryDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategoryCode(CategoryDTO categoryDTO) {
        List<Category> category = categoryRepository.findByCategoryCode(categoryDTO.getCategoryCode());
        return categoryConverter.toCategoryDTOList(category);
    }

    @Override
    public List<CategoryDTO> searchAllByName(String name) {
        return categoryConverter.toCategoryDTOList(
                categoryRepository.searchAllByNameContainingIgnoreCase(name)
        );
    }

    @Override
    public List<String> getAllNameByCategory() {
        List<String> categoryNameList = categoryRepository.getAllByCategoryName();
        if (categoryNameList.isEmpty()){
            log.info("List category name is empty !");
            throw new ObjectEmptyException(404, "List category name is empty !");
        }
        log.info("Get list category name is successfully !");
        return categoryNameList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO upsert(CategoryDTO categoryDTO) {
        Category category;
        if (categoryDTO.getId() != null){
            boolean exist = exitsCategory(categoryDTO);
            if (!exist){
                log.info("Cannot update category id: "+categoryDTO.getId());
                throw new ObjectNotFoundException(
                        404, "Cannot update category id: "+categoryDTO.getId()
                );
            }
            Category oldCategory = categoryRepository.findOneById(categoryDTO.getId());
            category = categoryConverter.toCategory(categoryDTO, oldCategory);
            log.info("Update category is completed !");
        }else {
            List<Category> categoryList = categoryConverter.toCategoryList(getAllCategoryCode(categoryDTO));
            if (!categoryList.isEmpty()){
                log.info("Category code is duplicated !");

            }
            category = categoryConverter.toCategory(categoryDTO);
            log.info("Insert category is completed !");
        }
        return categoryConverter.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(CategoryDTO categoryDTO) {
        boolean exists = categoryRepository.existsById(categoryDTO.getId());
        if (!exists){
            log.info("Cannot delete id: "+categoryDTO.getId());
            throw new ObjectNotFoundException(
                    404, "Cannot delete id: "+categoryDTO.getId()
            );
        }
        this.categoryRepository.deleteById(categoryDTO.getId());
        log.info("Delete category is completed !");
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exitsCategory(CategoryDTO categoryDTO) {
        return categoryRepository.existsById(categoryDTO.getId());
    }

    @Override
    public String uploadCategoryImages(MultipartFile fileName) {
       return null;
    }

    @Override
    public String uploadLocalCategoryImages(MultipartFile fileName) {
        return null;
    }

    @Override
    public byte[] readImageUrl(String fileName) {
        String folderName = "category";
        return uploadLocalUtil.readFileContent(fileName, folderName);
    }
}
