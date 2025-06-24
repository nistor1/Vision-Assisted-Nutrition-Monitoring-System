-- Apple

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000000', 100.0, 'g', 84.7, 62.0, 56.0,
             0.03, 0.19, 0.21, 0.15
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000001', 100.0, 'mg', 5.0, 0.1, 4.7, 9.0,
             95.0, 1.0, 0.02, 0.024, 0.029
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000002', 100.0, 'g', 14.8, 2.0, 12.2, 1.32,
             3.09, 7.81, 0.15, 0.15
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000003', 100.0, 'mg', NULL, 0.009, 0.066,
             0.09, NULL, 0.021, NULL,
             6.0, NULL, NULL, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000004', 'Apple', 'Fruit',
             'https://example.com/images/apple.jpg', 100.0, 'g',
             '2025-06-18T14:00:00.000+00:00', '2025-06-18T14:00:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000000', '1da71bb6-6737-42ec-99a7-010965000001',
             '1da71bb6-6737-42ec-99a7-010965000002', '1da71bb6-6737-42ec-99a7-010965000003'
         );

-- Avocado

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000005', 100.0, 'g', 67.0, 223.0, 206.0,
             0.29, 1.81, 20.3, 2.55
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000006', 100.0, 'mg', 14.0, 0.61, 32.8, 42.0,
             576.0, 2.49, 0.46, 0.285, 0.197
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000007', 100.0, 'g', 8.32, NULL, NULL, NULL,
            NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000008', 100.0, 'mg', NULL, NULL, NULL,
            NULL, NULL, 0.167, NULL,
             4.0, NULL, NULL, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000009', 'Avocado', 'Fruit',
             'https://example.com/images/avocado.jpg', 100.0, 'g',
             '2025-06-18T14:15:00.000+00:00', '2025-06-18T14:15:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000005', '1da71bb6-6737-42ec-99a7-010965000006',
             '1da71bb6-6737-42ec-99a7-010965000007', '1da71bb6-6737-42ec-99a7-010965000008'
         );

-- Banana

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000010', 100.0, 'g', 78.3, 85.0, 77.0,
             0.12, 0.73, 0.22, 0.67
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000011', 100.0, 'mg', NULL, NULL, NULL, NULL,
            NULL, NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000012', 100.0, 'g', 20.1, 2.02, 15.8, 2.1,
             7.0, 6.7, 0.25, 0.25
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000013', 100.0, 'mg', 0.001, 0.04, 0.1,
             0.57, NULL, 0.234, NULL,
            NULL, NULL, 9.7, NULL,
            NULL, 0.0002
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000014', 'Banana', 'Fruit',
             'https://example.com/images/banana.jpg', 100.0, 'g',
             '2025-06-18T14:30:00.000+00:00', '2025-06-18T14:30:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000010', '1da71bb6-6737-42ec-99a7-010965000011',
             '1da71bb6-6737-42ec-99a7-010965000012', '1da71bb6-6737-42ec-99a7-010965000013'
         );

-- Bell Pepper

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000020', 100.0, 'g', 91.9, 31.0, 27.0,
             0.14, 0.9, 0.13, 0.4
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000021', 100.0, 'mg', 6.0, 0.35, 11.0, 27.0,
             213.0, 2.5, 0.2, 0.04, 0.133
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000022', 100.0, 'g', 6.65, 1.2, NULL, NULL,
            NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000023', 100.0, 'mg', NULL, 0.055, 0.142,
             1.02, NULL, 0.303, 0.0004,
            NULL, NULL, 142.0, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000024', 'Bell Pepper', 'Vegetable',
             'https://example.com/images/bell-pepper.jpg', 100.0, 'g',
             '2025-06-18T14:35:00.000+00:00', '2025-06-18T14:35:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000020', '1da71bb6-6737-42ec-99a7-010965000021',
             '1da71bb6-6737-42ec-99a7-010965000022', '1da71bb6-6737-42ec-99a7-010965000023'
         );

