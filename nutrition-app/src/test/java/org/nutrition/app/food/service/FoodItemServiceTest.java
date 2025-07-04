package org.nutrition.app.food.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.entity.FoodItemSimpleProjection;
import org.nutrition.app.food.repository.FoodItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.nutrition.app.util.TestUtils.FoodUtils.createFoodItemRequest;
import static org.nutrition.app.util.TestUtils.FoodUtils.randomFoodItem;
import static org.nutrition.app.util.TestUtils.FoodUtils.updateFoodItemRequest;

@ExtendWith(MockitoExtension.class)
class FoodItemServiceTest {

    @Mock
    private FoodItemRepository foodItemRepository;

    @InjectMocks
    private FoodItemService foodItemService;

    @Test
    void testCreate_validRequest_shouldReturnCreatedDTO() {
        var foodItem = randomFoodItem();
        var request = createFoodItemRequest(foodItem);
        var saved = foodItem;
        saved.setId(UUID.randomUUID());

        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(saved);

        var result = foodItemService.create(request);

        verify(foodItemRepository).save(any(FoodItem.class));
        assertTrue(result.isPresent());
        assertThat(result.get().id(), equalTo(saved.getId()));
        assertThat(result.get().foodName(), equalTo(request.getFoodName()));
    }

    @Test
    void testFindById_foodItemExists_shouldReturnDTO() {
        var foodItem = randomFoodItem();
        when(foodItemRepository.findById(foodItem.getId())).thenReturn(Optional.of(foodItem));

        var result = foodItemService.findById(foodItem.getId());

        verify(foodItemRepository).findById(foodItem.getId());
        assertTrue(result.isPresent());
        assertThat(result.get().id(), equalTo(foodItem.getId()));
    }

    @Test
    void testFindById_foodItemNotFound_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(foodItemRepository.findById(id)).thenReturn(Optional.empty());

        var result = foodItemService.findById(id);

        verify(foodItemRepository).findById(id);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdate_existingItem_shouldReturnUpdatedDTO() {
        var foodItem = randomFoodItem();
        var request = updateFoodItemRequest(foodItem);

        when(foodItemRepository.findById(foodItem.getId())).thenReturn(Optional.of(foodItem));
        when(foodItemRepository.save(foodItem)).thenReturn(foodItem);

        var result = foodItemService.update(request);

        verify(foodItemRepository).findById(foodItem.getId());
        verify(foodItemRepository).save(foodItem);
        assertTrue(result.isPresent());
        assertThat(result.get().id(), equalTo(foodItem.getId()));
    }

    @Test
    void testUpdate_itemNotFound_shouldReturnEmpty() {
        var request = updateFoodItemRequest(randomFoodItem());
        when(foodItemRepository.findById(request.getId())).thenReturn(Optional.empty());

        var result = foodItemService.update(request);

        verify(foodItemRepository).findById(request.getId());
        verify(foodItemRepository, never()).save(any());
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteById_existingItem_shouldReturnDeletedDTO() {
        var foodItem = randomFoodItem();
        when(foodItemRepository.findById(foodItem.getId())).thenReturn(Optional.of(foodItem));

        var result = foodItemService.deleteById(foodItem.getId());

        verify(foodItemRepository).delete(foodItem);
        assertTrue(result.isPresent());
        assertThat(result.get().id(), equalTo(foodItem.getId()));
    }

    @Test
    void testDeleteById_notFound_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(foodItemRepository.findById(id)).thenReturn(Optional.empty());

        var result = foodItemService.deleteById(id);

        verify(foodItemRepository).findById(id);
        verify(foodItemRepository, never()).delete(any());
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll_withSearch_shouldReturnFilteredPage() {
        var foodItem = randomFoodItem();
        var pageable = Pageable.unpaged();
        var page = new PageImpl<>(List.of(foodItem));

        when(foodItemRepository.findByCategory("Fruits", pageable)).thenReturn(page);

        var result = foodItemService.findAll("Fruits", pageable);

        verify(foodItemRepository).findByCategory("Fruits", pageable);
        assertTrue(result.isPresent());
        assertThat(result.get().getContent().size(), equalTo(1));
    }

    @Test
    void testFindAll_withoutSearch_shouldReturnAllPage() {
        var foodItem = randomFoodItem();
        var pageable = Pageable.unpaged();
        var page = new PageImpl<>(List.of(foodItem));

        when(foodItemRepository.findAll(pageable)).thenReturn(page);

        var result = foodItemService.findAll(null, pageable);

        verify(foodItemRepository).findAll(pageable);
        assertTrue(result.isPresent());
        assertThat(result.get().getContent().size(), equalTo(1));
    }

    @Test
    void testFindAllSimple_withSearch_shouldReturnFilteredSimplePage() {
        var pageable = Pageable.unpaged();
        Page<FoodItemSimpleProjection> page = new PageImpl<>(List.of(new FakeFoodItemSimpleProjection()));

        when(foodItemRepository.findSimpleByCategory("Vegetables", pageable)).thenReturn(page);

        var result = foodItemService.findAllSimple("Vegetables", pageable);

        verify(foodItemRepository).findSimpleByCategory("Vegetables", pageable);
        assertTrue(result.isPresent());
        assertThat(result.get().getContent().size(), equalTo(1));
        assertThat(result.get().getContent().get(0).category(), equalTo("Vegetables"));
    }

    @Test
    void testFindSimpleById_shouldReturnDTO() {
        UUID id = UUID.randomUUID();
        FoodItemSimpleProjection projection = new FakeFoodItemSimpleProjection();

        when(foodItemRepository.findSimpleById(id)).thenReturn(Optional.of(projection));

        var result = foodItemService.findSimpleById(id);

        verify(foodItemRepository).findSimpleById(id);
        assertTrue(result.isPresent());
        assertThat(result.get().foodName(), equalTo("Broccoli"));
    }


    @Test
    void testFindByTag_shouldReturnDTO() {
        var foodItem = randomFoodItem();
        when(foodItemRepository.findByTag(foodItem.getTag())).thenReturn(Optional.of(foodItem));

        var result = foodItemService.findByTag(foodItem.getTag());

        verify(foodItemRepository).findByTag(foodItem.getTag());
        assertTrue(result.isPresent());
    }

    static class FakeFoodItemSimpleProjection implements FoodItemSimpleProjection {
        @Override
        public UUID getId() {
            return UUID.randomUUID();
        }

        @Override
        public String getFoodName() {
            return "Broccoli";
        }

        @Override
        public String getCategory() {
            return "Vegetables";
        }

        @Override
        public String getImageUrl() {
            return "";
        }

        @Override
        public Double getServingSize() {
            return 100.0;
        }

        @Override
        public String getServingUnit() {
            return "g";
        }

        @Override
        public boolean isActive() {
            return false;
        }


    }

}

