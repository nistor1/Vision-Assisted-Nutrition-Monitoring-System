
import React, { useEffect, useState } from 'react';
import { Descriptions, Card, Spin, Alert, Button, List, Avatar, Divider, Tag } from 'antd';
import dayjs from 'dayjs';
import type { Meal } from '../../types/MealEntities';
import { apiService } from '../../services/api';
import { useNavigate } from 'react-router-dom';

interface MealViewProps {
  mealId: string;
  onClose: () => void;
}

const MealView: React.FC<MealViewProps> = ({ mealId, onClose }) => {
  const [meal, setMeal] = useState<Meal | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMeal = async () => {
      setLoading(true);
      setError(null);
      try {
        const res = await apiService.getMealById(mealId);
        setMeal(res.body.payload ?? null);
      } catch (err) {
        console.error(err);
        setError('Failed to load meal details');
      } finally {
        setLoading(false);
      }
    };
    fetchMeal();
  }, [mealId]);

  if (loading) return <Spin tip="Loading meal..." />;
  if (error) return <Alert message={error} type="error" showIcon />;
  if (!meal) return <Alert message="Meal not found" type="warning" showIcon />;

  return (
    <Card
      title={`Meal: ${meal.name}`}
      extra={<Button onClick={onClose}>Close</Button>}
      style={{ maxWidth: 1000, margin: '0 auto' }}
    >
      <Descriptions column={2} bordered>
        <Descriptions.Item label="Type">
          <Tag color="blue">{meal.mealType}</Tag>
        </Descriptions.Item>
        <Descriptions.Item label="Status">
          <Tag color={meal.mealStatus === 'FINALIZED' ? 'green' : 'orange'}>
            {meal.mealStatus}
          </Tag>
        </Descriptions.Item>
        <Descriptions.Item label="Date">
          {dayjs(meal.createdAt).format('YYYY-MM-DD HH:mm')}
        </Descriptions.Item>
        <Descriptions.Item label="Calories">{meal.totalCalories} kcal</Descriptions.Item>
        <Descriptions.Item label="Proteins">{meal.totalProteins} g</Descriptions.Item>
        <Descriptions.Item label="Carbs">{meal.totalCarbohydrates} g</Descriptions.Item>
        <Descriptions.Item label="Fats">{meal.totalFats} g</Descriptions.Item>
        <Descriptions.Item label="Sugars">{meal.totalSugars} g</Descriptions.Item>
      </Descriptions>

      <Divider>Entries</Divider>

      <List
        itemLayout="horizontal"
        dataSource={meal.entries}
        renderItem={(entry) => (
          <List.Item
            actions={[
              <Button
                size="small"
                onClick={() => navigate(`/food-items/${entry.foodItem.id}`)}
              >
                View Food Item
              </Button>,
            ]}
          >
            <List.Item.Meta
              avatar={<Avatar shape="square" size={64} src={entry.foodItem.imageUrl} />}
              title={entry.foodItem.foodName}
              description={
                <>
                  <div>Category: {entry.foodItem.category}</div>
                  <div>Quantity: {entry.quantity}</div>
                  <div>Calculated quantity: {Number(entry.quantity) * Number(entry.foodItem.servingSize)} {entry.foodItem.servingUnit}</div>
                </>
              }
            />
          </List.Item>
        )}
      />
    </Card>
  );
};

export default MealView;