-- Beets, raw

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000025', 100.0, 'g', 88.2, 45.0, 41.0,
             0.27, 1.69, 0.3, 1.06
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000026', 100.0, 'mg', 14.0, 0.43, 21.0, 34.0,
             342.0, 112.0, 0.31, 0.161, 0.257
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000027', 100.0, 'g', 8.79, 3.1, 5.1, 4.29,
             0.3, 0.51, 0.25, 0.25
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000028', 100.0, 'mg', NULL, NULL, NULL,
            NULL, NULL, NULL, NULL,
            NULL, NULL, 4.6, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000029', 'Beets', 'Vegetable',
             'https://example.com/images/beets.jpg', 100.0, 'g',
             '2025-06-18T14:40:00.000+00:00', '2025-06-18T14:40:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000025', '1da71bb6-6737-42ec-99a7-010965000026',
             '1da71bb6-6737-42ec-99a7-010965000027', '1da71bb6-6737-42ec-99a7-010965000028'
         );

-- Bread

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000030', 100.0, 'g', 35.7, 267.0, 270.0,
             1.51, 9.43, 3.59, 2.1
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000031', 100.0, 'mg', 211.0, 3.36, 26.9, 113.0,
             117.0, 477.0, 0.88, 0.124, 0.632
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000032', 100.0, 'g', 49.2, 2.3, 5.34, NULL,
             1.44, 2.28, 1.56, 0.06
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000033', 100.0, 'mg', NULL, 0.507, 0.24,
             4.76, 0.548, 0.092, NULL,
            NULL, NULL, NULL, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000034', 'Bread', 'Baked Products',
             'https://example.com/images/bread.jpg', 100.0, 'g',
             '2025-06-18T15:00:00.000+00:00', '2025-06-18T15:00:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000030', '1da71bb6-6737-42ec-99a7-010965000031',
             '1da71bb6-6737-42ec-99a7-010965000032', '1da71bb6-6737-42ec-99a7-010965000033'
         );

-- Broccoli

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000035', 100.0, 'g', 90.0, 39.0, 32.0,
             0.41, 2.57, 0.34, 0.83
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000036', 100.0, 'mg', 46.0, 0.69, 21.0, 67.0,
             303.0, 36.0, 0.42, 0.059, 0.197
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000037', 100.0, 'g', 6.27, 2.4, 1.4, 0.01,
             0.58, 0.82, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000038', 100.0, 'mg', 0.008, 0.077, 0.114,
             0.639, 0.61, 0.191, NULL,
             0.065, NULL, 91.3, NULL,
             0.15, 0.0001
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000039', 'Broccoli', 'Vegetable',
             'https://example.com/images/broccoli.jpg', 100.0, 'g',
             '2025-06-18T15:15:00.000+00:00', '2025-06-18T15:15:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000035', '1da71bb6-6737-42ec-99a7-010965000036',
             '1da71bb6-6737-42ec-99a7-010965000037', '1da71bb6-6737-42ec-99a7-010965000038'
         );

-- Cabbage

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-01096500003a', 100.0, 'g', 91.9, 31.0, 28.0,
             0.15, 0.96, 0.23, 0.56
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-01096500003b', 100.0, 'mg', 42.0, 0.07, 13.9, 27.0,
             207.0, 16.0, 0.21, 0.025, 0.248
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-01096500003c', 100.0, 'g', 6.38, NULL, NULL, NULL,
            NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-01096500003d', 100.0, 'mg', NULL, NULL, NULL,
            NULL, NULL, 0.138, NULL,
            NULL, NULL, 40.3, NULL,
            NULL, 0.0006
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-01096500003e', 'Cabbage', 'Vegetable',
             'https://example.com/images/cabbage.jpg', 100.0, 'g',
             '2025-06-18T15:30:00.000+00:00', '2025-06-18T15:30:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-01096500003a', '1da71bb6-6737-42ec-99a7-01096500003b',
             '1da71bb6-6737-42ec-99a7-01096500003c', '1da71bb6-6737-42ec-99a7-01096500003d'
         );

