package com.femow.order.service.dataaccess.restaurant.adapter;

import com.femow.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.femow.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import com.femow.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository;
import com.femow.order.service.domain.entity.Restaurant;
import com.femow.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantDataAccessMapper restaurantDataAccessMapper;
    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);

        Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository.findByRestaurantIdAndProductIdIn(
                restaurant.getId().getValue(), restaurantProducts);

        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
