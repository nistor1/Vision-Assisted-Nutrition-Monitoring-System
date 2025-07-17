import React, { useEffect, useState } from 'react';
import { Descriptions, Card, Spin, Alert, Button, Image, Divider } from 'antd';
import dayjs from 'dayjs';
import type { FoodItem } from '../../types/FoodEntities';
import { apiService } from '../../services/api';

interface FoodItemViewProps {
  foodItemId: string;
  onClose: () => void;
}

const FoodItemView: React.FC<FoodItemViewProps> = ({ foodItemId, onClose }) => {
  const [food, setFood] = useState<FoodItem | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchFood = async () => {
      setLoading(true);
      setError(null);
      try {
        const res = await apiService.getFoodItemById(foodItemId);
        setFood(res.body.payload ?? null);
      } catch (err) {
        console.error(err);
        setError('Failed to load food item details');
      } finally {
        setLoading(false);
      }
    };
    fetchFood();
  }, [foodItemId]);

  if (loading) return <Spin tip="Loading food item..." />;
  if (error) return <Alert message={error} type="error" showIcon />;
  if (!food) return <Alert message="Food item not found" type="warning" showIcon />;

  return (
    <Card
      title={`Food: ${food.foodName}`}
      extra={<Button onClick={onClose}>Close</Button>}
      style={{ maxWidth: 1000, margin: '0 auto' }}
    >
      <Image src={food.imageUrl} alt={food.foodName} width={150} height={150} style={{ marginBottom: 20 }} />

      <Descriptions column={2} bordered labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
        <Descriptions.Item label="Category">{food.category}</Descriptions.Item>
        <Descriptions.Item label="Serving Size">
          {food.servingSize !== null ? `${food.servingSize} ${food.servingUnit}` : 'N/A'}
        </Descriptions.Item>
        <Descriptions.Item label="Active">{food.active ? 'Yes' : 'No'}</Descriptions.Item>
        <Descriptions.Item label="Tag">{food.tag ?? 'N/A'}</Descriptions.Item>
        <Descriptions.Item label="Created At">{dayjs(food.createdAt).format('YYYY-MM-DD HH:mm')}</Descriptions.Item>
        <Descriptions.Item label="Updated At">{dayjs(food.updatedAt).format('YYYY-MM-DD HH:mm')}</Descriptions.Item>
      </Descriptions>

      <Divider >Proximates</Divider>
      <Descriptions column={2} bordered size="small" labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
        {Object.entries(food.proximates)
          .filter(([k, v]) => k !== 'id' && k !== 'unit' && k !== 'portionSize' && v != null)
          .map(([key, value]) => (
            <Descriptions.Item key={key} label={key}>{value} g</Descriptions.Item>
          ))}
      </Descriptions>

      <Divider>Carbohydrates</Divider>
      <Descriptions column={2} bordered size="small" labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
        {Object.entries(food.carbohydrates)
          .filter(([k, v]) => k !== 'id' && k !== 'unit' && k !== 'portionSize' && v != null)
          .map(([key, value]) => (
            <Descriptions.Item key={key} label={key}>{value} g</Descriptions.Item>
          ))}
      </Descriptions>

      <Divider>Minerals (mg)</Divider>
      <Descriptions column={2} bordered size="small" labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
        {Object.entries(food.minerals)
          .filter(([k, v]) => k !== 'id' && k !== 'unit' && k !== 'portionSize' && v != null)
          .map(([key, value]) => (
            <Descriptions.Item key={key} label={key}>{value} mg</Descriptions.Item>
          ))}
      </Descriptions>

      <Divider>Vitamins (mg)</Divider>
      <Descriptions column={2} bordered size="small" labelStyle={{ width: 120 }} contentStyle={{ width: 120 }}>
        {Object.entries(food.vitamins)
          .filter(([k, v]) => k !== 'id' && k !== 'unit' && k !== 'portionSize' && v != null)
          .map(([key, value]) => (
            <Descriptions.Item key={key} label={key}>{value} mg</Descriptions.Item>
          ))}
      </Descriptions>

    </Card>
  );
};

export default FoodItemView;