-- Carrot

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-01096500003f', 100.0, 'g', 87.7, 48.0, 45.0,
             0.15, 0.94, 0.35, 0.72
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000040', 100.0, 'mg', 30.0, 0.15, 12.4, 40.0,
             280.0, 87.0, 0.24, 0.061, 0.13
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000041', 100.0, 'g', 10.3, 3.1, NULL, NULL,
            NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000042', 100.0, 'mg', NULL, 0.065, 0.095,
             1.41, NULL, 0.146, 0.002,
            NULL, NULL, NULL, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000043', 'Carrot', 'Vegetable',
             'https://example.com/images/carrot.jpg', 100.0, 'g',
             '2025-06-18T15:35:00.000+00:00', '2025-06-18T15:35:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-01096500003f', '1da71bb6-6737-42ec-99a7-010965000040',
             '1da71bb6-6737-42ec-99a7-010965000041', '1da71bb6-6737-42ec-99a7-010965000042'
         );

-- Cauliflower

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000044', 100.0, 'g', 92.7, 28.0, 23.0,
             0.26, 1.64, 0.24, 0.68
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000045', 100.0, 'mg', 20.0, 0.33, 14.2, 40.0,
             274.0, 20.0, 0.23, 0.024, 0.141
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000046', 100.0, 'g', 4.72, 1.9, NULL, NULL,
            NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000047', 100.0, 'mg', NULL, NULL, NULL,
            NULL, NULL, NULL, NULL,
             0.097, NULL, 67.1, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000048', 'Cauliflower', 'Vegetable',
             'https://example.com/images/cauliflower.jpg', 100.0, 'g',
             '2025-06-18T15:45:00.000+00:00', '2025-06-18T15:45:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000044', '1da71bb6-6737-42ec-99a7-010965000045',
             '1da71bb6-6737-42ec-99a7-010965000046', '1da71bb6-6737-42ec-99a7-010965000047'
         );

-- Cucumber

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000049', 100.0, 'g', 95.9, 16.0, 14.0,
             0.1, 0.62, 0.18, 0.38
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000050', 100.0, 'mg', 16.0, NULL, 10.1, 23.0,
             170.0, 2.0, 0.2, 0.063, 0.085
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000051', 100.0, 'g', 2.95, NULL, NULL, NULL,
            NULL, NULL, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000052', 100.0, 'mg', NULL, NULL, NULL,
            NULL, NULL, NULL, 0.000962,
            NULL, NULL, NULL, NULL,
            NULL, 0.024
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000053', 'Cucumber', 'Vegetable',
             'https://example.com/images/cucumber.jpg', 100.0, 'g',
             '2025-06-18T15:50:00.000+00:00', '2025-06-18T15:50:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000049', '1da71bb6-6737-42ec-99a7-010965000050',
             '1da71bb6-6737-42ec-99a7-010965000051', '1da71bb6-6737-42ec-99a7-010965000052'
         );

-- Eggplant

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000054', 100.0, 'g', 93.1, 26.0, 22.0,
             0.14, 0.85, 0.12, 0.52
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit, calcium, iron, magnesium, phosphorus,
    potassium, sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000055', 100.0, 'mg', 11.0, NULL, 13.5, 23.0,
             222.0, NULL, 0.12, 0.061, 0.106
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose,
    glucose, fructose, maltose, lactose
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000056', 100.0, 'g', 5.4, 2.4, 2.35, 0.25,
             1.19, 1.16, 0.25, 0.25
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_a, vitamin_b1, vitamin_b2,
    vitamin_b3, vitamin_b5, vitamin_b6, vitamin_b7,
    vitamin_b9, vitamin_b12, vitamin_c, vitamin_d,
    vitamin_e, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000057', 100.0, 'mg', NULL, NULL, NULL,
            NULL, NULL, NULL, NULL,
             0.02, NULL, 0.8, NULL,
            NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000058', 'Eggplant', 'Vegetable',
             'https://example.com/images/eggplant.jpg', 100.0, 'g',
             '2025-06-18T15:55:00.000+00:00', '2025-06-18T15:55:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000054', '1da71bb6-6737-42ec-99a7-010965000055',
             '1da71bb6-6737-42ec-99a7-010965000056', '1da71bb6-6737-42ec-99a7-010965000057'
         );

