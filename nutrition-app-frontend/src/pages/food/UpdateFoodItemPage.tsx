import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { message, Card, Spin, Alert } from 'antd';
import type { FoodItem } from '../../types/FoodEntities';
import { apiService } from '../../services/api';
import FoodForm from '../../components/food/FoodItemForm';
import { getRedirectedPath } from '../../utils/url';

const UpdateFoodItemPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const location = useLocation();

  const [foodItem, setFoodItem] = useState<FoodItem | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const redirectPath = getRedirectedPath(location.search);

  useEffect(() => {
    const fetchFoodItem = async () => {
      if (!id) return;
      setLoading(true);
      try {
        const res = await apiService.getFoodItemById(id);
        console.log('Fetched food item:', res.body);
        setFoodItem(res.body.payload ?? null);
      } catch (err) {
        setError('Failed to fetch food item data');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchFoodItem();
  }, [id]);

  const handleSubmit = async (values: FoodItem) => {
    try {
      const response = await apiService.updateFoodItem(values);

      if (response.body.status === 'OK') {
        message.success('Food item updated successfully');
        navigate(redirectPath);
      } else {
        const errors = response.body.errors?.map((e) => e.message).join(', ') || 'Failed to update food item';
        message.error(errors);
      }
    } catch (error) {
      console.error('Update failed:', error);
      message.error('An unexpected error occurred');
    }
  };

  const handleCancel = () => navigate(redirectPath);

  if (loading) return <Spin tip="Loading food item..." />;
  if (error) return <Alert type="error" message={error} showIcon />;
  if (!foodItem) return <Alert type="warning" message="Food item not found" showIcon />;

  return (
    <Card title="Edit Food Item" style={{ maxWidth: 800, margin: '0 auto' }}>
      <FoodForm initialValues={foodItem} onSubmit={handleSubmit} onCancel={handleCancel} />
    </Card>
  );
};

export default UpdateFoodItemPage;
