package com.healthcare.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.healthcare.model.entity.Meal;
import com.healthcare.repository.MealRepository;
import com.healthcare.service.MealService;

import io.jsonwebtoken.lang.Collections;

/**
 * Meal service
 */
@Service
@Transactional
public class MealServiceImpl implements MealService {

	private static final String REDIS_KEY = Meal.class.getSimpleName();

	private MealRepository mealRepository;
	private RedisTemplate<String, Meal> redisTemplate;

	@Autowired
	public MealServiceImpl(MealRepository mealRepository, RedisTemplate<String, Meal> redisTemplate) {
		this.mealRepository = mealRepository;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Meal save(Meal meal) {
		Meal savedMeal = mealRepository.save(meal);
		redisTemplate.opsForHash().put(REDIS_KEY, savedMeal.getId(), savedMeal);

		return savedMeal;
	}

	@Override
	public Meal findById(Long id) {
		Object meal = redisTemplate.opsForHash().get(REDIS_KEY, id);
		if (meal != null) {
			return (Meal) meal;
		}

		return mealRepository.findOne(id);
	}

	@Override
	public Long deleteById(Long id) {
		mealRepository.delete(id);

		return redisTemplate.opsForHash().delete(REDIS_KEY, id);
	}
	
	/**
	 *  find all meals
	 * @return List<Meal>
	 */
	@Override
	public List<Meal> findAll() {
		Map<Object, Object> mealMap = redisTemplate.opsForHash().entries(REDIS_KEY);
		List<Meal> mealList = Collections.arrayToList(mealMap.values().toArray());
		if (mealMap.isEmpty())
			mealList = mealRepository.findAll();
		return mealList;
	}

}