-- Garlic

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000059', 100.0, 'g', 63.1, 143.0, 130.0,
             1.06, 6.62, 0.38, 1.71
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000060', 100.0, 'mg'
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000061', 100.0, 'g', 28.2, 2.7, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit, vitamin_c
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000062', 100.0, 'mg', 10.0
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000063', 'Garlic', 'Vegetable',
             'https://example.com/images/garlic.jpg', 100.0, 'g',
             '2025-06-18T15:58:00.000+00:00', '2025-06-18T15:58:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000059', '1da71bb6-6737-42ec-99a7-010965000060',
             '1da71bb6-6737-42ec-99a7-010965000061', '1da71bb6-6737-42ec-99a7-010965000062'
         );

-- Lettuce

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000064', 100.0, 'g', 94.3, 21.0, 17.0,
             0.16, 0.98, 0.07, 0.61
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000065', 100.0, 'mg',
             28.0, 0.27, 12.0, 23.0, 260.0,
             23.0, 0.25, 0.049, 0.227
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000066', 100.0, 'g', 4.06, NULL, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_b1, vitamin_b3, vitamin_b6, vitamin_k
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000067', 100.0, 'mg',
             0.063, 0.371, 0.063, 0.0834
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000068', 'Lettuce', 'Vegetable',
             'https://example.com/images/lettuce.jpg', 100.0, 'g',
             '2025-06-18T16:03:00.000+00:00', '2025-06-18T16:03:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000064', '1da71bb6-6737-42ec-99a7-010965000065',
             '1da71bb6-6737-42ec-99a7-010965000066', '1da71bb6-6737-42ec-99a7-010965000067'
         );

-- Mushroom

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000069', 100.0, 'g', 91.8, 30.0, 24.0,
             0.5, 3.09, 0.2, 0.88
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000070', 100.0, 'mg',
             4.0, 0.31, 10.2, 100.0, 380.0,
             5.0, 0.51, 0.321, 0.061
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000071', 100.0, 'g', 4.01, 1.8, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_b1, vitamin_b2, vitamin_b3, vitamin_b6, vitamin_b7
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000072', 100.0, 'mg',
             0.004, 0.517, 4.17, 0.112, 0.001
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '1da71bb6-6737-42ec-99a7-010965000073', 'Mushroom', 'Vegetable',
             'https://example.com/images/mushroom.jpg', 100.0, 'g',
             '2025-06-18T16:06:00.000+00:00', '2025-06-18T16:06:00.000+00:00', TRUE,
             '1da71bb6-6737-42ec-99a7-010965000069', '1da71bb6-6737-42ec-99a7-010965000070',
             '1da71bb6-6737-42ec-99a7-010965000071', '1da71bb6-6737-42ec-99a7-010965000072'
         );

-- Onion

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000079', 100.0, 'g', 91.3, 36.0, 33.0,
             0.14, 0.89, 0.13, 0.4
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000080', 100.0, 'mg',
             21.0, 0.15, 9.3, 29.0, 141.0,
             2.0, 0.12, 0.047, 0.099
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose, glucose, fructose, maltose, lactose
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000081', 100.0, 'g', 7.68, 1.2, 5.76, 0.59, 2.63, 2.52, 0.25, 0.25
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000082', 100.0, 'mg',
            NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000083', 'Onion', 'Vegetable',
             'https://example.com/images/onion.jpg', 100.0, 'g',
             '2025-06-18T16:15:00.000+00:00', '2025-06-18T16:15:00.000+00:00', TRUE,
             '9fa297df-ea06-40a5-bba6-010965000079', '9fa297df-ea06-40a5-bba6-010965000080',
             '9fa297df-ea06-40a5-bba6-010965000081', '9fa297df-ea06-40a5-bba6-010965000082'
         );

-- Orange

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000084', 100.0, 'g', 86.7, 52.0, 47.0,
             0.14, 0.91, 0.15, 0.43
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000085', 100.0, 'mg',
             43.0, 0.33, 10.7, 23.0, 166.0,
             9.0, 0.11, 0.064, 0.029
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose, glucose, fructose
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000086', 100.0, 'g', 11.8, 2.0, 8.57, 4.19, 2.02, 2.36
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000087', 100.0, 'mg',
             59.1, 0.068, 0.051, 0.425,
             0.261, 0.079, 0.025, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             '9fa297df-ea06-40a5-bba6-010965000088', 'Orange', 'Fruit',
             'https://example.com/images/orange.jpg', 100.0, 'g',
             '2025-06-18T16:20:00.000+00:00', '2025-06-18T16:20:00.000+00:00', TRUE,
             '9fa297df-ea06-40a5-bba6-010965000084', '9fa297df-ea06-40a5-bba6-010965000085',
             '9fa297df-ea06-40a5-bba6-010965000086', '9fa297df-ea06-40a5-bba6-010965000087'
         );

