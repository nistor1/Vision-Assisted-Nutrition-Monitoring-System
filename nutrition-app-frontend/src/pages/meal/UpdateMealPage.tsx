import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { message, Card, Spin, Alert } from 'antd';
import { apiService } from '../../services/api';
import MealForm from '../../components/meal/MealForm';
import type {CreateMealRequest, Meal, UpdateMealRequest} from '../../types/MealEntities';
import { getRedirectedPath } from '../../utils/url';

const UpdateMealPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const location = useLocation();
  const redirectPath = getRedirectedPath(location.search);

  const [meal, setMeal] = useState<UpdateMealRequest | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchMeal = async () => {
      if (!id) return;
      setLoading(true);
      try {
        const response = await apiService.getMealById(id);
        const data: Meal | null = response.body.payload ?? null;

        if (data) {
          const transformed: UpdateMealRequest = {
            id: data.id,
            name: data.name,
            mealType: data.mealType,
            entries: data.entries.map(entry => ({
              foodItemId: entry.foodItem.id,
              quantity: entry.quantity
            }))
          };
          setMeal(transformed);
        } else {
          setMeal(null);
        }
      } catch (err) {
        console.error('Failed to fetch meal', err);
        setError('Failed to fetch meal');
      } finally {
        setLoading(false);
      }
    };
    fetchMeal();
  }, [id]);

  const handleSubmit = async (values: CreateMealRequest | UpdateMealRequest) => {
    if ('id' in values) {
      const response = await apiService.updateMeal(values);
      if (response.body.status === 'OK') {
        const response = await apiService.finalizeMeal(values.id);
        if (response.body.status === 'OK') {
            message.success('Meal confirmed successfully');
            navigate(redirectPath);
        }
      } else {
        const errors = response.body.errors?.map(e => e.message).join(', ') || 'Failed to update meal';
        message.error(errors);
      }
    } else {
      message.error('Unexpected payload: missing meal ID');
    }
  };

  const handleCancel = () => navigate(redirectPath);

  if (loading) return <Spin tip="Loading meal..." />;
  if (error) return <Alert type="error" message={error} showIcon />;
  if (!meal) return <Alert type="warning" message="Meal not found" showIcon />;

  return (
    <Card
      title="Edit Meal"
      style={{ maxWidth: 1000, margin: '0 auto' }}
    >
      <MealForm meal={meal} onSubmit={handleSubmit} onCancel={handleCancel}/>
    </Card>

  );
};

export default UpdateMealPage;
