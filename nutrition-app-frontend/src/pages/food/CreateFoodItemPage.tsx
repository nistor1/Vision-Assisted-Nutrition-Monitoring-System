import React from 'react';
import { Card, message } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import type { FoodItem } from '../../types/FoodEntities';
import { apiService } from '../../services/api';
import FoodForm from '../../components/food/FoodItemForm.tsx';
import { getRedirectedPath } from '../../utils/url';

const CreateFoodItemPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleSubmit = async (values: Omit<FoodItem, 'id'>) => {
    try {
      const response = await apiService.createFoodItem(values);

      if (response.body.status === 'OK') {
        message.success('Food item created successfully');
        navigate(getRedirectedPath(location.search));
      } else {
        const errors = response.body.errors?.map((e) => e.message).join(', ') || 'Failed to create food item';
        message.error(errors);
      }
    } catch (error) {
      console.error('Failed to create food item:', error);
      message.error('An unexpected error occurred');
    }
  };

  const handleCancel = () => {
    navigate(getRedirectedPath(location.search));
  };

  return (
    <Card title="Create New Food Item" style={{ maxWidth: 800, margin: '0 auto' }}>
      <FoodForm onSubmit={handleSubmit} onCancel={handleCancel} />
    </Card>
  );
};

export default CreateFoodItemPage;