-- Pear

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000084', 100.0, 'g', 84.1, 63.0, 57.0,
             0.06, 0.38, 0.16, 0.3
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000085', 100.0, 'mg',
             8.0, 0.17, 5.7, 10.0, 87.0,
             7.0, 0.07, 0.07, 0.032
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose, glucose, fructose
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000086', 100.0, 'g', 15.1, 3.1, 9.69, 0.43, 2.5, 6.76
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k, vitamin_a
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000087', 100.0, 'mg',
             4.4, 0.012, 0.026, 0.164,
             0.042, 0.026, 6.0, 0.004, 0.001
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000088', 'Pear', 'Fruit',
             'https://example.com/images/pear.jpg', 100.0, 'g',
             '2025-06-18T17:00:00.000+00:00', '2025-06-18T17:00:00.000+00:00', TRUE,
             'ea2dfb7e-e5f0-4e90-a0aa-010965000084', 'ea2dfb7e-e5f0-4e90-a0aa-010965000085',
             'ea2dfb7e-e5f0-4e90-a0aa-010965000086', 'ea2dfb7e-e5f0-4e90-a0aa-010965000087'
         );

-- Pineapple

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000089', 100.0, 'g', 85.0, 60.0, 54.0,
             0.07, 0.46, 0.21, 0.25
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000090', 100.0, 'mg',
             12.0, 0.05, 13.4, 5.0, 137.0,
             2.0, 0.11, 0.095, 0.866
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars, sucrose, glucose, fructose
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000091', 100.0, 'g', 14.1, 0.9, 11.4, 3.46, 3.91, 4.05
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000092', 100.0, 'mg',
             58.6, 0.064, NULL, 0.228,
             NULL, 0.11, NULL, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000093', 'Pineapple', 'Fruit',
             'https://example.com/images/pineapple.jpg', 100.0, 'g',
             '2025-06-18T17:00:00.000+00:00', '2025-06-18T17:00:00.000+00:00', TRUE,
             'ea2dfb7e-e5f0-4e90-a0aa-010965000089', 'ea2dfb7e-e5f0-4e90-a0aa-010965000090',
             'ea2dfb7e-e5f0-4e90-a0aa-010965000091', 'ea2dfb7e-e5f0-4e90-a0aa-010965000092'
         );

-- Egg

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000094', 100.0, 'g', 75.8, 143.0, 147.0,
             1.99, 12.4, 9.96, 0.85
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000095', 100.0, 'mg',
             48.0, 1.67, 11.4, 184.0, 132.0,
             129.0, 1.24, 0.1, 0.05
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000096', 100.0, 'g', 0.96, 0.75, 0.2
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k, vitamin_d, vitamin_a, vitamin_b12
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000097', 100.0, 'mg',
             NULL, 0.077, 0.419, 0.2,
             NULL, 0.063, 0.071, NULL,
             0.003, 0.2, 0.001
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000098', 'Egg', 'Protein',
             'https://example.com/images/egg.jpg', 100.0, 'g',
             '2025-06-18T17:00:00.000+00:00', '2025-06-18T17:00:00.000+00:00', TRUE,
             'ea2dfb7e-e5f0-4e90-a0aa-010965000094', 'ea2dfb7e-e5f0-4e90-a0aa-010965000095',
             'ea2dfb7e-e5f0-4e90-a0aa-010965000096', 'ea2dfb7e-e5f0-4e90-a0aa-010965000097'
         );


