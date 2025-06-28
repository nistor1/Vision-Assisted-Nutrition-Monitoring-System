package org.nutrition.app.food.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.food.dto.FoodItemDTO;
import org.nutrition.app.food.dto.FoodItemSimpleDTO;
import org.nutrition.app.food.dto.request.create.CreateFoodItemRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionCarbohydratesRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionMineralsRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionProximatesRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionVitaminsRequest;
import org.nutrition.app.food.dto.request.update.UpdateFoodItemRequest;
import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.entity.FoodItemSimpleProjection;
import org.nutrition.app.food.entity.NutritionCarbohydrates;
import org.nutrition.app.food.entity.NutritionMinerals;
import org.nutrition.app.food.entity.NutritionProximates;
import org.nutrition.app.food.entity.NutritionVitamins;
import org.nutrition.app.food.repository.FoodItemRepository;
import org.nutrition.app.util.Constants.Time;
import org.nutrition.app.util.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public Optional<Page<FoodItemDTO>> findAll(String search, Pageable pageable) {
        Page<FoodItem> foodItems;

        if (search != null && !search.trim().isEmpty()) {
            foodItems = foodItemRepository.findByCategory(search, pageable);
        } else {
            foodItems = foodItemRepository.findAll(pageable);
        }

        return Optional.of(foodItems.map(this::mapFoodItemToDTO));
    }

    public Optional<Page<FoodItemSimpleDTO>> findAllSimple(String search, Pageable pageable) {
        Page<FoodItemSimpleProjection> foodItems;

        if (search != null && !search.trim().isEmpty()) {
            foodItems = foodItemRepository.findSimpleByCategory(search, pageable);
        } else {
            foodItems = foodItemRepository.findAllSimple(pageable);
        }

        return Optional.of(foodItems.map(this::mapFoodItemToSimpleDTO));
    }

    public Optional<FoodItemDTO> findById(final UUID id) {
        return foodItemRepository.findById(id).map(this::mapFoodItemToDTO);
    }

    public Optional<FoodItemSimpleDTO> findSimpleById(final UUID id) {
        return foodItemRepository.findSimpleById(id).map(this::mapFoodItemToSimpleDTO);
    }

    public Optional<FoodItemDTO> findByTag(final Integer tag) {
        return foodItemRepository.findByTag(tag).map(this::mapFoodItemToDTO);
    }

    public Optional<FoodItemDTO> create(final CreateFoodItemRequest request) {
        var foodItem = FoodItem.builder()
                .withTag(request.getTag())
                .withFoodName(request.getFoodName())
                .withCategory(request.getCategory())
                .withImageUrl(request.getImageUrl())
                .withServingSize(request.getServingSize())
                .withServingUnit(request.getServingUnit())
                .withActive(Boolean.TRUE.equals(request.getActive()))
                .withCreatedAt(Time.now())
                .withUpdatedAt(Time.now())
                .build();

        // Map and set nutrition entities
        foodItem.setProximates(mapToProximatesEntity(request.getProximates()));
        foodItem.setMinerals(mapToMineralsEntity(request.getMinerals()));
        foodItem.setCarbohydrates(mapToCarbohydratesEntity(request.getCarbohydrates()));
        foodItem.setVitamins(mapToVitaminsEntity(request.getVitamins()));

        var saved = foodItemRepository.save(foodItem);
        return Optional.of(mapFoodItemToDTO(saved));
    }

    public Optional<FoodItemDTO> update(final UpdateFoodItemRequest request) {
        return foodItemRepository.findById(request.getId())
                .map(foodItem -> {
                    Mapper.updateValues(foodItem, request);
                    foodItem.setUpdatedAt(Time.now());

                    foodItemRepository.save(foodItem);

                    return mapFoodItemToDTO(foodItem);
                });
    }

    @Transactional
    public Optional<FoodItemDTO> deleteById(final UUID id) {
        return foodItemRepository.findById(id)
                .map(foodItem -> {
                    foodItemRepository.delete(foodItem);
                    return mapFoodItemToDTO(foodItem);
                });
    }

    public FoodItemDTO mapFoodItemToDTO(final FoodItem foodItem) {
        return Mapper.mapTo(foodItem, FoodItemDTO.class);
    }

    public FoodItemSimpleDTO mapFoodItemToSimpleDTO(final FoodItemSimpleProjection foodItem) {
        return Mapper.mapTo(foodItem, FoodItemSimpleDTO.class);
    }

    private NutritionProximates mapToProximatesEntity(CreateNutritionProximatesRequest dto) {
        return NutritionProximates.builder()
                .withPortionSize(dto.getPortionSize())
                .withUnit(dto.getUnit())
                .withWater(dto.getWater())
                .withEnergyGeneral(dto.getEnergyGeneral())
                .withEnergySpecific(dto.getEnergySpecific())
                .withNitrogen(dto.getNitrogen())
                .withProtein(dto.getProtein())
                .withTotalLipid(dto.getTotalLipid())
                .withAsh(dto.getAsh())
                .build();
    }

    private NutritionMinerals mapToMineralsEntity(CreateNutritionMineralsRequest dto) {
        return NutritionMinerals.builder()
                .withPortionSize(dto.getPortionSize())
                .withUnit(dto.getUnit())
                .withCalcium(dto.getCalcium())
                .withIron(dto.getIron())
                .withMagnesium(dto.getMagnesium())
                .withPhosphorus(dto.getPhosphorus())
                .withPotassium(dto.getPotassium())
                .withSodium(dto.getSodium())
                .withZinc(dto.getZinc())
                .withCopper(dto.getCopper())
                .withManganese(dto.getManganese())
                .build();
    }

    private NutritionCarbohydrates mapToCarbohydratesEntity(CreateNutritionCarbohydratesRequest dto) {
        return NutritionCarbohydrates.builder()
                .withPortionSize(dto.getPortionSize())
                .withUnit(dto.getUnit())
                .withCarbohydrate(dto.getCarbohydrate())
                .withFiber(dto.getFiber())
                .withTotalSugars(dto.getTotalSugars())
                .withSucrose(dto.getSucrose())
                .withGlucose(dto.getGlucose())
                .withFructose(dto.getFructose())
                .withMaltose(dto.getMaltose())
                .withLactose(dto.getLactose())
                .build();
    }


    private NutritionVitamins mapToVitaminsEntity(CreateNutritionVitaminsRequest dto) {
        return NutritionVitamins.builder()
                .withPortionSize(dto.getPortionSize())
                .withUnit(dto.getUnit())
                .withVitaminA(dto.getVitaminA())
                .withVitaminB1(dto.getVitaminB1())
                .withVitaminB2(dto.getVitaminB2())
                .withVitaminB3(dto.getVitaminB3())
                .withVitaminB5(dto.getVitaminB5())
                .withVitaminB6(dto.getVitaminB6())
                .withVitaminB7(dto.getVitaminB7())
                .withVitaminB9(dto.getVitaminB9())
                .withVitaminB12(dto.getVitaminB12())
                .withVitaminC(dto.getVitaminC())
                .withVitaminD(dto.getVitaminD())
                .withVitaminE(dto.getVitaminE())
                .withVitaminK(dto.getVitaminK())
                .build();
    }

}