-- Potato

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000099', 100.0, 'g', 81.1, 73.0, 72.0,
             0.29, 1.81, 0.26, 0.89
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000100', 100.0, 'mg',
             6.0, 0.37, 22.3, 57.0, 446.0,
             2.0, 0.37, 0.13, 0.16
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000101', 100.0, 'g', 16.0, 13.8, 0.65
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000102', 100.0, 'mg',
             23.3, 0.051, NULL, 1.58,
             NULL, 0.145, NULL, 0.0008
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000103', 'Potato', 'Vegetable',
             'https://example.com/images/potato.jpg', 100.0, 'g',
             '2025-06-18T17:00:00.000+00:00', '2025-06-18T17:00:00.000+00:00', TRUE,
             'ea2dfb7e-e5f0-4e90-a0aa-010965000099', 'ea2dfb7e-e5f0-4e90-a0aa-010965000100',
             'ea2dfb7e-e5f0-4e90-a0aa-010965000101', 'ea2dfb7e-e5f0-4e90-a0aa-010965000102'
         );

-- Strawberry

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000104', 100.0, 'g', 90.8, 36.0, 33.0,
             0.10, 0.64, 0.22, 0.34
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000105', 100.0, 'mg',
             17.0, 0.26, 12.5, 23.0, 161.0,
             2.0, 0.11, 0.035, 0.368
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber,total_sugars, sucrose, fructose, glucose
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000106', 100.0, 'g', 7.96, NULL, 4.86, 0.47, 2.44, 2.01
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_e, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000107', 100.0, 'mg',
             0.29, NULL, NULL, NULL,
             NULL, NULL, NULL, 0.2
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'ea2dfb7e-e5f0-4e90-a0aa-010965000108', 'Strawberry', 'Fruit',
             'https://example.com/images/strawberry.jpg', 100.0, 'g',
             '2025-06-18T17:00:00.000+00:00', '2025-06-18T17:00:00.000+00:00', TRUE,
             'ea2dfb7e-e5f0-4e90-a0aa-010965000104', 'ea2dfb7e-e5f0-4e90-a0aa-010965000105',
             'ea2dfb7e-e5f0-4e90-a0aa-010965000106', 'ea2dfb7e-e5f0-4e90-a0aa-010965000107'
         );


-- Tomato

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000109', 100.0, 'g', 94.7, 22.0, 19.0,
             0.11, 0.7, 0.42, 0.31
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000110', 100.0, 'mg',
             10.0, 0.1, 8.1, 19.0, 193.0,
             2.0, 0.08, 0.032, 0.087
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000111', 100.0, 'g', 3.84, 1.0, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k, vitamin_b7, vitamin_a
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000112', 100.0, 'mg',
             17.8, 0.056, NULL, 0.533,
             NULL, 0.079, 0.01, NULL, 0.0004, 0.03
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000113', 'Tomato', 'Vegetable',
             'https://example.com/images/tomato.jpg', 100.0, 'g',
             '2025-06-18T17:10:00.000+00:00', '2025-06-18T17:10:00.000+00:00', TRUE,
             'fa2b9e55-d0c3-4e2b-8d02-010965000109', 'fa2b9e55-d0c3-4e2b-8d02-010965000110',
             'fa2b9e55-d0c3-4e2b-8d02-010965000111', 'fa2b9e55-d0c3-4e2b-8d02-010965000112'
         );

-- Zucchini

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000114', 100.0, 'g', 95.0, 19.0, 16.0,
             0.16, 0.98, 0.2, 0.54
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000115', 100.0, 'mg',
             21.0, 0.19, 15.3, 33.0, 226.0,
             2.5, 0.26, 0.064, 0.149
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000116', 100.0, 'g', 3.27, 0.8, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000117', 100.0, 'mg',
             15.0, NULL, NULL, NULL,
             NULL, NULL, 0.07, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000118', 'Zucchini', 'Vegetable',
             'https://example.com/images/zucchini.jpg', 100.0, 'g',
             '2025-06-18T17:15:00.000+00:00', '2025-06-18T17:15:00.000+00:00', TRUE,
             'fa2b9e55-d0c3-4e2b-8d02-010965000114', 'fa2b9e55-d0c3-4e2b-8d02-010965000115',
             'fa2b9e55-d0c3-4e2b-8d02-010965000116', 'fa2b9e55-d0c3-4e2b-8d02-010965000117'
         );

-- Lemon

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000119', 100.0, 'g', 88.98, 29.0, NULL,
             NULL, 1.1, 0.3, NULL
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000120', 100.0, 'mg',
             26.0, 0.6, 8.0, 16.0, 138.0,
             2.0, 0.06, 0.037, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000121', 100.0, 'g', 9.32, 2.8, 2.5
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_e
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000122', 100.0, 'mg',
             53.0, 0.04, 0.02, 0.1,
             NULL, 0.08, 0.01, 0.15
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'fa2b9e55-d0c3-4e2b-8d02-010965000123', 'Lemon', 'Fruit',
             'https://example.com/images/lemon.jpg', 100.0, 'g',
             '2025-06-18T17:20:00.000+00:00', '2025-06-18T17:20:00.000+00:00', TRUE,
             'fa2b9e55-d0c3-4e2b-8d02-010965000119', 'fa2b9e55-d0c3-4e2b-8d02-010965000120',
             'fa2b9e55-d0c3-4e2b-8d02-010965000121', 'fa2b9e55-d0c3-4e2b-8d02-010965000122'
         );

-- Ginger

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'fb3c2f66-e1d4-5f3c-9e13-121076001130', 100.0, 'g', 92.3, 20.0, NULL,
             NULL, 0.33, 0.1, NULL
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'fb3c2f66-e1d4-5f3c-9e13-121076001131', 100.0, 'mg',
             74.0, 0.28, 4.0, 2.0, 36.0,
             906.0, 0.04, 0.018, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'fb3c2f66-e1d4-5f3c-9e13-121076001132', 100.0, 'g', 4.83, 2.6, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b6, vitamin_b9, vitamin_k, vitamin_e
) VALUES (
             'fb3c2f66-e1d4-5f3c-9e13-121076001133', 100.0, 'mg',
            NULL, 0.02, 0.015, 0.022,
             0.037, 0.001, 0.002, 0.18
         );


INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id,
    nutrition_vitamins_id
) VALUES (
             'fb3c2f66-e1d4-5f3c-9e13-121076001135', 'Ginger', 'Root',
             'https://example.com/images/ginger.jpg', 100.0, 'g',
             '2025-06-18T17:45:00.000+00:00', '2025-06-18T17:45:00.000+00:00', TRUE,
             'fb3c2f66-e1d4-5f3c-9e13-121076001130', 'fb3c2f66-e1d4-5f3c-9e13-121076001131',
             'fb3c2f66-e1d4-5f3c-9e13-121076001132', 'fb3c2f66-e1d4-5f3c-9e13-121076001133'
         );


-- Watermelon

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0f7', 100.0, 'g', 91.45, 30.0, NULL,
             NULL, 0.61, 0.15, NULL
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0f8', 100.0, 'mg',
             7.0, 0.24, 10.0, 11.0, 112.0,
             1.0, 0.1, 0.042, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0f9', 100.0, 'g', 7.55, 0.4, 6.2
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b6, vitamin_b9, vitamin_k, vitamin_e,
    vitamin_a
) VALUES (
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0fa', 100.0, 'mg',
             8.1, 0.033, 0.021, 0.178,
             0.045, 0.003, 0.0001, 0.05,
             0.03
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id,
    nutrition_vitamins_id
) VALUES (
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0fc', 'Watermelon', 'Fruit',
             'https://example.com/images/watermelon.jpg', 100.0, 'g',
             '2025-06-18T17:50:00.000+00:00', '2025-06-18T17:50:00.000+00:00', TRUE,
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0f7', 'c5d42f99-9bb9-48a3-9f31-4cc573c4a0f8',
             'c5d42f99-9bb9-48a3-9f31-4cc573c4a0f9', 'c5d42f99-9bb9-48a3-9f31-4cc573c4a0fa'
         );

-- Guava

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a1', 100.0, 'g', 80.8, 68.0, NULL,
             NULL, 2.55, 0.95, NULL
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a2', 100.0, 'mg',
             18.0, 0.26, 22.0, 40.0, 417.0,
             2.0, 0.23, 0.23, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a3', 100.0, 'g', 14.32, 5.4, 8.92
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b6, vitamin_b9, vitamin_k, vitamin_e,
    vitamin_a
) VALUES (
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a4', 100.0, 'mg',
             228.3, 0.067, 0.04, 1.084,
             0.11, 0.05, 0.003, 0.73,
             0.03
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id,
    nutrition_vitamins_id
) VALUES (
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a6', 'Guava', 'Fruit',
             'https://example.com/images/guava.jpg', 100.0, 'g',
             '2025-06-18T18:10:00.000+00:00', '2025-06-18T18:10:00.000+00:00', TRUE,
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a1', 'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a2',
             'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a3', 'b2a7f1a3-4235-4e1a-8d9f-7a67c4d9a0a4'
         );

-- Dragon fruit

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a901', 100.0, 'g', 82.44, 68.0, NULL,
             NULL, 0.68, 0.21, NULL
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a902', 100.0, 'mg',
             13.0, 0.12, 14.0, 18.0, 206.0,
             1.0, 0.09, 0.061, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a903', 100.0, 'g', 16.24, 1.8, 12.0
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b6, vitamin_b9, vitamin_k, vitamin_e,
    vitamin_a
) VALUES (
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a904', 100.0, 'mg',
             19.8, 0.05, 0.026, 0.356,
             0.101, 0.01, 0.004, 0.18,
             0.005
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id,
    nutrition_vitamins_id
) VALUES (
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a906', 'Dragon fruit', 'Fruit',
             'https://example.com/images/dragonfruit.jpg', 100.0, 'g',
             '2025-06-18T18:10:00.000+00:00', '2025-06-18T18:10:00.000+00:00', TRUE,
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a901', 'e8d314af-25f6-4c7d-8f4e-33a2b3e4a902',
             'e8d314af-25f6-4c7d-8f4e-33a2b3e4a903', 'e8d314af-25f6-4c7d-8f4e-33a2b3e4a904'
         );

-- Sugar apple

INSERT INTO nutrition_proximates (
    id, portion_size, unit, water, energy_general, energy_specific,
    nitrogen, protein, total_lipid, ash
) VALUES (
             'a1b2c3d4-e5f6-7890-abcd-1234567890ab', 100.0, 'g', 73.2, 94.0, NULL,
             NULL, 2.06, 0.29, 0.78
         );

INSERT INTO nutrition_minerals (
    id, portion_size, unit,
    calcium, iron, magnesium, phosphorus, potassium,
    sodium, zinc, copper, manganese
) VALUES (
             'a1b2c3d4-e5f6-7890-abcd-1234567890ac', 100.0, 'mg',
             24.0, 0.6, 21.0, 32.0, 247.0,
             9.0, 0.1, 0.086, NULL
         );

INSERT INTO nutrition_carbohydrates (
    id, portion_size, unit, carbohydrate, fiber, total_sugars
) VALUES (
             'a1b2c3d4-e5f6-7890-abcd-1234567890ad', 100.0, 'g', 23.6, 4.4, NULL
         );

INSERT INTO nutrition_vitamins (
    id, portion_size, unit,
    vitamin_c, vitamin_b1, vitamin_b2, vitamin_b3,
    vitamin_b5, vitamin_b6, vitamin_b9, vitamin_k
) VALUES (
             'a1b2c3d4-e5f6-7890-abcd-1234567890ae', 100.0, 'mg',
             36.3, 0.11, 0.113, 0.883,
             0.226, 0.2, 0.014, NULL
         );

INSERT INTO food_item (
    id, food_name, category, image_url, serving_size, serving_unit,
    created_at, updated_at, active,
    nutrition_proximates_id, nutrition_minerals_id, nutrition_carbohydrates_id, nutrition_vitamins_id
) VALUES (
             'a1b2c3d4-e5f6-7890-abcd-1234567890af', 'Sugar apple', 'Fruit',
             'https://example.com/images/sugar_apple.jpg', 100.0, 'g',
             '2025-06-18T17:10:00.000+00:00', '2025-06-18T17:10:00.000+00:00', TRUE,
             'a1b2c3d4-e5f6-7890-abcd-1234567890ab', 'a1b2c3d4-e5f6-7890-abcd-1234567890ac',
             'a1b2c3d4-e5f6-7890-abcd-1234567890ad', 'a1b2c3d4-e5f6-7890-abcd-1234567890ae'
         );